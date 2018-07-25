/*
 * ContractRecyclerViewAdapter.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/13/18 4:11 PM
 */

package vn.homecredit.hcvn.ui.contract;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.api.contract.HcContract;
import vn.homecredit.hcvn.databinding.ItemContractCloseBinding;
import vn.homecredit.hcvn.ui.contract.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;

public class ContractRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<HcContract> items = new ArrayList<>();

    public ContractRecyclerViewAdapter() {
    }

    public void setNewData(List<HcContract> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public HcContract getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getTypeContract();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == HcContract.TYPE_CLOSED) {
            ItemContractCloseBinding itemContractCloseBinding = ItemContractCloseBinding.inflate(layoutInflater,parent, false);
            return new CloseViewHolder(itemContractCloseBinding);

        }else {
            ItemContractCloseBinding itemContractCloseBinding = ItemContractCloseBinding.inflate(layoutInflater,parent, false);
            return new PendingViewHolder(itemContractCloseBinding);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        HcContract hcContract = items.get(position);
        if (hcContract.getTypeContract() == HcContract.TYPE_CLOSED) {
            ((CloseViewHolder) holder).bind(hcContract);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class PendingViewHolder extends RecyclerView.ViewHolder {
        private final ItemContractCloseBinding binding;

        public PendingViewHolder(ItemContractCloseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(HcContract hcContract) {
            binding.setContract(hcContract);
            binding.executePendingBindings();
        }
    }
    public class CloseViewHolder extends RecyclerView.ViewHolder {
        private final ItemContractCloseBinding binding;

        public CloseViewHolder(ItemContractCloseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(HcContract hcContract) {
            binding.setContract(hcContract);
            if (getLayoutPosition() != 0) {
                binding.tvSection.setVisibility(View.GONE);
            }else {
                binding.tvSection.setVisibility(View.VISIBLE);
            }
            binding.executePendingBindings();
        }
    }
}
