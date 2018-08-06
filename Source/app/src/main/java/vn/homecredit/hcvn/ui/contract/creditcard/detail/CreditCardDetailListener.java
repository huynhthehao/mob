/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/31/18 3:44 PM, by Admin
 */

package vn.homecredit.hcvn.ui.contract.creditcard.detail;

import vn.homecredit.hcvn.data.model.api.contract.HcContract;

public interface CreditCardDetailListener {
    void onShowMoreToggled(boolean isShown);
    void onStatementTapped();

    void onTransactionHistoryTapped(HcContract contract);
    void onRepaymentHistoryTapped(HcContract contract);
    void onHoldTransactionTapped(HcContract contract);

    void onPaymentLocationTapped();
}
