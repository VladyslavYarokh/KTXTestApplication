package vladyslav.yarokh.ktxactivityresultapi

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import vladyslav.yarokh.ktxactivityresultapi.databinding.ActivitySecondBinding

private const val TOAST = "toast"
private const val PERMISSION_CODE = "permission_code"

class SecondActivity: AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Toast.makeText(this, intent.getStringExtra(TOAST), Toast.LENGTH_LONG).show()

        initViews()
    }

    private fun initViews() {
        binding.apply {
            btnGrantPermission.setOnClickListener {
                val intent = Intent().apply {
                    putExtra(PERMISSION_CODE, Manifest.permission.CAMERA)
                }
                setResult(Activity.RESULT_OK, intent)
                finish()
            }

            ivBack.setOnClickListener {
                onBackPressed()
            }
        }
    }
}