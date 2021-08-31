package kz.beeline.cars.adapterdelegatewithdagger.di

import dagger.Component

@Component
interface AppComponent {
    fun getStartComponent(): StartFragmentComponent
    fun getEndComponent(): EndFragmentComponent
}