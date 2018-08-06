/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 8/2/18 9:28 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.ui.contract.creditcard.transaction;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import vn.homecredit.hcvn.data.model.api.Transaction;
import vn.homecredit.hcvn.databinding.ItemTransactionBinding;

public class TransactionHolder extends RecyclerView.ViewHolder {

    private ItemTransactionBinding binding;
    private TransactionItemListener listener;

    public ItemTransactionBinding getBinding(){
        return this.binding;
    }

    public void setListener(TransactionItemListener listener){
        this.listener = listener;
    }

    public TransactionHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);

        itemView.setOnClickListener(v -> {
            Transaction transaction = binding.getTransaction();

            if(listener != null)
                listener.onTransactionTapped(transaction);
        });
    }
}