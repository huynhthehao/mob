/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/2/18 4:00 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.validations.base;

public abstract class RegexRule<S> implements ValidationRule<String> {
    private final String mErrorMessage;
    private final String mPattern;

    protected RegexRule(String errorMessage, String pattern) {
        mErrorMessage = errorMessage;
        mPattern = pattern;
    }

    public boolean Check(String value) {
        return false;
    }

    public String getErrorMessage()
    {
        return mErrorMessage;
    }
}
