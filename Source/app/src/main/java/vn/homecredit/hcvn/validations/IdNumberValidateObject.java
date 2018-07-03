/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/2/18 3:58 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.validations;

import vn.homecredit.hcvn.validations.base.RegexValidateObject;

public class IdNumberValidateObject extends RegexValidateObject {

    public static final String ID_NUMBER_PATTERN = "^((\\d){9}|(\\d){12})$";

    public IdNumberValidateObject() {
        super(ID_NUMBER_PATTERN);
    }

}
