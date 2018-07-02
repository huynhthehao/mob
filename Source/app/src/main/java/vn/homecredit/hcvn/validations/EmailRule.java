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
    private final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public EmailRule(String errorMessage) {
        mErrorMessage = errorMessage;
    }

    @Override
    public boolean Check(String value) {
        if (value.isEmpty())
            return false;
        return value.matches(emailPattern);
    }

    @Override
    public String getErrorMessage() {
        return mErrorMessage;
    }
}
