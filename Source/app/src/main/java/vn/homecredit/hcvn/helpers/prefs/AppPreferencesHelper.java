/*
 * AppPreferencesHelper.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/12/18 4:50 PM
 */

package vn.homecredit.hcvn.helpers.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.google.gson.Gson;

import javax.inject.Inject;

import vn.homecredit.hcvn.data.model.DeviceInfoModel;
import vn.homecredit.hcvn.data.model.LanguageCode;
import vn.homecredit.hcvn.data.model.api.ProfileResp;
import vn.homecredit.hcvn.data.model.api.VersionResp;
import vn.homecredit.hcvn.di.PreferenceInfo;
import vn.homecredit.hcvn.utils.StringUtils;

public class AppPreferencesHelper implements PreferencesHelper {

    // Do not edit these keys
    public static final String PREF_KEY_LOGGED_ON_INFO = "logged_on_user_key_";
    public static final String PREF_KEY_LOGGED_ON_USER = "logged_on_user";
    public static final String PREF_KEY_FINGERPRINT_ENABLE = "touch_id_enable_";
    public static final String PREF_KEY_FINGERPRINT_SETTING = "touch_id_setting_";
    public static final String PREF_KEY_CREDO_CONSENT_STATUS = "CREDO_CONSENT_STATUS_";


    private static final String PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN";
    private static final String PREF_KEY_PROFILE = "PREF_KEY_PROFILE";
    private static final String PREF_KEY_VERSIONRESP = "PREF_KEY_VERSIONRESP";
    private static final String PREF_KEY_SHOW_DASHBOARD = "PREF_KEY_SHOW_DASHBOARD";
    private static final String PREF_KEY_NOTIFICATION_SETTING = "PREF_KEY_NOTIFICATION_SETTING";
    private static final String PREF_KEY_LANGUAGE_CODE = "PREF_KEY_LANGUAGE_CODE";
    private static final String PREF_KEY_DEVICE_INFO = "PREF_KEY_DEVICE_INFO";
    private static final String PREF_KEY_LOGIN_TIMESTAMP = "PREF_KEY_LOGIN_TIMESTAMP";
    private static final String PREF_KEY_CURRENT_BADGE_COUNT = "PREF_KEY_CURRENT_BADGE_COUNT";
    private static final String PREF_KEY_IS_OPEN_NOTIFICATION_SCREEN = "PREF_KEY_IS_OPEN_NOTIFICATION_SCREEN";

    private final SharedPreferences mPrefs;
    private static String initFileName;
    private static Context initContext;

