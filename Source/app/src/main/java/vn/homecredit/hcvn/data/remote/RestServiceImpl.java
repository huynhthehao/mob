/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 8/21/18 4:03 PM, by hien.nguyenm
 */

package vn.homecredit.hcvn.data.remote;

import android.text.TextUtils;
import android.util.Base64;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import vn.homecredit.hcvn.BuildConfig;
import vn.homecredit.hcvn.data.DefaultAndroidNetworking;
import vn.homecredit.hcvn.data.model.api.HcApiException;
import vn.homecredit.hcvn.data.model.api.OtpTimerResp;
import vn.homecredit.hcvn.data.model.api.ProfileResp;
import vn.homecredit.hcvn.data.model.api.TokenResp;
import vn.homecredit.hcvn.data.model.api.VersionResp;
import vn.homecredit.hcvn.data.model.api.base.BaseApiResponse;
import vn.homecredit.hcvn.data.model.api.contract.ContractResp;
import vn.homecredit.hcvn.data.model.api.contract.MasterContractDocResp;
import vn.homecredit.hcvn.data.model.api.contract.MasterContractResp;
import vn.homecredit.hcvn.data.model.api.contract.MasterContractVerifyResp;
import vn.homecredit.hcvn.data.model.api.contract.PaymentHistoryResp;
import vn.homecredit.hcvn.data.model.api.contract.ScheduleDetailResp;
import vn.homecredit.hcvn.data.model.api.creditcard.TransactionResp;
import vn.homecredit.hcvn.data.model.api.support.SupportHistoryResp;
import vn.homecredit.hcvn.data.model.api.support.SupportResp;
import vn.homecredit.hcvn.data.model.mapdata.model.clw.ClwModel;
import vn.homecredit.hcvn.data.model.mapdata.model.disbursement.DisbursementModel;
import vn.homecredit.hcvn.data.model.mapdata.model.payment.PaymentModel;
import vn.homecredit.hcvn.data.model.momo.RePaymentResp;
import vn.homecredit.hcvn.data.model.offer.ContractOfferResp;
import vn.homecredit.hcvn.data.model.offer.OfferDetailResp;
import vn.homecredit.hcvn.data.model.tracking.InternalTrackingModel;
import vn.homecredit.hcvn.data.model.tracking.TrackingResp;
import vn.homecredit.hcvn.helpers.prefs.PreferencesHelper;
import vn.homecredit.hcvn.service.DeviceInfo;
import vn.homecredit.hcvn.service.VersionService;
import vn.homecredit.hcvn.ui.contract.statement.model.StatementModel;
import vn.homecredit.hcvn.ui.contract.statement.model.StatementResp;
import vn.homecredit.hcvn.ui.contract.statement.statementdetails.model.StatementDetailsResp;
import vn.homecredit.hcvn.ui.notification.model.NotificationResp;
import vn.homecredit.hcvn.ui.payment.model.MakePaymentRequestValue;
import vn.homecredit.hcvn.ui.payment.model.MakePaymentResp;


@Singleton
public class RestServiceImpl implements RestService {
    public static final String RUNTIME_PLATFORM = "Android";
    private final boolean useMock;
    private ApiHeader mApiHeader;

    private DeviceInfo mDeviceInfo;
    private VersionService mVersionService;
    @Inject
    PreferencesHelper preferencesHelper;

    @Inject
    public RestServiceImpl(ApiHeader apiHeader, DeviceInfo deviceInfo, VersionService versionService) {
        mApiHeader = apiHeader;
        mDeviceInfo = deviceInfo;
        mVersionService = versionService;

        if (BuildConfig.DEBUG)
            useMock = false;
        else
            useMock = false;
    }

    public Single<VersionResp> checkUpdate() {
        HashMap<String, String> requestHeader = new HashMap<String, String>();
        //TODO: This code in Xamarin was commented. Consider to remove it later
        requestHeader.put("X-PLAYER-ID", mDeviceInfo.getPrivateId());
        requestHeader.put("X-DEVICE-VERSION", mDeviceInfo.getVersion());
        requestHeader.put("X-DEVICE-PLATFORM", RUNTIME_PLATFORM);
        requestHeader.put("X-DEVICE-MODEL", mDeviceInfo.getBrandModel());
        requestHeader.put("X-PUSH-TOKEN", mDeviceInfo.getPushToken());
        requestHeader.put("X-APP-VERSION", mVersionService.getVersion());

        return DefaultAndroidNetworking.getWithoutSubscribeOn(ApiEndPoint.ENDPOINT_APP + "/version?platform=2",
                requestHeader,
                VersionResp.class);
    }


