package vn.homecredit.hcvn.ui.base

import android.support.v7.widget.RecyclerView

/**
 * Create an abstract base recycler adapter for reusing purpose
 */
abstract class BaseRecyclerAdapter<T : Any, VH : RecyclerView.ViewHolder>(protected val items: MutableList<T>) : RecyclerView.Adapter<VH>() {
    override fun getItemCount(): Int = items.count()
    fun item(position: Int) = items[position]

    interface OnItemClickListener<T : Any> {
        fun onItemClicked(item: T)
    }
}