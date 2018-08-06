/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 8/6/18 11:40 AM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.ui.contract.creditcard.list;

import vn.homecredit.hcvn.data.model.api.HcCreditCard;

public interface CreditCardContractListener {
    void onCardTapped(HcCreditCard card);
}
