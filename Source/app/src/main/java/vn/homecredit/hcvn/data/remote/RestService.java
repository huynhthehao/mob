/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/6/18 4:57 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.data.remote;

import io.reactivex.Single;
import vn.homecredit.hcvn.data.model.api.OtpTimerResp;
import vn.homecredit.hcvn.data.model.api.ProfileResp;
import vn.homecredit.hcvn.data.model.api.TokenResp;
import vn.homecredit.hcvn.data.model.api.VersionResp;
import vn.homecredit.hcvn.data.model.api.base.BaseApiResponse;
import vn.homecredit.hcvn.data.model.api.contract.ContractResp;
import vn.homecredit.hcvn.data.model.api.contract.MasterContractDocResp;
import vn.homecredit.hcvn.data.model.api.contract.MasterContractResp;
import vn.homecredit.hcvn.data.model.api.contract.MasterContractVerifyResp;
import vn.homecredit.hcvn.ui.notification.model.NotificationResp;

public interface RestService {
    Single<VersionResp> checkUpdate();
    Single<TokenResp> getToken(String phoneNumber, String password);
    Single<ProfileResp> getProfile();
    Single<OtpTimerResp> verifySignupOTP(String phone, String idNumber, String otp);
    ApiHeader getApiHeader();
    Single<OtpTimerResp> verified(String username, String contractsId);
    Single<OtpTimerResp> changePassword(String oldPassword, String newPassword);
    Single<OtpTimerResp> forgetPasswordVerify(String phone, String contractId);
    Single<OtpTimerResp> forgetPasswordOTP(String phone, String contractId, String otp);
    Single<ProfileResp> signUp(String phone, String contracsId, String otp, String password);
    Single<ProfileResp> forgetPasswordSetNew(String phone, String contracsId, String otp, String password);
    Single<NotificationResp> getNotifications();
    Single<BaseApiResponse> markNotificationAsRead(String notificationId);

    Single<ContractResp> contract();
    Single<MasterContractResp> masterContract(String contractId);
    Single<MasterContractDocResp> masterContractDoc(String contractId);
    Single<OtpTimerResp> masterContractApprove(String contractId);
    Single<MasterContractVerifyResp> masterContractVerify(String contractId, String otp, boolean hasDisbursementBankAccount, boolean isCreditCardContract);
}
