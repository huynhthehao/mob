/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 8/6/18 11:42 AM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.ui.contract.creditcard.transaction;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.Module;
import vn.homecredit.hcvn.data.model.api.Transaction;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;


@Module
public class TransactionListViewModel extends BaseViewModel {

    private Transaction.TransactionsLoading transactionsLoadingData;
    private TransactionItemListener listener;

    @Inject
    public TransactionListViewModel(SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
    }

    public void setListener(TransactionItemListener listener) {
        this.listener = listener;
    }

    public void setData(Context context, Transaction.TransactionsLoading transactionsLoading) {
        this.transactionsLoadingData = transactionsLoading;
    }


    public void onFilterMenuTapped(){
        if(this.listener != null){
            listener.onFilterMenuTapped();
        }
    }
}
