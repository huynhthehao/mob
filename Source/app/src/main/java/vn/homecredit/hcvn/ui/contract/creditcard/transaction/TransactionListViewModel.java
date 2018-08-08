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
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.api.creditcard.TransactionsLoading;
import vn.homecredit.hcvn.data.model.enums.TransactionListType;
import vn.homecredit.hcvn.data.remote.RestService;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;


@Module
public class TransactionListViewModel extends BaseViewModel {

    private final RestService restService;
    private TransactionsLoading transactionsLoadingData;
    private TransactionItemListener listener;
    private int searchRange = 1;
    private List<Integer> searchrangeIds = new ArrayList<>();

    @Inject
    public TransactionListViewModel(SchedulerProvider schedulerProvider, RestService restService) {
        super(schedulerProvider);
        this.restService = restService;
        searchrangeIds.add(0);
        searchrangeIds.add(R.id.menuItemInOneMonth);
        searchrangeIds.add(0);
        searchrangeIds.add(R.id.menuItemInThreeMonths);
        searchrangeIds.add(0);
        searchrangeIds.add(0);
        searchrangeIds.add(R.id.menuItemInSixMonths);
    }

    public void setListener(TransactionItemListener listener) {
        this.listener = listener;
    }

    public void setData(Context context, TransactionsLoading transactionsLoading) {
        this.transactionsLoadingData = transactionsLoading;
        getTransactionṣ̣();
    }

    public void setSearchRange(int rangeId) {
        int range = searchrangeIds.indexOf(rangeId);
        if (range < 0)
            range = 1;

        if (this.searchRange != range) {
            this.searchRange = range;
            getTransactionṣ̣();
        }
    }


    public void onFilterMenuTapped() {
        if (this.listener != null) {
            listener.onFilterMenuTapped();
        }
    }

    @Override
    public void init() {
        super.init();

        updateLoadingStatus(false);
    }

    private void updateLoadingStatus(boolean isShown) {
        if (listener != null)
            listener.setLoadingStatus(isShown);
    }

    private void getTransactionṣ̣() {
        updateLoadingStatus(true);

        if (transactionsLoadingData == null || transactionsLoadingData.contract == null) {
            showMessage(R.string.data_not_found);
            return;
        }

        String contractId = transactionsLoadingData.contract.getContractNumber();
        boolean isHold = transactionsLoadingData.listType == TransactionListType.Holding;
        boolean isRepay = transactionsLoadingData.listType == TransactionListType.RePayment;

        startSafeProcess(restService.getTransactions(contractId, isHold, isRepay, searchRange).subscribe(resp -> {
            updateLoadingStatus(false);

            if (listener != null)
                listener.onDataRefreshed(resp.getData());

            /*if (resp.getData() == null || resp.getData().size() < 1)
                showMessage(R.string.data_not_found);*/
        }, throwable -> {
            updateLoadingStatus(false);
            handleError(throwable);
        }));
    }

    public void onRefresh() {
        getTransactionṣ̣();
    }
}
