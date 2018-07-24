/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/3/18 11:34 AM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.rules.acl;

import javax.inject.Inject;
import javax.inject.Singleton;

import vn.homecredit.hcvn.rules.base.BaseStringRule;
import vn.homecredit.hcvn.rules.base.StringRule;
import vn.homecredit.hcvn.utils.validations.IdNumberValidateObject;
import vn.homecredit.hcvn.utils.validations.MobilePhoneValidateObject;

@Singleton
public class AclRuleFactoryImpl implements AclRuleFactory {

    @Inject
    public AclRuleFactoryImpl() {
    }

    @Override
    public StringRule getPrimaryPhoneRule() {

        return new BaseStringRule("Số điện thoại trống hoặc không hợp lệ") {
            @Override
            public boolean stringIsValid(String s) {
                MobilePhoneValidateObject mobilePhoneRule = new MobilePhoneValidateObject();
                return mobilePhoneRule.validate(s.toString());
            }
        };
    }

    @Override
    public StringRule getIdNumberRule() {
        return new BaseStringRule("Số CMND trống hoặc không hợp lệ") {
            @Override
            public boolean stringIsValid(String s) {
                IdNumberValidateObject mobilePhoneRule = new IdNumberValidateObject();
                return mobilePhoneRule.validate(s.toString());
            }
        };
    }
}