    @Override
    public Single<TokenResp> getToken(String phoneNumber, String password) {
        VersionResp.VersionRespData versionRespData = preferencesHelper.getVersionRespData();
        if (versionRespData == null ||
                versionRespData.getSettings() == null ||
                versionRespData.getSettings().getOpenApiClientId() == null) {
            return Single.error(new Throwable("System Error"));
        }
        String s = String.format("OpenApi:%s", versionRespData.getSettings().getOpenApiClientId());
        byte[] b = s.getBytes(Charset.forName("UTF-8"));
        String authCode = Base64.encodeToString(b, android.util.Base64.DEFAULT);
        HashMap<String, String> requestHeader = new HashMap<>();
        requestHeader.put("Authorization", String.format("Basic %s", authCode.trim()));
        HashMap<String, String> requestBody = new HashMap<String, String>();
        requestBody.put("grant_type", "password");
        requestBody.put("username", phoneNumber);
        requestBody.put("password", password);
        requestBody.put("login_type", "direct");
        requestBody.put("lang", "vi");
        return DefaultAndroidNetworking.postWithoutSubscribeOn(ApiEndPoint.ENDPOINT_TOKEN, requestHeader, requestBody, TokenResp.class);
    }

    @Override
    public Single<ProfileResp> getProfile() {
        String url = buildUrl(ApiEndPoint.ENDPOINT_APP + "/customer/profile");
        return DefaultAndroidNetworking.getWithoutSubscribeOn(url,
                mApiHeader.getProtectedApiHeader(),
                ProfileResp.class);
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHeader;
    }

    @Override
    public Single<OtpTimerResp> verified(String username, String contractsId) {
        String url = buildUrl(ApiEndPoint.ENDPOINT_APP + "/customer/signup/verify?v=2");
        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("PhoneNumber", username);
        requestBody.put("ContractNumber", contractsId);
        HashMap<String, String> headers = new HashMap<>();
        if (mDeviceInfo != null) {
            headers.put("X-DEVICE-ID", mDeviceInfo.getId());
            headers.put("XX-DEVICE-ID", mDeviceInfo.getPrivateId());
        }
        return DefaultAndroidNetworking.postWithoutSubscribeOn(url, headers, requestBody, OtpTimerResp.class);
    }

    @Override
    public Single<OtpTimerResp> changePassword(String oldPassword, String newPassword) {
        String url = buildUrl(ApiEndPoint.ENDPOINT_APP + "/customer/changepassword");
        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("oldPassword", oldPassword);
        requestBody.put("password", newPassword);
        return DefaultAndroidNetworking.postWithoutSubscribeOn(url, mApiHeader.getProtectedApiHeader(), requestBody, OtpTimerResp.class);
    }

    @Override
    public Single<OtpTimerResp> forgetPasswordVerify(String phone, String contractId) {
        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("phoneNumber", phone);
        requestBody.put("contractNumber", contractId);
        String url = buildUrl(ApiEndPoint.ENDPOINT_APP + "/customer/forgotpassword/verify");
        return DefaultAndroidNetworking.postWithoutSubscribeOn(url, null, requestBody, OtpTimerResp.class);
    }

    @Override
    public Single<OtpTimerResp> forgetPasswordOTP(String phone, String contractId, String otp) {
        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("phoneNumber", phone);
        requestBody.put("contractNumber", contractId);
        requestBody.put("VerificationCode", otp);
        String url = buildUrl(ApiEndPoint.ENDPOINT_APP + "/customer/forgotpassword/verify/otp");
        return DefaultAndroidNetworking.postWithoutSubscribeOn(url, null, requestBody, OtpTimerResp.class);
    }

    @Override
    public Single<ProfileResp> signUp(String phone, String contractsId, String otp, String password) {
        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("phoneNumber", phone);
        requestBody.put("contractNumber", contractsId);
        requestBody.put("verificationCode", otp);
        requestBody.put("password", password);
        String url = buildUrl(ApiEndPoint.ENDPOINT_APP + "/customer/signup");

        HashMap<String, String> headers = new HashMap<>();
        if (mDeviceInfo != null) {
            headers.put("X-DEVICE-ID", mDeviceInfo.getId());
            headers.put("XX-DEVICE-ID", mDeviceInfo.getPrivateId());
        }

        return DefaultAndroidNetworking.postWithoutSubscribeOn(url, headers, requestBody, ProfileResp.class);
    }

