package vladyslav.yarokh.ktxactivityresultapi.ui

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import vladyslav.yarokh.ktxactivityresultapi.R
import vladyslav.yarokh.ktxactivityresultapi.data.PermissionItem
import vladyslav.yarokh.recyclerlibrary.base.BaseRecyclerAdapter
import vladyslav.yarokh.recyclerlibrary.base.BaseViewHolder
import vladyslav.yarokh.recyclerlibrary.ext.autoNotify
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class PermissionAdapter: BaseRecyclerAdapter<PermissionItem>() {
    override var items: MutableList<PermissionItem> by Delegates.observable(
        mutableListOf(),
        ::onItemChange
    )

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<PermissionItem, out ViewDataBinding> = PermissionViewHolder(inflate(parent, R.layout.holder_permission_item))

    override fun updateListItems(newObjects: List<PermissionItem>) {
        items = newObjects.toMutableList()
    }

    private fun onItemChange(
        property: KProperty<*>,
        oldList: MutableList<PermissionItem>,
        newValue: MutableList<PermissionItem>
    ) {
        autoNotify(oldList, newValue) { oldConversation, newConversation ->
            oldConversation.permission.hashCode() == newConversation.permission.hashCode()
        }
    }

    override fun getItemId(position: Int): Long {
        return items[position].permission.hashCode().toLong()
    }
}