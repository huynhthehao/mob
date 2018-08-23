/*
 * AppDataManager.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/12/18 4:46 PM
 */

package vn.homecredit.hcvn.data;

import android.app.NotificationManager;
import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import vn.homecredit.hcvn.database.AppDatabase;
import vn.homecredit.hcvn.helpers.fingerprint.FingerPrintHelper;
import vn.homecredit.hcvn.helpers.memory.MemoryHelper;
import vn.homecredit.hcvn.helpers.prefs.PreferencesHelper;
import vn.homecredit.hcvn.data.model.api.ProfileResp;
import vn.homecredit.hcvn.data.model.api.TokenResp;
import vn.homecredit.hcvn.data.model.api.VersionResp;
import vn.homecredit.hcvn.data.remote.ApiHeader;
import vn.homecredit.hcvn.data.remote.RestService;
import vn.homecredit.hcvn.service.OneSignalService;
import vn.homecredit.hcvn.utils.FingerPrintAuthValue;

@Singleton
public class AppDataManager implements DataManager {

    private final PreferencesHelper mPreferencesHelper;
    private final RestService mRestService;
    private final MemoryHelper mMemoryHelper;
    private final OneSignalService mOneSignalService;
    private final FingerPrintHelper mfingerPrintHelper;
    private AppDatabase appDatabase;


    @Inject
    public AppDataManager(PreferencesHelper preferencesHelper, RestService restService, MemoryHelper memoryHelper, OneSignalService oneSignalService, FingerPrintHelper fingerPrintHelper, AppDatabase appDatabase) {
        mPreferencesHelper = preferencesHelper;
        mRestService = restService;
        mMemoryHelper = memoryHelper;
        mOneSignalService = oneSignalService;
        mfingerPrintHelper = fingerPrintHelper;
        this.appDatabase = appDatabase;
    }

    @Override
    public Single<VersionResp> checkUpdate() {
        return mRestService.checkUpdate();
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
        // delete tags notification
        mOneSignalService.deleteTag("UserId");
        mOneSignalService.deleteTag("UserName");
        // delete all table in database
        Thread t = new Thread(() -> appDatabase.clearAllTables());
        t.start();
    }


    @Override
    public boolean getNotificationSetting() {
        return mPreferencesHelper.getNotificationSetting();
    }

    @Override
    public void setNotificationSetting(boolean isEnable) {
        mPreferencesHelper.setNotificationSetting(isEnable);
    }

    @Override
    public boolean getFingerPrintSetting() {
        return mPreferencesHelper.getFingerPrintSetting();
    }

    @Override
    public void setFingerPrintSetting(boolean isEnable) {
        mPreferencesHelper.setFingerPrintSetting(isEnable);
    }

    @Override
    public void setLanguageCode(String languageId) {
        mPreferencesHelper.setLanguageCode(languageId);
    }

    @Override
    public String getLanguageCode() {
        return mPreferencesHelper.getLanguageCode();
    }

    @Override
    public ProfileResp.ProfileRespData getProfileRespData() {
        return mPreferencesHelper.getProfile();
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
    public FingerPrintAuthValue getFingerPrintAuthValue() {
        return mfingerPrintHelper.getFingerPrintAuthValue();
    }
}