    @Override
    public Single<ProfileResp> forgetPasswordSetNew(String phone, String contractsId, String otp, String password) {
        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("phoneNumber", phone);
        requestBody.put("contractNumber", contractsId);
        requestBody.put("verificationCode", otp);
        requestBody.put("password", password);
        String url = buildUrl(ApiEndPoint.ENDPOINT_APP + "/customer/forgotpassword");
        return DefaultAndroidNetworking.postWithoutSubscribeOn(url, null, requestBody, ProfileResp.class);
    }

    @Override
    public Single<NotificationResp> getNotifications() {
        String url = buildUrl(ApiEndPoint.ENDPOINT_APP + "/customer/notifications");
        return DefaultAndroidNetworking.getWithoutSubscribeOn(url, mApiHeader.getProtectedApiHeader(), NotificationResp.class);
    }

    @Override
    public Single<BaseApiResponse> markNotificationAsRead(String notificationId) {
        String url = buildUrl(ApiEndPoint.ENDPOINT_APP + "/notifications/read/" + notificationId);
        return DefaultAndroidNetworking.getWithoutSubscribeOn(url,
                mApiHeader.getProtectedApiHeader(),
                BaseApiResponse.class);
    }

    public Single<TransactionResp> getTransactions(String contractId, boolean isHold, boolean isRepay, int withinMonths) {
        String requestUrl = ApiEndPoint.ENDPOINT_APP + "/contracts/" + contractId + "/transactions?";

        if (isRepay)
            requestUrl += "isRepay=true";
        else if (isHold)
            requestUrl += "isHold=true";
        else {
            if (withinMonths < 1)
                withinMonths = 1;
            requestUrl += "withinMonths=" + String.valueOf(withinMonths);
        }

        requestUrl = buildUrl(requestUrl);
        return DefaultAndroidNetworking.get(requestUrl, mApiHeader.getProtectedApiHeader(), TransactionResp.class);
    }

    public Single<ContractResp> contract() {
        String url = buildUrl(ApiEndPoint.ENDPOINT_APP + "/customer/contracts");
        return DefaultAndroidNetworking.getWithoutSubscribeOn(url,
                mApiHeader.getProtectedApiHeader(),
                ContractResp.class);
    }

    @Override
    public Single<MasterContractResp> masterContract(String contractId) {
        String url = buildUrl(ApiEndPoint.ENDPOINT_APP + "/contracts/master/summary/" + contractId);
        return DefaultAndroidNetworking.getWithoutSubscribeOn(url,
                mApiHeader.getProtectedApiHeader(),
                MasterContractResp.class);
    }

    @Override
    public Single<MasterContractDocResp> masterContractDoc(String contractId) {
        String url = buildUrl(ApiEndPoint.ENDPOINT_APP + "/contracts/master/" + contractId + "/image");
        return DefaultAndroidNetworking.getWithoutSubscribeOn(url,
                mApiHeader.getProtectedApiHeader(),
                MasterContractDocResp.class);
    }

    @Override
    public Single<OtpTimerResp> masterContractApprove(String contractId) {
        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("contractNumber", contractId);
        String url = buildUrl(ApiEndPoint.ENDPOINT_APP + "/contracts/master/sign/accept?v=2");
        return DefaultAndroidNetworking.postWithoutSubscribeOn(url,
                mApiHeader.getProtectedApiHeader(),
                requestBody,
                OtpTimerResp.class);
    }

    @Override
    public Single<MasterContractVerifyResp> masterContractVerify(String contractId, String otp, boolean hasDisbursementBankAccount, boolean isCreditCardContract) {
        String url = buildUrl(ApiEndPoint.ENDPOINT_APP + "/contracts/master/sign/verify?v=2");
        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("ContractNumber", contractId);
        requestBody.put("OTP", otp);
        requestBody.put("HasDisbursementBankAccount", hasDisbursementBankAccount ? "true" : "false");
        requestBody.put("IsCreditCardContract", isCreditCardContract ? "true" : "false");
        return DefaultAndroidNetworking.post(url)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(requestBody)
                .build()
                .getObjectSingle(MasterContractVerifyResp.class)
                .onErrorResumeNext(throwable -> Single.error(new HcApiException(throwable, MasterContractVerifyResp.class)));
    }

