/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/30/18 3:57 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.ui.contract.creditcard;

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
            Drawable type;
            if(HcCreditCard.ActiveStatusLabel.equalsIgnoreCase(card.status)){
                type = context.getResources().getDrawable(R.drawable.shadow_no_border);
                activeCards.add(card);
            }
            else {
                type = context.getResources().getDrawable(R.drawable.card_rounded_corner);
                nonActiveCards.add(card);
            }
            card.setBackgroundTypeId(type);
        }

        activeCards.addAll(nonActiveCards);

        this.cards = activeCards;
    }

    public List<HcCreditCard> getData(){
        return cards;
    }
}
