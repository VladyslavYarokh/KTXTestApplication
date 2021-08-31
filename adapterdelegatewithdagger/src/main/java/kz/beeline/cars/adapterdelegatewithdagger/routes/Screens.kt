package kz.beeline.cars.adapterdelegatewithdagger.routes

import com.github.terrakok.cicerone.androidx.FragmentScreen
import kz.beeline.cars.adapterdelegatewithdagger.endfragment.EndFragment
import kz.beeline.cars.adapterdelegatewithdagger.startfragment.StartFragment

object Screens {
    fun Main() = FragmentScreen { StartFragment() }
    fun End() = FragmentScreen { EndFragment() }
}