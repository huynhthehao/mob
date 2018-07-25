/*
 * RestServiceImpl.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/12/18 1:12 PM
 */

package vn.homecredit.hcvn.data.remote;

import android.text.TextUtils;
import android.util.Base64;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.nio.charset.Charset;
import java.util.HashMap;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import vn.homecredit.hcvn.BuildConfig;
import vn.homecredit.hcvn.data.DefaultAndroidNetworking;
import vn.homecredit.hcvn.data.model.api.contract.ContractDataResp;
import vn.homecredit.hcvn.data.model.api.contract.ContractResp;
import vn.homecredit.hcvn.helpers.memory.MemoryHelper;
import vn.homecredit.hcvn.helpers.prefs.PreferencesHelper;
import vn.homecredit.hcvn.data.model.api.HcApiException;
import vn.homecredit.hcvn.data.model.api.OtpTimerResp;
import vn.homecredit.hcvn.data.model.api.ProfileResp;
import vn.homecredit.hcvn.data.model.api.TokenResp;
import vn.homecredit.hcvn.data.model.api.VersionResp;
import vn.homecredit.hcvn.service.DeviceInfo;
import vn.homecredit.hcvn.service.OneSignalService;
import vn.homecredit.hcvn.service.VersionService;

@Singleton
public class RestServiceImpl implements RestService {

    public static final String RUNTIME_PLATFORM = "Android";
    private final boolean useMock;
    private ApiHeader mApiHeader;

    private MemoryHelper mMemoryHelper;
    private DeviceInfo mDeviceInfo;
    private VersionService mVersionService;
    private final OneSignalService mOneSignalService;
    @Inject
    PreferencesHelper preferencesHelper;

    @Inject
    public RestServiceImpl(ApiHeader apiHeader, MemoryHelper memoryHelper, DeviceInfo deviceInfo, VersionService versionService, OneSignalService oneSignalService) {
        mApiHeader = apiHeader;
        mMemoryHelper = memoryHelper;
        mDeviceInfo = deviceInfo;
        mVersionService = versionService;
        mOneSignalService = oneSignalService;

        if (BuildConfig.DEBUG)
            useMock = false;
        else
            useMock = false;
    }

