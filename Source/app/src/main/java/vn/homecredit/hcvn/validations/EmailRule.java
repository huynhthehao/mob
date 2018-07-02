/*
 * EmailRule.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/2/18 3:21 PM
 */

package vn.homecredit.hcvn.validations;

public class EmailRule implements ValidationRule<String> {

    private final String mErrorMessage;

    public EmailRule(String errorMessage) {
        mErrorMessage = errorMessage;
    }

    @Override
    public boolean Check(String value) {
        return false;
    }

    @Override
    public String getErrorMessage() {
        return mErrorMessage;
    }
}
