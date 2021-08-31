package kz.beeline.cars.adapterdelegatewithdagger.startfragment

import kz.beeline.cars.adapterdelegatewithdagger.data.MessageState
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class StartFragmentPresenter @Inject constructor(messageState: MessageState): MvpPresenter<StartFragmentView>() {
    init {
        viewState.showMessage(messageState)
    }

    fun showNextScreen(){
        viewState.showNextScreen()
    }
}