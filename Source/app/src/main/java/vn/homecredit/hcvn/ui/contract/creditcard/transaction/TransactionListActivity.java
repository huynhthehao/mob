/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 8/3/18 8:48 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.ui.contract.creditcard.transaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import org.parceler.Parcels;
import javax.inject.Inject;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.api.Transaction;
import vn.homecredit.hcvn.data.model.enums.TransactionListType;
import vn.homecredit.hcvn.databinding.ActivityTransactionListBinding;
import vn.homecredit.hcvn.ui.base.BaseActivity;

public class TransactionListActivity extends BaseActivity<ActivityTransactionListBinding, TransactionListViewModel> implements TransactionItemListener {

    public static final String BUNDLE_TRANSACTION_LOADING = "INPUT_TRANSACTION_LOADING";

    TransactionListViewAdapter transactionListViewAdapter;
    private PopupMenu filterMenu;

    @Inject
    TransactionListViewModel viewModel;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_transaction_list;
    }

    @Override
    public TransactionListViewModel getViewModel() {
        return viewModel;
    }

    public static Intent getNewIntent(Context context, Transaction.TransactionsLoading transactionsLoading) {
        Intent newInstance = new Intent(context, TransactionListActivity.class);
        if (transactionsLoading != null) {
            Bundle currentBundle = new Bundle();
            currentBundle.putParcelable(BUNDLE_TRANSACTION_LOADING, Parcels.wrap(transactionsLoading));
            newInstance.putExtras(currentBundle);
        }
        return newInstance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewDataBinding().toolbar.setNavigationOnClickListener(v -> finish());
        viewModel.setListener(this);

        if (getIntent().hasExtra(BUNDLE_TRANSACTION_LOADING)) {
            Transaction.TransactionsLoading loadingData = Parcels.unwrap(getIntent().getParcelableExtra(BUNDLE_TRANSACTION_LOADING));
            viewModel.setData(this, loadingData);
            //TODO: use this

            initFilterMenu(loadingData);
            initDataAdapter();
        }
    }

    private void initFilterMenu(Transaction.TransactionsLoading loadingData){
        ImageView menuIcon = findViewById(R.id.filterMenu);
        if(loadingData.getListType() != TransactionListType.History){
            if(loadingData.getListType() == TransactionListType.Holding)
                getViewDataBinding().toolbar.setTitle(R.string.hold_transactions_title);
            else
                getViewDataBinding().toolbar.setTitle(R.string.repayment_history_title);

            menuIcon.setVisibility(View.GONE);
            return;
        }else{
            getViewDataBinding().toolbar.setTitle(R.string.transaction_history_title);
        }

        filterMenu = new PopupMenu(this, menuIcon);
        filterMenu.inflate(R.menu.menu_transaction_filter);
        filterMenu.setOnMenuItemClickListener(item -> {

            //TODO: TBI
            showMessage(item.getItemId());
            return false;
        });
    }


    private void initDataAdapter() {
        transactionListViewAdapter = new TransactionListViewAdapter(this);
        RecyclerView recyclerView = getViewDataBinding().getRoot().findViewById(R.id.lvCreditCards);
        recyclerView.setAdapter(transactionListViewAdapter);
        transactionListViewAdapter.setListener(this);
        //transactionListViewAdapter.setNewData(transactions);
        SwipeRefreshLayout fresher = findViewById(R.id.fsCreditCardList);
        fresher.setEnabled(false);
    }


    @Override
    public void onTransactionTapped(Transaction transaction) {
        if (transaction == null) {
            showMessage(R.string.data_not_found);
            return;
        }

        //Intent intent = CreditCardDetailActivity.getNewIntent(this, card);
        //startActivity(intent);

    }

    @Override
    public void onFilterMenuTapped() {
        if(filterMenu != null){
            filterMenu.show();
        }
    }
}
