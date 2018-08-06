/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/31/18 3:44 PM, by Admin
 */

package vn.homecredit.hcvn.ui.contract.creditcard.detail;

public interface CreditCardDetailListener {
    void onShowMoreToggled(boolean isShown);
    void onStatementTapped();
    void onTransactionHistoryTapped();
    void onRepaymentHistoryTapped();
    void onHoldTransactionTapped();
    void onPaymentLocationTapped();
}
