/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/6/18 4:57 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.data.remote;

import io.reactivex.Single;
import vn.homecredit.hcvn.data.model.api.OtpTimerResp;
import vn.homecredit.hcvn.data.model.api.ProfileResp;
import vn.homecredit.hcvn.data.model.api.SignupResp;
import vn.homecredit.hcvn.data.model.api.TokenResp;
import vn.homecredit.hcvn.data.model.api.VersionResp;

public interface RestService {
    Single<VersionResp> checkUpdate();
    Single<TokenResp> getToken(String phoneNumber, String password);
    Single<ProfileResp> getProfile();
    Single<OtpTimerResp> verifySignupOTP(String phone, String idNumber, String otp);
    ApiHeader getApiHeader();
    Single<OtpTimerResp> verified(String username, String contractsId);
    Single<OtpTimerResp> changePassword(String oldPassword, String newPassword);
    Single<ProfileResp> signUp(String phone, String contracsId, String otp, String password);
}
