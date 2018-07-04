/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/3/18 11:31 AM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.rules.base;

import android.text.Editable;

public interface StringRule {
    boolean stringIsValid(String s);

    boolean stringIsNotValid(String s);

    String getErrorMessage();
}
