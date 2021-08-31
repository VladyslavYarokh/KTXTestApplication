package kz.beeline.cars.adapterdelegatewithdagger

import android.app.Application
import com.github.terrakok.cicerone.Cicerone
import kz.beeline.cars.adapterdelegatewithdagger.di.AppComponent
import kz.beeline.cars.adapterdelegatewithdagger.di.DaggerAppComponent

class App: Application() {

    lateinit var appComponent: AppComponent
    private val cicerone = Cicerone.create()
    val router get() = cicerone.router
    val navigatorHolder get() = cicerone.getNavigatorHolder()

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        appComponent = DaggerAppComponent.create()
    }

    companion object {
        internal lateinit var INSTANCE: App
            private set
    }
}