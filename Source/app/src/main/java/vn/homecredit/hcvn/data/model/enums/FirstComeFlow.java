/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/26/18 1:39 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.data.model.enums;

public enum FirstComeFlow {
    AFTER_LOGIN(1),
    AFTER_SIGNUP(2);

    private int value;

    FirstComeFlow(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static FirstComeFlow parse(int id) {
        for (FirstComeFlow type : values()) {
            if (type.getValue() == id) {
                return type;
            }
        }

        return FirstComeFlow.AFTER_LOGIN;
    }
}
