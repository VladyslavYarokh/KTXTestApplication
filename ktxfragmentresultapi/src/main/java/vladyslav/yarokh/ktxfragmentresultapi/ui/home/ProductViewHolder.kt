package vladyslav.yarokh.ktxfragmentresultapi.ui.home

import android.annotation.SuppressLint
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import vladyslav.yarokh.ktxfragmentresultapi.R
import vladyslav.yarokh.ktxfragmentresultapi.databinding.HolderProductItemBinding
import vladyslav.yarokh.ktxfragmentresultapi.ui.data.ProductItem
import vladyslav.yarokh.recyclerlibrary.base.BaseViewHolder

class ProductViewHolder(binding: ViewDataBinding, private val listener: OnProductItemClick): BaseViewHolder<ProductItem, HolderProductItemBinding>(binding) {
    @SuppressLint("SetTextI18n")
    override fun bindView(position: Int) {
        with(binding){
            tvProduct.text = item.name
            tvDesc.text = item.description
            tvPrice.text = "${item.price}$"
            llProduct.setOnClickListener {
                listener.onProductClick(item)
            }

            var requestOptions = RequestOptions()
            requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(16))
            Glide.with(binding.root.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(item.url)
                .placeholder(R.drawable.ic_baseline_image_not_supported_24)
                .into(ivProduct)
        }
    }
}