    @Override
    public Single<RePaymentResp> getRePayment(String contractId) {
        String url = buildUrl(ApiEndPoint.ENDPOINT_APP + "/contracts/" + contractId + "/repayment");
        return DefaultAndroidNetworking.getWithoutSubscribeOn(url,
                mApiHeader.getProtectedApiHeader(),
                RePaymentResp.class);
    }

    @Override
    public Single<SupportResp> submitFeedback(String subject, String description, String phoneNumber, String contractId) {
        String url = buildUrl(ApiEndPoint.ENDPOINT_APP + "/tickets/send");
        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("Subject", subject);
        requestBody.put("Description", description);
        requestBody.put("PhoneNumber", phoneNumber);
        if (!TextUtils.isEmpty(contractId))
            requestBody.put("ContractNumber", contractId);

        return DefaultAndroidNetworking.postWithoutSubscribeOn(url,
                mApiHeader.getProtectedApiHeader(),
                requestBody,
                SupportResp.class);
    }

    @Override
    public Single<SupportHistoryResp> getSupportHistories() {
        return DefaultAndroidNetworking.get(buildUrl(RestUrl.SUPPORT_HISTORY),
                mApiHeader.getProtectedApiHeader(),
                SupportHistoryResp.class);
    }

    @Override
    public Single<ClwModel> getClwNear(Double lat, Double lon) {
        String url = buildUrl(ApiEndPoint.ENDPOINT_APP +
                "/clw/pos?" +
                "lng=" + lon +
                "&lat=" + lat);
        return DefaultAndroidNetworking.getWithoutSubscribeOn(url, mApiHeader.getPayooApiHeader(), ClwModel.class);
    }

    @Override
    public Single<DisbursementModel> getDisbursementNear(Double lat, Double lon) {
        String url = buildUrl(ApiEndPoint.ENDPOINT_APP +
                "/pos/disbursement?" +
                "lng=" + lon +
                "&lat=" + lat);
        return DefaultAndroidNetworking.getWithoutSubscribeOn(url,
                mApiHeader.getProtectedApiHeader(),
                DisbursementModel.class);
    }

    @Override
    public Single<PaymentModel> getPaymenttNear(Double lat, Double lon) {
        String url = buildUrl(ApiEndPoint.ENDPOINT_APP +
                "/pos/payment?" +
                "lng=" + lon +
                "&lat=" + lat + "&v=2");
        return DefaultAndroidNetworking.getWithoutSubscribeOn(url,
                mApiHeader.getProtectedApiHeader(),
                PaymentModel.class);
    }

    @Override
    public Single<ContractOfferResp> contractOffer(String campId) {
        String url = buildUrl(ApiEndPoint.ENDPOINT_APP + String.format("/offers/contracts?camp=%s", campId));
        return DefaultAndroidNetworking.getWithoutSubscribeOn(url,
                mApiHeader.getProtectedApiHeader(),
                ContractOfferResp.class);
    }

    @Override
    public Single<OfferDetailResp> offerFormula(String riskGroup, String productCode) {
        String url = buildUrl(ApiEndPoint.ENDPOINT_APP + String.format("/offers/formula?group=%s&product=%s", riskGroup, productCode));
        return DefaultAndroidNetworking.getWithoutSubscribeOn(url,
                mApiHeader.getProtectedApiHeader(),
                OfferDetailResp.class);
    }

    @Override
    public Single<TrackingResp> trackingAuthenticated(InternalTrackingModel data) {
        String url = buildUrl(ApiEndPoint.ENDPOINT_TRACKING + String.format("/data"));
        return DefaultAndroidNetworking.postWithoutSubscribeOn(url,
                mApiHeader.getProtectedApiHeader(),
                data,
                TrackingResp.class);
    }

