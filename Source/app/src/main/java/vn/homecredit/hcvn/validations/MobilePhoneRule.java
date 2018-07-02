/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/2/18 3:56 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.validations;

import vn.homecredit.hcvn.validations.base.RegexRule;

public class MobilePhoneRule extends RegexRule<String> {

    public static final String ID_NUMBER_PATTERN = "^0(([9,8](\\d){8})|([1](\\d){9}))$";

    public MobilePhoneRule(String errorMessage) {
        super(errorMessage, ID_NUMBER_PATTERN);
    }
}
