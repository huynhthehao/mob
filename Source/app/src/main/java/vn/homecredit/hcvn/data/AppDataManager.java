/*
 * AppDataManager.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/12/18 4:46 PM
 */

package vn.homecredit.hcvn.data;

import android.content.Context;

import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import vn.homecredit.hcvn.data.local.memory.MemoryHelper;
import vn.homecredit.hcvn.data.local.prefs.PreferencesHelper;
import vn.homecredit.hcvn.data.model.OtpPassParam;
import vn.homecredit.hcvn.data.model.api.OtpTimerResp;
import vn.homecredit.hcvn.data.model.api.TokenResp;
import vn.homecredit.hcvn.data.model.api.VersionResp;
import vn.homecredit.hcvn.data.remote.RestService;
import vn.homecredit.hcvn.data.remote.acl.AclRestService;

@Singleton
public class AppDataManager implements DataManager {
    private final Context mContext;
    private final PreferencesHelper mPreferencesHelper;
    private final RestService mRestService;
    private final Gson mGson;
    private MemoryHelper mMemoryHelper;
    private AclRestService mAclRestService;


    @Inject
    public AppDataManager(Context context, PreferencesHelper preferencesHelper, RestService restService, Gson gson, MemoryHelper memoryHelper, AclRestService aclRestService) {
        mContext = context;
        mPreferencesHelper = preferencesHelper;
        mRestService = restService;
        mGson = gson;
        mMemoryHelper = memoryHelper;
        mAclRestService = aclRestService;
    }

    @Override
    public Single<VersionResp> CheckUpdate() {
        return mRestService.CheckUpdate();
    }

    @Override
    public Single<TokenResp> GetToken(String phoneNumber, String password) {
        return mRestService.GetToken(phoneNumber, password);
    }

    @Override
    public VersionResp.VersionRespData getVersionRespData() {
        return mMemoryHelper.getVersionRespData();
    }

    @Override
    public void setVersionRespData(VersionResp.VersionRespData versionRespData) {
        mMemoryHelper.setVersionRespData(versionRespData);
    }

    @Override
    public Single<OtpTimerResp> verifyPersonal(String phone, String idNumber, String playerId) {
        return mAclRestService.verifyPersonal(phone, idNumber, playerId);
    }

    @Override
    public Single<TokenResp> verifyPersonalOtp(OtpPassParam otpPassParam, String otp) {
        return mAclRestService.verifyPersonalOtp(otpPassParam, otp);
    }
}
