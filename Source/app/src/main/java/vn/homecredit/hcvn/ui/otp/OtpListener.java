/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/2/18 2:56 PM, by Admin
 */

package vn.homecredit.hcvn.ui.otp;

import vn.homecredit.hcvn.data.model.OtpPassParam;

public interface OtpListener {
    void onNext(OtpPassParam data);
}
