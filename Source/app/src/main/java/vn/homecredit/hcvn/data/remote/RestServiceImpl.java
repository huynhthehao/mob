/*
 * RestServiceImpl.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/12/18 1:12 PM
 */

package vn.homecredit.hcvn.data.remote;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Base64;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import vn.homecredit.hcvn.data.local.memory.MemoryHelper;
import vn.homecredit.hcvn.data.model.api.TokenResp;
import vn.homecredit.hcvn.data.model.api.VersionResp;
import vn.homecredit.hcvn.data.model.api.base.BaseApiResponse;

@Singleton
public class RestServiceImpl implements RestService {

    private ApiHeader mApiHeader;

    private MemoryHelper mMemoryHelper;

    @Inject
    public RestServiceImpl(ApiHeader apiHeader, MemoryHelper memoryHelper) {
        mApiHeader = apiHeader;
        mMemoryHelper = memoryHelper;
    }

    public Single<VersionResp> CheckUpdate()
    {
        HashMap<String, String> requestHeader = new HashMap<String, String>();
        //TODO: change abc to real device id
        requestHeader.put("X-DEVICE-ID", "abc");
        requestHeader.put("X-PLAYER-ID", "abc");
        requestHeader.put("X-DEVICE-VERSION", "abc");
        requestHeader.put("X-DEVICE-PLATFORM", "abc");
        requestHeader.put("X-DEVICE-MODEL", "abc");
        requestHeader.put("X-PUSH-TOKEN", "abc");
        requestHeader.put("X-APP-VERSION", "a");

        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_APP + "/version?platform=2")
                .addHeaders(requestHeader)
                .build().getObjectSingle(VersionResp.class);
    }

    @Override
    public Single<TokenResp> GetToken(String phoneNumber, String password) {
        String s = String.format("OpenApi:%s", mMemoryHelper.getVersionRespData().getSettings().getOpenApiClientId());
        byte[] b = s.getBytes(Charset.forName("UTF-8"));
        String authCode = Base64.encodeToString(b, android.util.Base64.DEFAULT);

        HashMap<String, String> requestHeader = new HashMap<String, String>();
        requestHeader.put("Authorization", String.format("Basic %s", authCode.trim()));

        HashMap<String, String> requestBody = new HashMap<String, String>();
        requestBody.put("grant_type", "password");
        requestBody.put("username", phoneNumber);
        requestBody.put("password", password);
        requestBody.put("login_type", "direct");
        requestBody.put("lang", "vi");

        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_TOKEN)
                .addHeaders(requestHeader)
                .addBodyParameter(requestBody)
                .build().getObjectSingle(TokenResp.class);
    }
}
