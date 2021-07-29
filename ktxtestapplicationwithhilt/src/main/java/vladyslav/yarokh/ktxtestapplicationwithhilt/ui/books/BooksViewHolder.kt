package vladyslav.yarokh.ktxtestapplicationwithhilt.ui.books

import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import vladyslav.yarokh.ktxtestapplicationwithhilt.R
import vladyslav.yarokh.ktxtestapplicationwithhilt.base.BaseViewHolder
import vladyslav.yarokh.ktxtestapplicationwithhilt.data.BookModel
import vladyslav.yarokh.ktxtestapplicationwithhilt.databinding.HolderBookBinding

internal class BooksViewHolder(binding: ViewDataBinding, private val listener: BookItemListener): BaseViewHolder<BookModel, HolderBookBinding>(binding) {
    override fun bindView(position: Int) {
        with(binding){
            tvBookName.text = item.name
            tvAuthor.text = item.author
            llBook.setOnClickListener {
                listener.onBookClick(item)
            }

            var requestOptions = RequestOptions()
            requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(16))
            Glide.with(binding.root.context).applyDefaultRequestOptions(requestOptions).load(item.url).placeholder(R.drawable.ic_baseline_image_not_supported_24).into(ivBook)
        }
    }
}