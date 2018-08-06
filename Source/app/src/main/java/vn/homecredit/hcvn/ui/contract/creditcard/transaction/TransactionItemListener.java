/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/31/18 3:44 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.ui.contract.creditcard.transaction;

import vn.homecredit.hcvn.data.model.api.Transaction;

public interface TransactionItemListener {
    void onTransactionTapped(Transaction transaction);
    void onFilterMenuTapped();
}
