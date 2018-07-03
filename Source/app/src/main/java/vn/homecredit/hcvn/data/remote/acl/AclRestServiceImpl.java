/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/3/18 3:53 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.data.remote.acl;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.HashMap;

import io.reactivex.Single;
import vn.homecredit.hcvn.data.model.OtpPassParam;
import vn.homecredit.hcvn.data.model.api.OtpTimerResp;
import vn.homecredit.hcvn.data.model.api.TokenResp;
import vn.homecredit.hcvn.data.remote.ApiEndPoint;

public class AclRestServiceImpl implements AclRestService {
    @Override
    public Single<OtpTimerResp> verifyPersonal(String phone, String idNumber, String playerId) {
        HashMap<String, String> requestHeader = new HashMap<String, String>();
        requestHeader.put("PhoneNumber", phone);
        requestHeader.put("IdNumber", idNumber);
        requestHeader.put("DeviceId", playerId);

        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_APP + "/api/clw/otp")
                .addHeaders(requestHeader)
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

        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_APP + "/api/clw/otp/Validation")
                .addHeaders(requestHeader)
                .build().getObjectSingle(TokenResp.class);
    }
}
