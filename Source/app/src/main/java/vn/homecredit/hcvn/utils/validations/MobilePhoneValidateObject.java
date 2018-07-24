/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/2/18 3:56 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.utils.validations;

import vn.homecredit.hcvn.utils.validations.base.RegexValidateObject;

public class MobilePhoneValidateObject extends RegexValidateObject {

    public static final String ID_NUMBER_PATTERN = "^0(([9,8](\\d){8})|([1](\\d){9}))$";

    public MobilePhoneValidateObject() {
        super(ID_NUMBER_PATTERN);
    }
}
