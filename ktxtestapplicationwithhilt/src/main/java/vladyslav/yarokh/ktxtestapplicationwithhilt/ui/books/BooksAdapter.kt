package vladyslav.yarokh.ktxtestapplicationwithhilt.ui.books

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import vladyslav.yarokh.ktxtestapplicationwithhilt.R
import vladyslav.yarokh.ktxtestapplicationwithhilt.base.BaseRecyclerAdapter
import vladyslav.yarokh.ktxtestapplicationwithhilt.base.BaseViewHolder
import vladyslav.yarokh.ktxtestapplicationwithhilt.data.BookModel
import vladyslav.yarokh.ktxtestapplicationwithhilt.ext.autoNotify
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class BooksAdapter (
    private val listener: BookItemListener
):  BaseRecyclerAdapter<BookModel>(){

    override var items: MutableList<BookModel> by Delegates.observable(
        mutableListOf(),
        ::onItemChange
    )

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<BookModel, out ViewDataBinding> = BooksViewHolder(inflate(parent, R.layout.holder_book), listener)

    override fun updateListItems(newObjects: List<BookModel>) {
        items = newObjects.toMutableList()
    }

    private fun onItemChange(
        property: KProperty<*>,
        oldList: MutableList<BookModel>,
        newValue: MutableList<BookModel>
    ) {
        autoNotify(oldList, newValue) { oldConversation, newConversation ->
            oldConversation.url.hashCode() == newConversation.url.hashCode()
        }
    }

    override fun getItemId(position: Int): Long {
        return items[position].url.hashCode().toLong()
    }
}