    @Override
    public Single<TrackingResp> trackingAnonymous(InternalTrackingModel data) {
        String url = buildUrl(ApiEndPoint.ENDPOINT_TRACKING + String.format("/track"));
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "TrackingKey " + mDeviceInfo.trackingKey());
        if (mDeviceInfo != null && !TextUtils.isEmpty(mDeviceInfo.getId()))
            headers.put("X-DEVICE-ID", mDeviceInfo.getId());
        return DefaultAndroidNetworking.postWithoutSubscribeOn(url,
                headers,
                data,
                TrackingResp.class);
    }

    @Override
    public Single<ScheduleDetailResp> viewInstalmentsv1(String contractId) {
        String url = buildUrl(ApiEndPoint.ENDPOINT_APP + String.format("/contracts/%s/instalments", contractId));
        return DefaultAndroidNetworking.getWithoutSubscribeOn(url,
                mApiHeader.getProtectedApiHeader(),
                ScheduleDetailResp.class);
    }

    @Override
    public Single<PaymentHistoryResp> viewPaymentsv1(String contractId) {
        String url = buildUrl(ApiEndPoint.ENDPOINT_APP + String.format("/contracts/%s/payments", contractId));
        return DefaultAndroidNetworking.getWithoutSubscribeOn(url,
                mApiHeader.getProtectedApiHeader(),
                PaymentHistoryResp.class);
    }

    @Override
    public Single<StatementResp> getStatements(String contractId) {
        String url = buildUrl(ApiEndPoint.ENDPOINT_APP + String.format("/contracts/%s/statements", contractId));
        return DefaultAndroidNetworking.getWithoutSubscribeOn(url,
                mApiHeader.getProtectedApiHeader(),
                StatementResp.class);
    }

    @Override
    public Single<StatementDetailsResp> getStatementDetails(String contractId, StatementModel statementModel) {
        String url = buildUrl(ApiEndPoint.ENDPOINT_APP + String.format("/contracts/%s/statementimages", contractId) + "?key=" + statementModel.getKey()
                + "&id=" + statementModel.getId());
        return DefaultAndroidNetworking.getWithoutSubscribeOn(url,
                mApiHeader.getProtectedApiHeader(),
                StatementDetailsResp.class);
    }

    @Override
    public Single<OtpTimerResp> verifySignupOTP(String phone, String contractsId, String otp) {
        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("phoneNumber", phone);
        requestBody.put("contractNumber", contractsId);
        requestBody.put("verificationCode", otp);
        String url = buildUrl(ApiEndPoint.ENDPOINT_APP + "/customer/signup/verify/otp");
        HashMap<String, String> headers = new HashMap<>();
        if (mDeviceInfo != null && !TextUtils.isEmpty(mDeviceInfo.getId()))
            headers.put("X-DEVICE-ID", mDeviceInfo.getId());
        return DefaultAndroidNetworking.postWithoutSubscribeOn(url, headers, requestBody, OtpTimerResp.class);
    }

    @Override
    public Single<MakePaymentResp> makePaymentForMomo(MakePaymentRequestValue requestValue) {
        String url = buildUrl(ApiEndPoint.ENDPOINT_APP + "/contracts/" + requestValue.getContractNumber() + "/makepayment");
        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("rePaymentId", requestValue.getRePaymentId());
        requestBody.put("token", requestValue.getToken());
        requestBody.put("provider", requestValue.getProvider());
        requestBody.put("amount", requestValue.getAmount());
        requestBody.put("customerNumber", requestValue.getCustomerNumber());
        requestBody.put("contractNumber", requestValue.getContractNumber());

        return DefaultAndroidNetworking.postWithoutSubscribeOn(url,
                mApiHeader.getProtectedApiHeader(), requestBody,
                MakePaymentResp.class);
    }


    @Override
    public Map<String, String> getApiAuthHeaders(boolean includeDeviceId) {
        ApiHeader.ProtectedApiHeader protectedHeader = mApiHeader.getProtectedApiHeader();
        Map<String, String> headers = new HashMap<>();
        if (protectedHeader != null) {
            headers.put("AccessToken", protectedHeader.getAccessToken());
            headers.put("Authorization", protectedHeader.getAuthorization());
            if (includeDeviceId && mDeviceInfo != null) {
                headers.put("X-DEVICE-ID", mDeviceInfo.getId());
            }
        }
        return headers;
    }

    private String buildUrl(String url) {
        if (url == null) return url;
        if (url.contains("?")) {
            url += "&lang=" + preferencesHelper.getLanguageCode();
        } else {
            url += "?lang=" + preferencesHelper.getLanguageCode();
        }
        // platform = 2: Android
        url += "&platform=2";
        return url;
    }
}
