/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/5/18 9:06 AM, by Admin
 */

package vn.homecredit.hcvn.data.model;

public enum OtpFlow {
    SignUp(1),
    ForgotPassword(2),
    ChangePassword(4),
    MasterContract(8),
    MasterContractCreditCard(16),
    CashLoanWalkin(32);

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

        return OtpFlow.SignUp;
    }
}
