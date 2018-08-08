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
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.api.creditcard.Transaction;
import vn.homecredit.hcvn.data.model.api.creditcard.TransactionDetailLoading;
import vn.homecredit.hcvn.data.model.api.creditcard.TransactionsLoading;
import vn.homecredit.hcvn.data.model.enums.TransactionListType;
import vn.homecredit.hcvn.databinding.ActivityTransactionListBinding;
import vn.homecredit.hcvn.ui.base.BaseActivity;

public class TransactionListActivity extends BaseActivity<ActivityTransactionListBinding, TransactionListViewModel> implements TransactionItemListener {

    public static final String BUNDLE_TRANSACTION_LOADING = "INPUT_TRANSACTION_LOADING";

    TransactionListViewAdapter transactionListViewAdapter;
    private PopupMenu filterMenu;
    private TransactionsLoading loadingData;
    private int currentMenuId = R.id.menuItemInOneMonth;

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

    public static Intent getNewIntent(Context context, TransactionsLoading transactionsLoading) {
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
            loadingData = Parcels.unwrap(getIntent().getParcelableExtra(BUNDLE_TRANSACTION_LOADING));
            viewModel.setData(this, loadingData);
            //TODO: use this

            initFilterMenu(loadingData);
            initDataAdapter();
        }
    }

    private void initFilterMenu(TransactionsLoading loadingData){
        ImageView menuIcon = findViewById(R.id.filterMenu);

        if(loadingData.listType != TransactionListType.History){
            if(loadingData.listType == TransactionListType.Holding)
                getViewDataBinding().toolbar.setTitle(R.string.hold_transactions_title);
            else
                getViewDataBinding().toolbar.setTitle(R.string.repayment_history_title);

            menuIcon.setVisibility(View.GONE);
            return;
        }else{
            getViewDataBinding().toolbar.setTitle(R.string.transaction_history_title);
        }

        filterMenu = new PopupMenu(this, menuIcon);
        filterMenu.getMenuInflater().inflate(R.menu.menu_transaction_filter, filterMenu.getMenu());
        filterMenu.setOnMenuItemClickListener(item -> {
            this.currentMenuId = item.getItemId();
            viewModel.setSearchRange(currentMenuId);
            return true;
        });
    }


    private void initDataAdapter() {
        transactionListViewAdapter = new TransactionListViewAdapter(this);
        RecyclerView recyclerView = getViewDataBinding().getRoot().findViewById(R.id.lvTransactions);
        recyclerView.setAdapter(transactionListViewAdapter);

        transactionListViewAdapter.setListener(this);
    }


    @Override
    public void setLoadingStatus(boolean isShown) {
        SwipeRefreshLayout fresher = findViewById(R.id.fsTransactionList);
        fresher.setRefreshing(isShown);
    }

    @Override
    public void onDataRefreshed(List<Transaction> transactions) {
        transactionListViewAdapter.setNewData(transactions);
    }

    @Override
    public void onTransactionTapped(Transaction transaction) {
        if (transaction == null || loadingData == null) {
            showMessage(R.string.data_not_found);
            return;
        }

        TransactionDetailLoading detailLoadingData = new TransactionDetailLoading(transaction,loadingData.listType);
        Intent intent = TransactionDetailActivity.getNewIntent(this, detailLoadingData);
        startActivity(intent);
    }


    @Override
    public void onFilterMenuTapped() {
        if(filterMenu != null){
            ImageView menuIcon = findViewById(R.id.filterMenu);
            MenuPopupHelper menuHelper = new MenuPopupHelper(this, (MenuBuilder) filterMenu.getMenu(), menuIcon);
            menuHelper.setForceShowIcon(true);
            menuHelper.setGravity(Gravity.END);

            for (MenuItem item:((MenuBuilder) filterMenu.getMenu()).getVisibleItems()){
                if(item.getItemId() == currentMenuId)
                    item.setIcon(R.drawable.ic_check_red);
                else
                    item.setIcon(null);
            }

            menuHelper.show();
        }
    }
}
