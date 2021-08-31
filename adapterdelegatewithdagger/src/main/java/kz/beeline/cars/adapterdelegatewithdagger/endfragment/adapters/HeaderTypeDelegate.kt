package kz.beeline.cars.adapterdelegatewithdagger.endfragment.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import kz.beeline.cars.adapterdelegatewithdagger.R
import kz.beeline.cars.adapterdelegatewithdagger.data.DataType
import kz.beeline.cars.adapterdelegatewithdagger.data.HeaderType
import kz.beeline.cars.adapterdelegatewithdagger.data.TypeMarker
import kz.beeline.cars.adapterdelegatewithdagger.ext.inflate

class HeaderTypeDelegate: AdapterDelegate<MutableList<TypeMarker>>() {

    var callback: ((headerType: HeaderType) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(parent.inflate(R.layout.holder_header))

    override fun isForViewType(items: MutableList<TypeMarker>, position: Int) =
        items.getOrNull(position) is HeaderType

    override fun onBindViewHolder(
        list: MutableList<TypeMarker>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payload: MutableList<Any>
    ) {
        (holder as? ViewHolder)?.bind(list[position] as HeaderType)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(model: HeaderType) {
            itemView.apply {
                setOnClickListener {
                    callback?.invoke(model)
                }

                findViewById<TextView>(R.id.tv_title).text = model.title.plus(" ${model.type}")
            }
        }
    }
}