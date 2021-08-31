package kz.beeline.cars.adapterdelegatewithdagger.endfragment

import kz.beeline.cars.adapterdelegatewithdagger.data.DataType
import kz.beeline.cars.adapterdelegatewithdagger.data.FooterType
import kz.beeline.cars.adapterdelegatewithdagger.data.HeaderType
import kz.beeline.cars.adapterdelegatewithdagger.data.MessageState
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class EndFragmentPresenter @Inject constructor(messageState: MessageState): MvpPresenter<EndFragmentView>() {
    init {
        viewState.showMessage(messageState)
    }

    fun back(){
        viewState.back()
    }

    fun onHeaderClick(headerType: HeaderType){
        viewState.showHeaderTitle(headerType)
    }

    fun onMainClick(dataType: DataType){
        viewState.updateTotal(dataType)
    }


    fun onFooterClick(footerType: FooterType){
        viewState.releaseTotal(footerType)
    }
}