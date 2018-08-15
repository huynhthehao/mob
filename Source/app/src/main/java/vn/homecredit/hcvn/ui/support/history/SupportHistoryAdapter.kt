package vn.homecredit.hcvn.ui.support.history

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import vn.homecredit.hcvn.data.model.api.support.Support
import vn.homecredit.hcvn.databinding.ItemSupportHistoryBinding
import vn.homecredit.hcvn.ui.base.BaseRecyclerAdapter

class SupportHistoryAdapter(histories: MutableList<Support>) : BaseRecyclerAdapter<Support, SupportHistoryAdapter.Holder>(histories) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)  :Holder {
        return Holder(ItemSupportHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
//        return Holder(ItemSupportHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(item(position))
    }

    class Holder(val binding: ItemSupportHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Support) {
            with(binding) {
                viewModel = SupportHistoryAdapterViewModel(item)
                executePendingBindings()
            }
        }
    }
}