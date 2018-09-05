/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/2/18 2:56 PM, by Admin
 */

package vn.homecredit.hcvn.ui.payment.momo.payothers;

import vn.homecredit.hcvn.data.model.momo.RePaymentData;

public interface PayOthersListener {
    void onNext(RePaymentData rePaymentData);
    void onContractHelp(String supportPhoneNumber);
}
