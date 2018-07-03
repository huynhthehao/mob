/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/2/18 4:29 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.ui.acl.validation;

import vn.homecredit.hcvn.data.model.OtpPassParam;

public interface AclValidationNavigator {
    void showError(String errorMessage);

    void next();

    void openOtpActivity(OtpPassParam otpPassParam);
}