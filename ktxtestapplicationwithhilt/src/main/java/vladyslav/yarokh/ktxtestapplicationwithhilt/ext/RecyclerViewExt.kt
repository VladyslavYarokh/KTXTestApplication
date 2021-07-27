package vladyslav.yarokh.ktxtestapplicationwithhilt.ext

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun <T> RecyclerView.Adapter<*>.autoNotify(
    oldList: List<T>,
    newList: List<T>,
    compare: (T, T) -> Boolean
) {
    val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return compare(oldList[oldItemPosition], newList[newItemPosition])
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size
    })
    diff.dispatchUpdatesTo(this)
}

fun RecyclerView.chatAutoScroll(compare: (firstVisibleElement: Int) -> Boolean) {

    adapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            if (positionStart == RecyclerView.SCROLLBAR_POSITION_DEFAULT) {
                val manager = layoutManager as LinearLayoutManager? ?: return
                val firstVisibleItemPosition = manager.findFirstVisibleItemPosition()
                if (firstVisibleItemPosition == RecyclerView.NO_POSITION) {
                    return
                }
                if (compare(firstVisibleItemPosition)) {
                    manager.scrollToPosition(RecyclerView.SCROLLBAR_POSITION_DEFAULT)
                }
            }
        }
    })
}

fun RecyclerView.onScrollListener(listener: (firstElement: Int) -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val manager = layoutManager as LinearLayoutManager? ?: return
            val firstVisibleItemPosition = manager.findFirstVisibleItemPosition()
            if (firstVisibleItemPosition == RecyclerView.NO_POSITION) {
                return
            }
            listener.invoke(firstVisibleItemPosition)
        }
    })
}