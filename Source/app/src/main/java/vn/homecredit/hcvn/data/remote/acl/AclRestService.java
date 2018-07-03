/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/3/18 3:53 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.data.remote.acl;

import io.reactivex.Single;
import vn.homecredit.hcvn.data.model.OtpPassParam;
import vn.homecredit.hcvn.data.model.api.OtpTimerResp;
import vn.homecredit.hcvn.data.model.api.TokenResp;

public interface AclRestService {
    Single<OtpTimerResp> verifyPersonal(String phone, String idNumber, String playerId);
    Single<TokenResp> verifyPersonalOtp(OtpPassParam otpPassParam, String otp);
}
