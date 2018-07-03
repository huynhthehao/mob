/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/3/18 11:31 AM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.rules.acl;

import vn.homecredit.hcvn.rules.base.StringRule;

public interface AclRuleFactory {
    StringRule getPrimaryPhoneRule();
    StringRule getIdNumberRule();
}
