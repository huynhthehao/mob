/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 8/7/18 7:00 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.ui.contract.creditcard.transaction;

import android.databinding.ObservableField;

import javax.inject.Inject;
import dagger.Module;

import vn.homecredit.hcvn.data.model.api.creditcard.Transaction;
import vn.homecredit.hcvn.data.model.api.creditcard.TransactionDetailLoading;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;


@Module
public class TransactionDetailViewModel extends BaseViewModel {

    private TransactionDetailLoading transactionDetailLoading;
    public ObservableField<Transaction> transaction = new ObservableField();

    @Inject
    public TransactionDetailViewModel(SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
    }


    public void setData(TransactionDetailLoading transactionDetailLoading) {
        this.transactionDetailLoading = transactionDetailLoading;

        if(transactionDetailLoading != null && transactionDetailLoading.transaction != null){
            transaction.set(transactionDetailLoading.transaction);
        }
    }
}
