package kz.beeline.cars.adapterdelegatewithdagger.startfragment

import kz.beeline.cars.adapterdelegatewithdagger.data.MessageState
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndStrategy::class)
interface StartFragmentView: MvpView {
    fun showNextScreen()
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(state: MessageState)
}