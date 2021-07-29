package vladyslav.yarokh.ktxfragmentresultapi.ui.splash

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import vladyslav.yarokh.ktxfragmentresultapi.databinding.FragmentSplashBinding

private const val VERSION = "version"
private const val LAST_UPDATE = "last_update"

class SplashFragment: Fragment() {
    private lateinit var remoteConfig: FirebaseRemoteConfig
    private lateinit var viewModel: SplashViewModel
    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(SplashViewModel::class.java)

        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        binding.apply {
            remoteConfig = Firebase.remoteConfig
            /**
             * Если указанное время не прошло
             * то fetchAndActivate() не отработает
             * */
            val configSettings = remoteConfigSettings {
                minimumFetchIntervalInSeconds = 3
            }
            remoteConfig.setConfigSettingsAsync(configSettings)
            /**
             * Будет брать эти дефолтные значения
             * если актуальные еще ни разу не фетчились и не активировались
             * */
            remoteConfig.setDefaultsAsync(mapOf(
                VERSION to "1.2.1",
                LAST_UPDATE to "129999999299"
            ))

            Handler().postDelayed({
                findNavController().navigate(SplashFragmentDirections.fromSplashToHome())
            }, 3000)
        }
    }
}