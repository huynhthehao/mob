/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/31/18 3:41 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.ui.contract.creditcard;

import vn.homecredit.hcvn.data.model.api.HcCreditCard;

public interface CreditCardContractListener {
    void onCardClicked(HcCreditCard card);
}
