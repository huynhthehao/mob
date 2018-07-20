package vn.homecredit.hcvn.data.repository;

import io.reactivex.Single;
import vn.homecredit.hcvn.data.model.api.OtpTimerResp;
import vn.homecredit.hcvn.data.model.api.ProfileResp;

public interface AccountRepository {

    Single<OtpTimerResp> signupVerify(String username, String contractsId);

    Single<OtpTimerResp> verifyOtpSignUp(String phone, String contractsId, String otp);

    Single<ProfileResp> signUp(String phone, String contractsId, String otp, String password);

    Single<ProfileResp> signIn(String phoneNumber, String password);

    Single<ProfileResp> getProfile();

    ProfileResp.ProfileRespData getCachedProfile();

    Single<OtpTimerResp> changePassword(String password, String newPassword) ;

    void updatePassword(String password) ;

}
