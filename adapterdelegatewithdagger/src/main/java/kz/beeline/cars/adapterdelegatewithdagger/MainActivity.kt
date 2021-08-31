package kz.beeline.cars.adapterdelegatewithdagger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import kz.beeline.cars.adapterdelegatewithdagger.routes.Screens
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import javax.inject.Inject

class MainActivity : AppCompatActivity(){

    val navigator = AppNavigator(this, R.id.root)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews(){
        (application as App).router.newRootScreen(Screens.Main())
    }

    override fun onResume() {
        super.onResume()
        (application as App).navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        (application as App).navigatorHolder.removeNavigator()
        super.onPause()
    }
}