package kz.beeline.cars.adapterdelegatewithdagger.endfragment.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import kz.beeline.cars.adapterdelegatewithdagger.R
import kz.beeline.cars.adapterdelegatewithdagger.data.FooterType
import kz.beeline.cars.adapterdelegatewithdagger.data.TypeMarker
import kz.beeline.cars.adapterdelegatewithdagger.ext.inflate

class FooterTypeDelegate: AdapterDelegate<MutableList<TypeMarker>>() {

    var callback: ((footerType: FooterType) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(parent.inflate(R.layout.holder_footer))

    override fun isForViewType(items: MutableList<TypeMarker>, position: Int) =
        items.getOrNull(position) is FooterType

    override fun onBindViewHolder(
        list: MutableList<TypeMarker>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payload: MutableList<Any>
    ) {
        (holder as? ViewHolder)?.bind(list[position] as FooterType)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(model: FooterType) {
            itemView.apply {
                setOnClickListener {
                    callback?.invoke(model)
                }

                findViewById<TextView>(R.id.tv_title).text = model.title
                findViewById<TextView>(R.id.tv_total).text = "${model.total} $"
            }
        }
    }
}