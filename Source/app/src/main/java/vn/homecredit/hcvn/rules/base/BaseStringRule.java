/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/3/18 11:42 AM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.rules.base;

import android.text.Editable;

public abstract class BaseStringRule implements StringRule {
    public BaseStringRule(String errorMessage) {
        mErrorMessage = errorMessage;
    }

    private final String mErrorMessage;

    protected BaseStringRule() {
        mErrorMessage = "UNDEFINE_ERROR";
    }

    public String getErrorMessage() {
        return mErrorMessage;
    }

    @Override
    public boolean stringIsNotValid(String s) {
        return !this.stringIsValid(s);
    }
}
