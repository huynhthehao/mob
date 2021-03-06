/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 8/2/18 9:34 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.ui.contract.creditcard.detail;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import javax.inject.Inject;

import dagger.Module;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.api.creditcard.HcCreditCard;
import vn.homecredit.hcvn.service.ResourceService;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;


@Module
public class CreditCardDetailViewModel extends BaseViewModel {

    private final ResourceService resourceService;
    private CreditCardDetailListener listener;

    public ObservableField<HcCreditCard> cardData = new ObservableField();
    public ObservableBoolean morePanelVisible = new ObservableBoolean(false);
    public ObservableField<String> morePanelText = new ObservableField("");

    @Inject
    public CreditCardDetailViewModel(SchedulerProvider schedulerProvider, ResourceService resourceService) {
        super(schedulerProvider);
        this.resourceService = resourceService;
        String more = resourceService.getStringById(R.string.show_more);
        morePanelText.set(more);
    }


    public void setData(HcCreditCard card) {
        this.cardData.set(card);
    }

    public void setListener(CreditCardDetailListener listener) {
        this.listener = listener;
    }

    public void onMorePanelToggled() {
        if (listener != null)
            listener.onShowMoreToggled(morePanelVisible.get());

        if (morePanelVisible.get()) {
            morePanelVisible.set(false);
            morePanelText.set(resourceService.getStringById(R.string.show_more));
            return;
        }
        morePanelVisible.set(true);
        morePanelText.set(resourceService.getStringById(R.string.show_less));
    }

    public void onStatementTapped() {
        if (listener != null) {
            HcCreditCard currentCard = cardData.get();
            if (currentCard == null || currentCard.refContract == null) {
                showMessage(R.string.data_not_found);
                return;
            }
            listener.onStatementTapped(currentCard.refContract.getContractNumber());
        }
    }

    public void onTransactionHistoryTapped() {
        if (listener != null) {
            HcCreditCard currentCard = cardData.get();
            if (currentCard == null || currentCard.refContract == null) {
                showMessage(R.string.data_not_found);
                return;
            }

            listener.onTransactionHistoryTapped(currentCard.refContract);
        }
    }

    public void onRepaymentHistoryTapped() {
        if (listener != null) {
            HcCreditCard currentCard = cardData.get();
            if (currentCard == null || currentCard.refContract == null) {
                showMessage(R.string.data_not_found);
                return;
            }

            listener.onRepaymentHistoryTapped(currentCard.refContract);
        }
    }

    public void onHoldTransactionTapped() {
        if (listener != null) {
            HcCreditCard currentCard = cardData.get();
            if (currentCard == null || currentCard.refContract == null) {
                showMessage(R.string.data_not_found);
                return;
            }

            listener.onHoldTransactionTapped(currentCard.refContract);
        }
    }

    public void onPaymentLocationTapped() {
        if (listener != null)
            listener.onPaymentLocationTapped();
    }
}
