/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/31/18 3:44 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.ui.contract.creditcard.transaction;

import java.util.List;

import vn.homecredit.hcvn.data.model.api.creditcard.Transaction;

public interface TransactionItemListener {
    void setLoadingStatus(boolean isShown);
    void onDataRefreshed(List<Transaction> transactions);
    void onTransactionTapped(Transaction transaction);
    void onFilterMenuTapped();
}
