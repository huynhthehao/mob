/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/3/18 3:53 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.data.remote.acl;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.HashMap;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import vn.homecredit.hcvn.data.local.memory.MemoryHelper;
import vn.homecredit.hcvn.data.model.OtpPassParam;
import vn.homecredit.hcvn.data.model.api.OtpTimerResp;
import vn.homecredit.hcvn.data.model.api.TokenResp;
import vn.homecredit.hcvn.data.remote.ApiEndPoint;
import vn.homecredit.hcvn.data.remote.ApiHeader;

@Singleton
public class AclRestServiceImpl implements AclRestService {

    private final ApiHeader mApiHeader;
    private final MemoryHelper mMemoryHelper;

    @Inject
    public AclRestServiceImpl(ApiHeader apiHeader, MemoryHelper memoryHelper) {
        mApiHeader = apiHeader;
        mMemoryHelper = memoryHelper;
    }

    @Override
    public Single<OtpTimerResp> verifyPersonal(String phone, String idNumber, String playerId) {
        HashMap<String, String> requestHeader = new HashMap<String, String>();
        //TODO: ABC Token
        requestHeader.put("X-DEVICE-ID", "ABC");

        HashMap<String, String> requestBody = new HashMap<String, String>();
        requestBody.put("PhoneNumber", phone);
        requestBody.put("IdNumber", idNumber);
        requestBody.put("DeviceId", playerId);

        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_CLW + "/otp")
                .addHeaders(requestHeader)
                .addBodyParameter(requestBody)
                .build().getObjectSingle(OtpTimerResp.class);
    }

    @Override
    public Single<TokenResp> verifyPersonalOtp(OtpPassParam otpPassParam, String otp) {
        HashMap<String, String> requestHeader = new HashMap<String, String>();
        requestHeader.put("PhoneNumber", otpPassParam.getPhone());
        requestHeader.put("IdNumber", otpPassParam.getContract());
        //TODO: change abc to real device id
        requestHeader.put("DeviceId", "abc");
        requestHeader.put("Otp", otp);
        requestHeader.put("Source", otpPassParam.getOtpTimerResp().getData().getSource());

        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_CLW + "/otp/Validation")
                .addHeaders(requestHeader)
                .build().getObjectSingle(TokenResp.class);
    }
}
