package kz.beeline.cars.adapterdelegatewithdagger.di

import dagger.Subcomponent
import kz.beeline.cars.adapterdelegatewithdagger.data.MessageState
import kz.beeline.cars.adapterdelegatewithdagger.endfragment.EndFragment
import kz.beeline.cars.adapterdelegatewithdagger.startfragment.StartFragment

@Subcomponent(modules = [EndComponentModule::class])
interface EndFragmentComponent {
    fun inject(endFragment: EndFragment)
}