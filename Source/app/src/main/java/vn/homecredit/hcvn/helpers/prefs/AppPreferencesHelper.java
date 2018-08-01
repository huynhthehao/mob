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

import com.google.gson.Gson;

import java.util.Locale;
import javax.inject.Inject;

import vn.homecredit.hcvn.data.model.DeviceInfoModel;
import vn.homecredit.hcvn.data.model.api.ProfileResp;
import vn.homecredit.hcvn.data.model.api.VersionResp;
import vn.homecredit.hcvn.di.PreferenceInfo;
import vn.homecredit.hcvn.utils.StringUtils;

public class AppPreferencesHelper implements PreferencesHelper {

    // Do not edit these keys
    public static final String PREF_KEY_LOGGED_ON_INFO = "logged_on_user_key_";
    public static final String PREF_KEY_LOGGED_ON_User = "logged_on_user";


    private static final String PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN";
    private static final String PREF_KEY_PROFILE = "PREF_KEY_PROFILE";
    private static final String PREF_KEY_VERSIONRESP = "PREF_KEY_VERSIONRESP";
    private static final String PREF_KEY_SHOW_DASHBOARD = "PREF_KEY_SHOW_DASHBOARD";
    private static final String PREF_KEY_NOTIFICATION_SETTING = "PREF_KEY_NOTIFICATION_SETTING";
    private static final String PREF_KEY_FINGER_PRINT_ENABLE = "PREF_KEY_FINGER_PRINT_ENABLE";
    private static final String PREF_KEY_LANGUAGE_CODE = "PREF_KEY_LANGUAGE_CODE";
    private static final String PREF_KEY_DEVICE_INFO = "PREF_KEY_DEVICE_INFO";

    private final SharedPreferences mPrefs;

    @Inject
    public AppPreferencesHelper(Context context, @PreferenceInfo String prefFileName) {
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

    @Override
    public void setLanguageCode(String languageId) {
        mPrefs.edit().putString(PREF_KEY_LANGUAGE_CODE, languageId).commit();
    }

    @Override
    public String getLanguageCode() {
        String langId = mPrefs.getString(PREF_KEY_LANGUAGE_CODE, "");

        if (langId != null && !langId.equals(""))
            return langId;

        langId = Locale.getDefault().getLanguage();
        if (!langId.equals("vi") && !langId.equals("en")) {
            langId = "vi";
        }
        return langId;
    }


    @Override
    public boolean getNotificationSetting() {
        return mPrefs.getBoolean(PREF_KEY_NOTIFICATION_SETTING, true);
    }

    @Override
    public void setNotificationSetting(boolean isEnable) {
        mPrefs.edit().putBoolean(PREF_KEY_NOTIFICATION_SETTING, isEnable).commit();
    }

    @Override
    public boolean getFingerPrintSetting() {
        return mPrefs.getBoolean(PREF_KEY_FINGER_PRINT_ENABLE, false);
    }

    @Override
    public void setFingerPrintSetting(boolean isEnable) {
        mPrefs.edit().putBoolean(PREF_KEY_FINGER_PRINT_ENABLE, isEnable).commit();
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
        }catch(Exception ex) {
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
}
