package kz.beeline.cars.adapterdelegatewithdagger.di

import dagger.Module
import dagger.Provides
import kz.beeline.cars.adapterdelegatewithdagger.data.MessageState

@Module
class EndComponentModule {
    @Provides
    fun provideMessageState(state: String): MessageState = MessageState(state)

    @Provides
    fun provideState(): String = "Loaded end"
}