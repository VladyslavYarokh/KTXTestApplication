package vladyslav.yarokh.ktxtestapplicationwithhilt.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<DataType : Any>(
    open var items: MutableList<DataType> = arrayListOf()
) : RecyclerView.Adapter<BaseViewHolder<DataType, out ViewDataBinding>>() {

    fun getItem(position: Int): DataType {
        return items[position]
    }

    override fun getItemCount() = items.size

    protected fun inflate(parent: ViewGroup, @LayoutRes contentLayoutID: Int): ViewDataBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), contentLayoutID, parent, false
        )
    }

    override fun onViewAttachedToWindow(holder: BaseViewHolder<DataType, out ViewDataBinding>) {
        holder.onAttachToWindow()
        super.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: BaseViewHolder<DataType, out ViewDataBinding>) {
        holder.onDetachFromWindow()
        super.onViewDetachedFromWindow(holder)
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<DataType, out ViewDataBinding>,
        position: Int
    ) {
        holder.bindView(position, getItem(position))
    }

    open fun updateListItems(newObjects: List<DataType>) {
        items.clear()
        items.addAll(newObjects)
        notifyDataSetChanged()
    }
}