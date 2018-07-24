/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/2/18 3:28 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.utils.validations.base;

public interface ValidationObject<T> {
    boolean validate(T value);
}
