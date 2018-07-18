/*
 * AppDataManager.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/12/18 4:46 PM
 */

package vn.homecredit.hcvn.data;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import vn.homecredit.hcvn.data.local.memory.MemoryHelper;
import vn.homecredit.hcvn.data.local.prefs.PreferencesHelper;
import vn.homecredit.hcvn.data.model.api.ProfileResp;
import vn.homecredit.hcvn.data.model.api.TokenResp;
import vn.homecredit.hcvn.data.model.api.VersionResp;
import vn.homecredit.hcvn.data.remote.ApiHeader;
import vn.homecredit.hcvn.data.remote.RestService;
import vn.homecredit.hcvn.service.OneSignalService;

@Singleton
public class AppDataManager implements DataManager {

    private final PreferencesHelper mPreferencesHelper;
    private final RestService mRestService;
    private MemoryHelper mMemoryHelper;
    private final OneSignalService mOneSignalService;


    @Inject
    public AppDataManager(PreferencesHelper preferencesHelper, RestService restService, MemoryHelper memoryHelper, OneSignalService oneSignalService) {
        mPreferencesHelper = preferencesHelper;
        mRestService = restService;
        mMemoryHelper = memoryHelper;
        mOneSignalService = oneSignalService;
    }

    @Override
    public Single<VersionResp> checkUpdate() {
        return mRestService.checkUpdate();
    }

    @Override
    public Single<TokenResp> getToken(String phoneNumber, String password) {
        return mRestService.getToken(phoneNumber, password);
    }

    @Override
    public Single<ProfileResp> getProfile() {
        return mRestService.getProfile().doOnSuccess(response -> {
            if (response.getResponseCode() == 0) {
                mOneSignalService.SendTags("UserId", response.getData().getUserId());
                mOneSignalService.SendTags("UserName", response.getData().getFullName());
                //TODO: Notifcation Setting
//                mOneSignalService.SendTags("Active", Settings.Notification.ToString());
                mMemoryHelper.setProfileRespData(response.getData());
                //TODO: Set Badge
//                App.Current.SetBadge(resp.Data.NotificationCount);
            }
        });
    }

    @Override
    public ApiHeader getApiHeader() {
        return mRestService.getApiHeader();
    }

    @Override
    public VersionResp.VersionRespData getVersionRespData() {
//        return mMemoryHelper.getVersionRespData();
        return mPreferencesHelper.getVersionRespData();
    }

    @Override
    public void setVersionRespData(VersionResp.VersionRespData versionRespData) {
        mMemoryHelper.setVersionRespData(versionRespData);
        mPreferencesHelper.setVersionRespData(versionRespData);
    }

    @Override
    public boolean getIsShowDashboard() {
        return mPreferencesHelper.getIsShowDashboard();
    }

    @Override
    public void setIsShowDashboard(boolean value) {
        mPreferencesHelper.setIsShowDashboard(value);
    }

    @Override
    public void logout() {
        mPreferencesHelper.logout();
    }

    @Override
    public ProfileResp.ProfileRespData getProfileRespData() {
        return mMemoryHelper.getProfileRespData();
    }

    @Override
    public void setProfileRespData(ProfileResp.ProfileRespData profileRespData) {
        mMemoryHelper.setProfileRespData(profileRespData);
    }

    @Override
    public String getAccessToken() {
        return mPreferencesHelper.getAccessToken();
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPreferencesHelper.setAccessToken(accessToken);
        mRestService.getApiHeader().getProtectedApiHeader().setAccessToken(accessToken);
    }

    @Override
    public ProfileResp.ProfileRespData loadProfile() {
        return mPreferencesHelper.loadProfile();
    }

    @Override
    public void saveProfile(ProfileResp.ProfileRespData profileRespData) {
        mPreferencesHelper.saveProfile(profileRespData);
    }
}
