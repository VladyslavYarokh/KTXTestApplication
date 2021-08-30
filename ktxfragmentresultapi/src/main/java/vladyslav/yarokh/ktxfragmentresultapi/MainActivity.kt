package vladyslav.yarokh.ktxfragmentresultapi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import vladyslav.yarokh.ktxfragmentresultapi.databinding.ActivityMainBinding
import vladyslav.yarokh.ktxfragmentresultapi.ui.data.PriceSort

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}