/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 8/3/18 8:48 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.ui.contract.creditcard.transaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.parceler.Parcels;

import javax.inject.Inject;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.api.creditcard.TransactionDetailLoading;
import vn.homecredit.hcvn.databinding.ActivityTransactionDetailBinding;
import vn.homecredit.hcvn.data.model.enums.TransactionListType;
import vn.homecredit.hcvn.ui.base.BaseActivity;

public class TransactionDetailActivity extends BaseActivity<ActivityTransactionDetailBinding, TransactionDetailViewModel>{

    public static final String BUNDLE_TRANSACTION_DETAIL_LOADING = "INPUT_TRANSACTION_DETAIL_LOADING";

    @Inject
    TransactionDetailViewModel viewModel;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_transaction_detail;
    }

    @Override
    public TransactionDetailViewModel getViewModel() {
        return viewModel;
    }

    public static Intent getNewIntent(Context context, TransactionDetailLoading transactionsLoading) {
        Intent newInstance = new Intent(context, TransactionDetailActivity.class);
        if (transactionsLoading != null) {
            Bundle currentBundle = new Bundle();
            currentBundle.putParcelable(BUNDLE_TRANSACTION_DETAIL_LOADING, Parcels.wrap(transactionsLoading));
            newInstance.putExtras(currentBundle);
        }
        return newInstance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewDataBinding().toolbar.setNavigationOnClickListener(v -> finish());

        if (getIntent().hasExtra(BUNDLE_TRANSACTION_DETAIL_LOADING)) {
            TransactionDetailLoading loadingData = Parcels.unwrap(getIntent().getParcelableExtra(BUNDLE_TRANSACTION_DETAIL_LOADING));
            viewModel.setData(loadingData);
            updateTitle(loadingData);
        }
    }

    private void updateTitle(TransactionDetailLoading loadingData){
        if(loadingData.listType != TransactionListType.History){
            if(loadingData.listType == TransactionListType.Holding)
                getViewDataBinding().toolbar.setTitle(R.string.hold_transactions_detail_title);
            else
                getViewDataBinding().toolbar.setTitle(R.string.repayment_detail_title);
        }else{
            getViewDataBinding().toolbar.setTitle(R.string.transaction_detail_title);
        }
    }
}
