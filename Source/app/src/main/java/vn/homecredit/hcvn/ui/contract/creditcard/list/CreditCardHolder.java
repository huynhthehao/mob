/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 8/6/18 11:40 AM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.ui.contract.creditcard.list;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import vn.homecredit.hcvn.data.model.api.HcCreditCard;
import vn.homecredit.hcvn.databinding.ItemCreditCardBinding;

public class CreditCardHolder extends RecyclerView.ViewHolder {

    private ItemCreditCardBinding binding;
    private CreditCardContractListener listener;

    public ItemCreditCardBinding getBinding(){
        return this.binding;
    }

    public void setListener(CreditCardContractListener listener){
        this.listener = listener;
    }

    public CreditCardHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);

        itemView.setOnClickListener(v -> {
            HcCreditCard card = binding.getCard();

            if(listener != null)
                listener.onCardTapped(card);
        });
    }
}