    @Inject
    public AppPreferencesHelper(Context context, @PreferenceInfo String prefFileName) {
        initFileName = prefFileName;
        initContext = context;
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public String getAccessToken() {
        return mPrefs.getString(PREF_KEY_ACCESS_TOKEN, null);
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPrefs.edit().putString(PREF_KEY_ACCESS_TOKEN, accessToken).apply();
    }

    @Override
    public ProfileResp.ProfileRespData getProfile() {
        return getObject(PREF_KEY_PROFILE, ProfileResp.ProfileRespData.class);
    }

    @Override
    public void saveProfile(ProfileResp.ProfileRespData profileRespData) {
        saveObject(PREF_KEY_PROFILE, profileRespData);
    }

    @Override
    public VersionResp.VersionRespData getVersionRespData() {
        return getObject(PREF_KEY_VERSIONRESP, VersionResp.VersionRespData.class);
    }

    @Override
    public void setVersionRespData(VersionResp.VersionRespData versionRespData) {
        saveObject(PREF_KEY_VERSIONRESP, versionRespData);
    }

    @Override
    public boolean getIsShowDashboard() {
        return mPrefs.getBoolean(PREF_KEY_SHOW_DASHBOARD, true);
    }

    @Override
    public void setIsShowDashboard(boolean value) {
        mPrefs.edit().putBoolean(PREF_KEY_SHOW_DASHBOARD, value).commit();
    }

    @Override
    public void logout() {
        mPrefs.edit().putBoolean(PREF_KEY_SHOW_DASHBOARD, true).commit();
        mPrefs.edit().putString(PREF_KEY_PROFILE, null).commit();
        mPrefs.edit().putString(PREF_KEY_ACCESS_TOKEN, null).commit();
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void changeLanguage() {
        String currentLangCode = getLanguageCode();
        String newLangCode = currentLangCode.equals(LanguageCode.VIETNAMESE)
                ? LanguageCode.ENGLISH
                : LanguageCode.VIETNAMESE;
        setLanguageCode(newLangCode);
    }


    @Override
    public void setLanguageCode(String languageId) {
        mPrefs.edit().putString(PREF_KEY_LANGUAGE_CODE, languageId).commit();
    }

    @Override
    public String getLanguageCode() {
        return getCurrentLanguageCode(mPrefs);
    }


    private static String getCurrentLanguageCode(SharedPreferences preferences) {
        // Comment cause just support vn getCurrentUTCTime, will open later when we support another languages.
        /*String langId = mPrefs.getString(PREF_KEY_LANGUAGE_CODE, "");

        if (langId != null && !langId.equals(""))
            return langId;

        langId = Locale.getDefault().getLanguage();
        if (!langId.equals(LanguageCode.VIETNAMESE) && !langId.equals(LanguageCode.ENGLISH)) {
            langId = LanguageCode.VIETNAMESE;
        }
        return langId;*/
        return LanguageCode.VIETNAMESE;
    }

    public static String getCurrentLanguageCode() {
        if (initContext == null || StringUtils.isNullOrEmpty(initFileName))
            return LanguageCode.VIETNAMESE;

        SharedPreferences preferences = initContext.getSharedPreferences(initFileName, Context.MODE_PRIVATE);
        return getCurrentLanguageCode(preferences);
    }

    @Override
    public boolean getNotificationSetting() {
        String currentUserPhoneNumber = getObject(AppPreferencesHelper.PREF_KEY_LOGGED_ON_USER, String.class);
        return mPrefs.getBoolean(PREF_KEY_NOTIFICATION_SETTING + currentUserPhoneNumber, true);
    }

    @Override
    public void setNotificationSetting(boolean isEnable) {
        String currentUserPhoneNumber = getObject(AppPreferencesHelper.PREF_KEY_LOGGED_ON_USER, String.class);
        mPrefs.edit().putBoolean(PREF_KEY_NOTIFICATION_SETTING + currentUserPhoneNumber, isEnable).commit();
    }

    @Override
    public void saveObject(String key, Object obj) {
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        prefsEditor.putString(key, json);
        prefsEditor.commit();
    }

    @Override
    public <T> T getObject(String key, Class<T> classType) {
        Gson gson = new Gson();
        String json = mPrefs.getString(key, null);
        if (StringUtils.isNullOrWhiteSpace(json)) {
            return null;
        }

        try {
            return gson.fromJson(json, classType);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public DeviceInfoModel getDeviceInfo() {
        return getObject(PREF_KEY_DEVICE_INFO, DeviceInfoModel.class);
    }

    @Override
    public void saveDeviceInfo(DeviceInfoModel deviceInfoModel) {
        saveObject(PREF_KEY_DEVICE_INFO, deviceInfoModel);
    }

    public boolean getFingerPrintSetting() {
        String currentUser = getObject(AppPreferencesHelper.PREF_KEY_LOGGED_ON_USER, String.class);
        String key = PREF_KEY_FINGERPRINT_SETTING + currentUser;
        return mPrefs.getBoolean(key, false);
    }


    @Override
    public void setFingerPrintSetting(boolean isEnable) {
        String currentUser = getObject(AppPreferencesHelper.PREF_KEY_LOGGED_ON_USER, String.class);
        String key = PREF_KEY_FINGERPRINT_SETTING + currentUser;
        mPrefs.edit().putBoolean(key, isEnable).commit();
    }


    @Override
    public void setFingerprintEnableStatus() {
        String currentUser = getObject(AppPreferencesHelper.PREF_KEY_LOGGED_ON_USER, String.class);
        String key = PREF_KEY_FINGERPRINT_ENABLE + currentUser;
        saveObject(key, "1");
    }

    @Override
    public String getFingerprintEnableStatus() {
        String currentUser = getObject(AppPreferencesHelper.PREF_KEY_LOGGED_ON_USER, String.class);
        String key = PREF_KEY_FINGERPRINT_ENABLE + currentUser;
        return getObject(key, String.class);
    }

    @Override
    public void setCredoConsentStatus() {
        String currentUser = getObject(AppPreferencesHelper.PREF_KEY_LOGGED_ON_USER, String.class);
        String key = PREF_KEY_CREDO_CONSENT_STATUS + currentUser;
        saveObject(key, "1");
    }

    @Override
    public String getCredoConsentStatus() {
        String currentUser = getObject(AppPreferencesHelper.PREF_KEY_LOGGED_ON_USER, String.class);
        String key = PREF_KEY_CREDO_CONSENT_STATUS + currentUser;
        return getObject(key, String.class);
    }

    @Override
    public void setCurrentBadgeCount(int currentBadgeCount) {
        mPrefs.edit().putInt(PREF_KEY_CURRENT_BADGE_COUNT, currentBadgeCount).commit();
    }

    @Override
    public int getCurrentBadgeCount() {
        return mPrefs.getInt(PREF_KEY_CURRENT_BADGE_COUNT, 0);
    }

    @Override
    public void setTimeLogin(long time) {
        mPrefs.edit().putLong(PREF_KEY_LOGIN_TIMESTAMP, time).commit();
    }

    @Override
    public long getTimeLogin() {
        return mPrefs.getLong(PREF_KEY_LOGIN_TIMESTAMP, 0);
    }

    @Override
    public void setIsOpenNotificationScreen(boolean isOpenNotificationScreen) {
        mPrefs.edit().putBoolean(PREF_KEY_IS_OPEN_NOTIFICATION_SCREEN, isOpenNotificationScreen).commit();
    }

    @Override
    public boolean isOpenNotificationScreen() {
        return mPrefs.getBoolean(PREF_KEY_IS_OPEN_NOTIFICATION_SCREEN, false);
    }
}
