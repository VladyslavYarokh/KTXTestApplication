package kz.beeline.cars.adapterdelegatewithdagger.di

import dagger.Subcomponent
import kz.beeline.cars.adapterdelegatewithdagger.data.MessageState
import kz.beeline.cars.adapterdelegatewithdagger.startfragment.StartFragment

@Subcomponent(modules = [StartComponentModule::class])
interface StartFragmentComponent {
    fun inject(startFragment: StartFragment)
}