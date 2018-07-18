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
import vn.homecredit.hcvn.data.local.memory.MemoryHelper;
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
        if (TextUtils.isEmpty(mDeviceInfo.getPlayerId()))
        {
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

        return DefaultAndroidNetworking.get(ApiEndPoint.ENDPOINT_APP + "/version?platform=2",
                requestHeader,
                VersionResp.class);
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
}
