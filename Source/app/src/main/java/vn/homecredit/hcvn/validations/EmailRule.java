/*
 * EmailRule.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/2/18 3:21 PM
 */

package vn.homecredit.hcvn.validations;

import vn.homecredit.hcvn.validations.base.RegexRule;

public class EmailRule extends RegexRule<String> {

    public static final String EMAIL_PATTERN = "([\\w\\.\\-_]+)?\\w+@[\\w-_]+(\\.\\w+){1,}";

    public EmailRule(String errorMessage) {
        super(errorMessage, EMAIL_PATTERN);
    }

}
