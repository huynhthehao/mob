/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 8/6/18 11:10 AM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.ui.contract.creditcard.list;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import dagger.Module;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.api.HcCreditCard;
import vn.homecredit.hcvn.data.model.api.contract.HcContract;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;


@Module
public class CreditCardListViewModel extends BaseViewModel {

    public List<HcCreditCard> cards = new ArrayList<>();

    @Inject
    public CreditCardListViewModel(SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
    }

    public void setData(Context context, HcContract contract){
        if(contract == null || contract.getCards() == null)
            return;

        List<HcCreditCard> activeCards = new ArrayList<>();
        List<HcCreditCard> nonActiveCards = new ArrayList<>();

        for (HcCreditCard card : contract.getCards()){
            card.refContract = contract;
            if(HcCreditCard.ActiveStatusLabel.equalsIgnoreCase(card.status))
                activeCards.add(card);
            else
                nonActiveCards.add(card);
        }

        activeCards.addAll(nonActiveCards);
        this.cards = activeCards;
    }

    public List<HcCreditCard> getData(){
        return cards;
    }
}
