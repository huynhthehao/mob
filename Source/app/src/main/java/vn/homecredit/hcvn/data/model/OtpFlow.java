/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/5/18 9:06 AM, by Admin
 */

package vn.homecredit.hcvn.data.model;

public enum OtpFlow {
    SIGN_UP(1),
    FORGOT_PASSWORD(2),
    CHANGE_PASSWORD(4),
    MASTER_CONTRACT(8),
    MASTER_CONTRACT_CREDIT_CARD(16),
    CASH_LOAN_WALKIN(32);

    private int value;

    OtpFlow(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static OtpFlow parse(int id) {
        for (OtpFlow type : values()) {
            if (type.getValue() == id) {
                return type;
            }
        }

        return OtpFlow.SIGN_UP;
    }
}
