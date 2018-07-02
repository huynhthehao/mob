/*
 * ValidationRule.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/2/18 3:19 PM
 */

package vn.homecredit.hcvn.validations;

public interface ValidationRule<T> {
    boolean Check(T value);

    String getErrorMessage();
}
