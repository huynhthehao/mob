/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/3/18 3:53 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.data.remote.acl;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.HashMap;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import vn.homecredit.hcvn.data.local.memory.MemoryHelper;
import vn.homecredit.hcvn.data.local.prefs.PreferencesHelper;
import vn.homecredit.hcvn.data.model.OtpPassParam;
import vn.homecredit.hcvn.data.model.api.OtpTimerResp;
import vn.homecredit.hcvn.data.model.api.TokenResp;
import vn.homecredit.hcvn.data.model.api.acl.ProposeOfferResp;
import vn.homecredit.hcvn.data.model.api.acl.Request.ProposeOfferRequest;
import vn.homecredit.hcvn.data.model.api.acl.SuggestOfferResp;
import vn.homecredit.hcvn.data.remote.ApiEndPoint;
import vn.homecredit.hcvn.data.remote.ApiHeader;
import vn.homecredit.hcvn.service.DeviceInfo;

@Singleton
public class AclRestServiceImpl implements AclRestService {

    private final ApiHeader mApiHeader;
    private final MemoryHelper mMemoryHelper;
    private final DeviceInfo mDeviceInfo;
    private PreferencesHelper preferencesHelper;

    @Inject
    public AclRestServiceImpl(ApiHeader apiHeader, MemoryHelper memoryHelper, DeviceInfo deviceInfo, PreferencesHelper preferencesHelper) {
        mApiHeader = apiHeader;
        mMemoryHelper = memoryHelper;
        mDeviceInfo = deviceInfo;
        this.preferencesHelper = preferencesHelper;
    }

    @Override
    public Single<OtpTimerResp> verifyPersonal(String phone, String idNumber, String playerId) {
        HashMap<String, String> requestHeader = new HashMap<String, String>();
        // TODO: mDeviceInfo.getId()
        requestHeader.put("X-DEVICE-ID", mDeviceInfo.getPlayerId());

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
        HashMap<String, String> requestBody = new HashMap<String, String>();
        requestBody.put("PhoneNumber", otpPassParam.getPhoneNumber());
        requestBody.put("IdNumber", otpPassParam.getContractId());
        requestBody.put("DeviceId", mDeviceInfo.getPlayerId());
        requestBody.put("Otp", otp);
        requestBody.put("Source", otpPassParam.getOtpTimerResp().getData().getSource());
        String langId = Locale.getDefault().getLanguage();
        if (!langId.equals("vi") && !langId.equals("en")) {
            langId = "vi";
        }
        String url = String.format("%s/otp/Validation?lang=%s",ApiEndPoint.ENDPOINT_CLW, langId);
        return Rx2AndroidNetworking.post(url)
                .addBodyParameter(requestBody)
                .build().getObjectSingle(TokenResp.class);
    }


    @Override
    public Single<SuggestOfferResp> getSuggestOffer() {

        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_CLW + "/offer/all")
                .addHeaders(mApiHeader.getAclApiHeader())
                .build().getObjectSingle(SuggestOfferResp.class);
    }

    @Override
    public Single<ProposeOfferResp> getMonthlyPaymentAsync(double amount, int tenor, float boundScore, String productCode) {
        ProposeOfferRequest proposeOfferRequest = new ProposeOfferRequest(amount, tenor, productCode, boundScore);
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_CLW + "/offer/detail")
                .addHeaders(mApiHeader.getAclApiHeader())
                .addBodyParameter(proposeOfferRequest)
                .build().getObjectSingle(ProposeOfferResp.class);
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHeader;
    }
}
