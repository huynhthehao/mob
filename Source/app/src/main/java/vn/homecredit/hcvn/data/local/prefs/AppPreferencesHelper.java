/*
 * AppPreferencesHelper.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/12/18 4:50 PM
 */

package vn.homecredit.hcvn.data.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import javax.inject.Inject;

import vn.homecredit.hcvn.data.model.api.ProfileResp;
import vn.homecredit.hcvn.data.model.api.VersionResp;
import vn.homecredit.hcvn.di.PreferenceInfo;

public class AppPreferencesHelper implements PreferencesHelper {
    private static final String PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN";
    private static final String PREF_KEY_PROFILE = "PREF_KEY_PROFILE";
    private static final String PREF_KEY_VERSIONRESP = "PREF_KEY_VERSIONRESP";
    private static final String PREF_KEY_SHOW_DASHBOARD = "PREF_KEY_SHOW_DASHBOARD";
    //private static final String FINGER_PRINT_ENABLE_KEY = "FingerPrintEnable";
    //private static final String CURRENT_LANGUAGE_KEY = "CurrentLanguage";

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
    public ProfileResp.ProfileRespData loadProfile() {
        Gson gson = new Gson();
        String json = mPrefs.getString(PREF_KEY_PROFILE, "");
        return gson.fromJson(json, ProfileResp.ProfileRespData.class);
    }

    @Override
    public void saveProfile(ProfileResp.ProfileRespData profileRespData) {
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(profileRespData); // myObject - instance of MyObject
        prefsEditor.putString(PREF_KEY_PROFILE, json);
        prefsEditor.commit();
    }

    @Override
    public VersionResp.VersionRespData getVersionRespData() {
        Gson gson = new Gson();
        String json = mPrefs.getString(PREF_KEY_VERSIONRESP, null);
        if (json == null) {
            return null;
        }
        return gson.fromJson(json, VersionResp.VersionRespData.class);
    }

    @Override
    public void setVersionRespData(VersionResp.VersionRespData versionRespData) {
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(versionRespData);
        prefsEditor.putString(PREF_KEY_VERSIONRESP, json);
        prefsEditor.commit();
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
    }

}
