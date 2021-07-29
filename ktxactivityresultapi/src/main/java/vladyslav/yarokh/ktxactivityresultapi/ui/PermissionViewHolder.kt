package vladyslav.yarokh.ktxactivityresultapi.ui

import android.annotation.SuppressLint
import androidx.databinding.ViewDataBinding
import vladyslav.yarokh.ktxactivityresultapi.data.PermissionItem
import vladyslav.yarokh.ktxactivityresultapi.databinding.HolderPermissionItemBinding
import vladyslav.yarokh.recyclerlibrary.base.BaseViewHolder

class PermissionViewHolder(binding: ViewDataBinding): BaseViewHolder<PermissionItem, HolderPermissionItemBinding>(binding) {
    @SuppressLint("SetTextI18n")
    override fun bindView(position: Int) {
        binding.apply {
            tvId.text = "#${position.plus(1)}"
            tvPermission.text = item.permission
        }
    }
}