    public Single<VersionResp> checkUpdate() {
        if (TextUtils.isEmpty(mDeviceInfo.getPlayerId())) {
            mOneSignalService.tryGetPlayerId();
        }

        HashMap<String, String> requestHeader = new HashMap<String, String>();
        //TODO: This code in Xamarin was commented. Consider to remove it later
        requestHeader.put("X-PLAYER-ID", mDeviceInfo.getPlayerId());
        requestHeader.put("X-DEVICE-VERSION", mDeviceInfo.getVersion());
        requestHeader.put("X-DEVICE-PLATFORM", RUNTIME_PLATFORM);
        requestHeader.put("X-DEVICE-MODEL", mDeviceInfo.getBrandModel());
        requestHeader.put("X-PUSH-TOKEN", mDeviceInfo.getPushToken());
        requestHeader.put("X-APP-VERSION", mVersionService.getVersion());

        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_APP + "/version?platform=2")
                .addHeaders(requestHeader)
                .build().getObjectSingle(VersionResp.class);
    }

    @Override
    public Single<TokenResp> getToken(String phoneNumber, String password) {

        String s = String.format("OpenApi:%s", mMemoryHelper.getVersionRespData().getSettings().getOpenApiClientId());
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

        if (useMock)
            requestBody.put("isMock", "true");

        return DefaultAndroidNetworking.post(ApiEndPoint.ENDPOINT_TOKEN, requestHeader, requestBody, TokenResp.class);
    }

    @Override
    public Single<ProfileResp> getProfile() {
        return DefaultAndroidNetworking.get(ApiEndPoint.ENDPOINT_APP + "/customer/profile?",
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
        return Rx2AndroidNetworking.post(url)
                .addBodyParameter(requestBody)
                .build()
                .getObjectSingle(OtpTimerResp.class)
                .onErrorResumeNext(throwable -> Single.error(new HcApiException(throwable, OtpTimerResp.class)))
                ;
    }

    @Override
    public Single<OtpTimerResp> changePassword(String oldPassword, String newPassword) {
        String url = buildUrl(ApiEndPoint.ENDPOINT_APP + "/customer/changepassword");
        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("oldPassword", oldPassword);
        requestBody.put("password", newPassword);
        return Rx2AndroidNetworking.post(url)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(requestBody)
                .build()
                .getObjectSingle(OtpTimerResp.class);
    }

    @Override
    public Single<OtpTimerResp> forgetPasswordVerify(String phone, String contractId) {
        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("phoneNumber", phone);
        requestBody.put("contractNumber", contractId);
        String url = buildUrl(ApiEndPoint.ENDPOINT_APP + "/customer/forgotpassword/verify");
        return Rx2AndroidNetworking.post(url)
                .addBodyParameter(requestBody)
                .build()
                .getObjectSingle(OtpTimerResp.class)
                .onErrorResumeNext(throwable -> Single.error(new HcApiException(throwable, OtpTimerResp.class)))
                ;
    }

    @Override
    public Single<OtpTimerResp> forgetPasswordOTP(String phone, String contractId, String otp) {
        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("phoneNumber", phone);
        requestBody.put("contractNumber", contractId);
        requestBody.put("VerificationCode", otp);
        String url = buildUrl(ApiEndPoint.ENDPOINT_APP + "/customer/forgotpassword/verify/otp");
        return Rx2AndroidNetworking.post(url)
                .addBodyParameter(requestBody)
                .build()
                .getObjectSingle(OtpTimerResp.class)
                .onErrorResumeNext(throwable -> Single.error(new HcApiException(throwable, OtpTimerResp.class)));
    }

    @Override
    public Single<ProfileResp> signUp(String phone, String contractsId, String otp, String password) {
        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("phoneNumber", phone);
        requestBody.put("contractNumber", contractsId);
        requestBody.put("verificationCode", otp);
        requestBody.put("password", password);
        String url = buildUrl(ApiEndPoint.ENDPOINT_APP + "/customer/signup");
        return Rx2AndroidNetworking.post(url)
                .addBodyParameter(requestBody)
                .build()
                .getObjectSingle(ProfileResp.class)
                .onErrorResumeNext(throwable -> Single.error(new HcApiException(throwable, ProfileResp.class)));
    }

    @Override
    public Single<ProfileResp> forgetPasswordSetNew(String phone, String contractsId, String otp, String password) {
        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("phoneNumber", phone);
        requestBody.put("contractNumber", contractsId);
        requestBody.put("verificationCode", otp);
        requestBody.put("password", password);
        String url = buildUrl(ApiEndPoint.ENDPOINT_APP + "/customer/forgotpassword");
        return Rx2AndroidNetworking.post(url)
                .addBodyParameter(requestBody)
                .build()
                .getObjectSingle(ProfileResp.class);
    }

    @Override
    public Single<ContractResp> contract() {
        String url = buildUrl(ApiEndPoint.ENDPOINT_APP + "/customer/contracts");
        return Rx2AndroidNetworking.get(url)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(ContractResp.class);
    }

    @Override
    public Single<OtpTimerResp> verifySignupOTP(String phone, String contractsId, String otp) {
        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("phoneNumber", phone);
        requestBody.put("contractNumber", contractsId);
        requestBody.put("verificationCode", otp);
        String url = buildUrl(ApiEndPoint.ENDPOINT_APP + "/customer/signup/verify/otp");
        return Rx2AndroidNetworking.post(url)
                .addBodyParameter(requestBody)
                .build()
                .getObjectSingle(OtpTimerResp.class);
    }

    private String buildUrl(String url) {
        if (url == null) return url;
        if (url.contains("?")) {
            url += "&lang=" + preferencesHelper.getLanguageCode();
        }else {
            url += "?lang=" + preferencesHelper.getLanguageCode();
        }
        return url;
    }


}
