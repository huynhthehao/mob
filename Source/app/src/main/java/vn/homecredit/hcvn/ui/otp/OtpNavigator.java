/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/2/18 2:56 PM, by Admin
 */

package vn.homecredit.hcvn.ui.otp;

import android.support.annotation.IdRes;
import android.view.View;

import vn.homecredit.hcvn.ui.base.BaseNavigator;

public interface OtpNavigator extends BaseNavigator {
    <T extends View> T getControlById(@IdRes int viewId);
    void next();
}
