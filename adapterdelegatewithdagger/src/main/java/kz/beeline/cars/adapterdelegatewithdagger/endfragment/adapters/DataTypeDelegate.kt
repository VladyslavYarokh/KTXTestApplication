package kz.beeline.cars.adapterdelegatewithdagger.endfragment.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import kz.beeline.cars.adapterdelegatewithdagger.R
import kz.beeline.cars.adapterdelegatewithdagger.data.DataType
import kz.beeline.cars.adapterdelegatewithdagger.data.TypeMarker
import kz.beeline.cars.adapterdelegatewithdagger.ext.inflate

class DataTypeDelegate : AdapterDelegate<MutableList<TypeMarker>>() {

    var callback: ((dataType: DataType) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup) =
        ViewHolder(parent.inflate(R.layout.holder_main_type))

    override fun isForViewType(items: MutableList<TypeMarker>, position: Int) =
        items.getOrNull(position) is DataType

    override fun onBindViewHolder(
        list: MutableList<TypeMarker>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payload: MutableList<Any>
    ) {
        (holder as? ViewHolder)?.bind(list[position] as DataType)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(model: DataType) {
            itemView.apply {
                setOnClickListener {
                    callback?.invoke(model)
                }
                findViewById<TextView>(R.id.tv_title).text = model.item
                findViewById<TextView>(R.id.tv_count).text = "${model.count} шт"
                findViewById<TextView>(R.id.tv_price).text = "${model.price} $"
            }
        }
    }

}