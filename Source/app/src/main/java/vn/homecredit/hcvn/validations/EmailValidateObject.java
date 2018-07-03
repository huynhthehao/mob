/*
 * EmailRule.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/2/18 3:21 PM
 */

package vn.homecredit.hcvn.validations;

import vn.homecredit.hcvn.validations.base.RegexValidateObject;

public class EmailValidateObject extends RegexValidateObject {

    public static final String EMAIL_PATTERN = "([\\w\\.\\-_]+)?\\w+@[\\w-_]+(\\.\\w+){1,}";

    public EmailValidateObject() {
        super(EMAIL_PATTERN);
    }

}
