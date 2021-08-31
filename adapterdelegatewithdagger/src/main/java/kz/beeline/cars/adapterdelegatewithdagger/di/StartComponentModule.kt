package kz.beeline.cars.adapterdelegatewithdagger.di

import dagger.Module
import dagger.Provides
import kz.beeline.cars.adapterdelegatewithdagger.data.MessageState

@Module
class StartComponentModule {
    @Provides
    fun provideMessageState(state: String): MessageState = MessageState(state)

    @Provides
    fun provideState(): String = "Loaded start"
}