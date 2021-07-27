package vladyslav.yarokh.ktxtestapplicationwithhilt.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<DataType : Any, ViewBindingType>(binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {

    lateinit var item: DataType
        private set

    @Suppress("UNCHECKED_CAST")
    val binding: ViewBindingType = binding as ViewBindingType

    fun bindView(position: Int, item: DataType) {
        this.item = item
        bindView(position)
    }

    abstract fun bindView(position: Int)

    open fun onAttachToWindow() {}

    open fun onDetachFromWindow() {}
}