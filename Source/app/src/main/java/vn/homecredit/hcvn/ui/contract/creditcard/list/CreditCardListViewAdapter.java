/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 8/2/18 9:29 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.ui.contract.creditcard.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vn.homecredit.hcvn.data.model.api.creditcard.HcCreditCard;
import vn.homecredit.hcvn.databinding.ItemCreditCardBinding;


public class CreditCardListViewAdapter extends RecyclerView.Adapter<CreditCardHolder> {

    private List<HcCreditCard> items = new ArrayList<>();
    private CreditCardContractListener listener;
    private Context context;

    public CreditCardListViewAdapter(Context context) {
        this.context = context;
    }

    public void setNewData(List<HcCreditCard> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void setListener(CreditCardContractListener listener) {
        this.listener = listener;
    }


    @Override
    public CreditCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ItemCreditCardBinding binding = ItemCreditCardBinding.inflate(layoutInflater, parent, false);

        return new CreditCardHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(CreditCardHolder holder, int position) {
        HcCreditCard creditCard = items.get(position);
        holder.getBinding().setCard(creditCard);
        holder.setListener(listener);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }
}
