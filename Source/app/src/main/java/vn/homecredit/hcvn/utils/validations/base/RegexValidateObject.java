/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/2/18 4:00 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.utils.validations.base;

import android.text.Editable;

public abstract class RegexValidateObject implements ValidationObject<String> {
    private final String mPattern;

    protected RegexValidateObject(String pattern) {
        mPattern = pattern;
    }

    public boolean validate(String value) {
        return value.matches(mPattern);
    }

    public boolean validate(Editable value) {
        return validate(value.toString());
    }

}
