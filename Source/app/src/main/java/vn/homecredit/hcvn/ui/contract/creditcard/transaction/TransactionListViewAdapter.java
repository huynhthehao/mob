/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 8/2/18 9:29 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.ui.contract.creditcard.transaction;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vn.homecredit.hcvn.data.model.api.creditcard.Transaction;
import vn.homecredit.hcvn.databinding.ItemTransactionBinding;


public class TransactionListViewAdapter extends RecyclerView.Adapter<TransactionHolder> {

    private List<Transaction> items = new ArrayList<>();
    private TransactionItemListener listener;
    private Context context;

    public TransactionListViewAdapter(Context context) {
        this.context = context;
    }

    public void setNewData(List<Transaction> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void setListener(TransactionItemListener listener) {
        this.listener = listener;
    }


    @Override
    public TransactionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ItemTransactionBinding binding = ItemTransactionBinding.inflate(layoutInflater, parent, false);

        return new TransactionHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(TransactionHolder holder, int position) {
        Transaction transaction = items.get(position);
        holder.getBinding().setTransaction(transaction);
        holder.setListener(listener);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }
}
