package kz.beeline.cars.adapterdelegatewithdagger.endfragment

import kz.beeline.cars.adapterdelegatewithdagger.data.DataType
import kz.beeline.cars.adapterdelegatewithdagger.data.FooterType
import kz.beeline.cars.adapterdelegatewithdagger.data.HeaderType
import kz.beeline.cars.adapterdelegatewithdagger.data.MessageState
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndStrategy::class)
interface EndFragmentView: MvpView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(state: MessageState)
    fun back()
    fun showHeaderTitle(headerType: HeaderType)
    fun updateTotal(dataType: DataType)
    fun releaseTotal(footerType: FooterType)
}