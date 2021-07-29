package vladyslav.yarokh.ktxfragmentresultapi.ui.home

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import vladyslav.yarokh.ktxfragmentresultapi.R
import vladyslav.yarokh.ktxfragmentresultapi.ui.data.ProductItem
import vladyslav.yarokh.recyclerlibrary.base.BaseRecyclerAdapter
import vladyslav.yarokh.recyclerlibrary.base.BaseViewHolder
import vladyslav.yarokh.recyclerlibrary.ext.autoNotify
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class ProductAdapter (
    private val listener: OnProductItemClick
):  BaseRecyclerAdapter<ProductItem>(){

    override var items: MutableList<ProductItem> by Delegates.observable(
        mutableListOf(),
        ::onItemChange
    )

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ProductItem, out ViewDataBinding> = ProductViewHolder(inflate(parent, R.layout.holder_product_item), listener)

    override fun updateListItems(newObjects: List<ProductItem>) {
        items = newObjects.toMutableList()
    }

    private fun onItemChange(
        property: KProperty<*>,
        oldList: MutableList<ProductItem>,
        newValue: MutableList<ProductItem>
    ) {
        autoNotify(oldList, newValue) { oldConversation, newConversation ->
            oldConversation.url.hashCode() == newConversation.url.hashCode()
        }
    }

    override fun getItemId(position: Int): Long {
        return items[position].url.hashCode().toLong()
    }
}