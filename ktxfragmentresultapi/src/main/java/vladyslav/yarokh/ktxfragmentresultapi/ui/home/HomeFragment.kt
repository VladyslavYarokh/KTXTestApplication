package vladyslav.yarokh.ktxfragmentresultapi.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import vladyslav.yarokh.ktxfragmentresultapi.R
import vladyslav.yarokh.ktxfragmentresultapi.databinding.FragmentHomeBinding
import vladyslav.yarokh.ktxfragmentresultapi.ui.data.PriceSort
import vladyslav.yarokh.ktxfragmentresultapi.ui.data.ProductItem
import vladyslav.yarokh.ktxfragmentresultapi.ui.helper.list

private const val RESULT_KEY = "result_key_products"
private const val SORT_KEY = "price_sort_key"
private const val VERSION = "version"
private const val LAST_UPDATE = "last_update"

class HomeFragment: Fragment(), OnProductItemClick {
    private val productAdapter = ProductAdapter(this).also {
        it.items = list.toMutableList()
    }
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var remoteConfig: FirebaseRemoteConfig

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(RESULT_KEY) { _, bundle ->
            val selectedSort = bundle.getParcelable<PriceSort>(SORT_KEY)
            val sortedList = list.filter { it.price >= selectedSort!!.price }
            productAdapter.updateListItems(sortedList)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        binding.apply {
            rvProducts.apply {
                val divider = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
                AppCompatResources.getDrawable(requireContext(), R.drawable.divider).let {
                    divider.setDrawable(it!!)
                }

                addItemDecoration(divider)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = productAdapter
            }

            fabFilter.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.fromHomeToFilter())
            }

            remoteConfig = Firebase.remoteConfig
            remoteConfig.fetchAndActivate().addOnCompleteListener {
                tvVersion.text = "Version: ${remoteConfig[VERSION].asString()}"
                tvLastUpdate.text = "Last update: ${remoteConfig[LAST_UPDATE].asString()}"
            }
        }
    }

    override fun onProductClick(product: ProductItem) {
        Toast.makeText(
            requireContext(),
            "${product.name}, price: ${product.price}",
            Toast.LENGTH_LONG
        ).show()
    }
}