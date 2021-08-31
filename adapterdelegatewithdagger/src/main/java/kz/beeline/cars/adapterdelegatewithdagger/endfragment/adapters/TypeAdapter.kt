package kz.beeline.cars.adapterdelegatewithdagger.endfragment.adapters

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import kz.beeline.cars.adapterdelegatewithdagger.ListUtils
import kz.beeline.cars.adapterdelegatewithdagger.data.DataType
import kz.beeline.cars.adapterdelegatewithdagger.data.FooterType
import kz.beeline.cars.adapterdelegatewithdagger.data.HeaderType
import kz.beeline.cars.adapterdelegatewithdagger.data.TypeMarker
import javax.inject.Inject

class TypeAdapter @Inject constructor(): ListDelegationAdapter<MutableList<TypeMarker>>() {

    private val headerDelegate = HeaderTypeDelegate()
    private val mainDelegate = DataTypeDelegate()
    private val footerDelegate = FooterTypeDelegate()

    var callback: Callback? = null
        set(value) {
        field = value
        headerDelegate.callback = { model -> callback?.onHeaderTypeClick(model) }
        mainDelegate.callback = { model -> callback?.onMainTypeClick(model)  }
        footerDelegate.callback = { model -> callback?.onFooterTypeClick(model)  }
    }

    init {
        items = ListUtils.provideList().toMutableList()

        delegatesManager
            .addDelegate(headerDelegate)
            .addDelegate(mainDelegate)
            .addDelegate(footerDelegate)
    }

    fun swapData(orders: List<TypeMarker>) {
        items.clear()
        items.addAll(orders)
        notifyDataSetChanged()
    }

    interface Callback {
        fun onHeaderTypeClick(header: HeaderType)
        fun onMainTypeClick(dataType: DataType)
        fun onFooterTypeClick(footer: FooterType)
    }
}