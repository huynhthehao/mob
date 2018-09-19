package vn.homecredit.hcvn.ui.support.history

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import vn.homecredit.hcvn.R
import vn.homecredit.hcvn.data.model.api.support.Support
import vn.homecredit.hcvn.databinding.ItemSupportHistoryBinding
import vn.homecredit.hcvn.ui.base.BaseRecyclerAdapter

class SupportHistoryAdapter(histories: List<Support>, val itemClickListener: BaseRecyclerAdapter.OnItemClickListener<Support>)
    : BaseRecyclerAdapter<Support, SupportHistoryAdapter.Holder>(mutableListOf()) {
    init {
        setItems(histories)
    }

    override fun getLayoutId(): Int = R.layout.item_support_history

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        return Holder(ItemSupportHistoryBinding.inflate(inflater, parent, false), this)
//        return Holder(DataBindingUtil.inflate(inflater, R.layout.item_support_history, parent, false), this)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(item(position))
    }

    class Holder(val binding: ItemSupportHistoryBinding, adapter: SupportHistoryAdapter) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener { adapter.itemClickListener.onItemClicked(adapter.item(adapterPosition)) }
        }

        fun bind(item: Support) {
            with(binding) {
                viewModel = SupportHistoryAdapterViewModel(item)
                executePendingBindings()
            }
        }
    }
}