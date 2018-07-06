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
import vn.homecredit.hcvn.di.PreferenceInfo;

public class AppPreferencesHelper implements PreferencesHelper {
    private static final String PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN";
    private static final String PREF_KEY_ACL_ACCESS_TOKEN = "PREF_KEY_ACL_ACCESS_TOKEN";
    private static final String PREF_KEY_PROFILE = "PREF_KEY_PROFILE";

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
    public String getAclAccessToken() {
        return mPrefs.getString(PREF_KEY_ACL_ACCESS_TOKEN, null);
    }

    @Override
    public void setAclAccessToken(String aclAccessToken) {
        mPrefs.edit().putString(PREF_KEY_ACL_ACCESS_TOKEN, aclAccessToken).apply();
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

}
