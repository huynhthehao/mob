/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 8/6/18 11:33 AM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.data.model.enums;

public enum TransactionListType {
    History(0),
    Holding(1),
    RePayment(2);

    private int type;
    TransactionListType(int type){
        this.type = type;
    }
}
