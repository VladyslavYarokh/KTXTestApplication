package vladyslav.yarokh.ktxactivityresultapi

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import vladyslav.yarokh.ktxactivityresultapi.contracts.OpenSecondActivityContract
import vladyslav.yarokh.ktxactivityresultapi.data.PermissionItem
import vladyslav.yarokh.ktxactivityresultapi.databinding.ActivityMainBinding
import vladyslav.yarokh.ktxactivityresultapi.ui.PermissionAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val grantedItems: MutableSet<String> = mutableSetOf()
    private val nonGrantedItems: MutableSet<String> = mutableSetOf()
    private val grantedData: MutableLiveData<Set<String>> = MutableLiveData(grantedItems)
    private val nonGrantedData: MutableLiveData<Set<String>> = MutableLiveData(nonGrantedItems)
    private val permAdapter = PermissionAdapter()
    private val nonPermAdapter = PermissionAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        binding.apply {
            val divider = DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
            AppCompatResources.getDrawable(this@MainActivity, R.drawable.divider).let {
                divider.setDrawable(it!!)
            }

            rvPermissions.apply {
                addItemDecoration(divider)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = permAdapter
            }

            rvNonPermissions.apply {
                addItemDecoration(divider)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = nonPermAdapter
            }

            grantedData.observe(this@MainActivity, observer)
            nonGrantedData.observe(this@MainActivity, nonGrantedObserver)

            fabGrantPermission.setOnClickListener {
                openActivity.launch(this@MainActivity.localClassName)
            }

            gainPermission.launch(arrayOf(
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.ACCESS_FINE_LOCATION
            ))
        }
    }

    private val openActivity = registerForActivityResult(OpenSecondActivityContract()) {
        grantedItems.add(it.toString())
        grantedData.postValue(grantedItems)
    }

    private val gainPermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
       permissions.entries.forEach {
           when {
               it.value.equals(true) -> {
                   grantedItems.add(it.key)
               }
               shouldShowRequestPermissionRationale(it.key) -> {
                   nonGrantedItems.add(it.key)
               }
               else -> {
                   Toast.makeText(this, "${it.key} permission marked as Dont show again. Fix it.", Toast.LENGTH_LONG).show()
               }
           }
           grantedData.postValue(grantedItems)
           nonGrantedData.postValue(nonGrantedItems)
       }
    }

    private val observer = Observer<Set<String>> {
        permAdapter.updateListItems(it.map { permission -> PermissionItem(permission) })
    }

    private val nonGrantedObserver = Observer<Set<String>> {
        nonPermAdapter.updateListItems(it.map { permission -> PermissionItem(permission) })
    }
}