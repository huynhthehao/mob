/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/4/18 1:35 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.service;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

@Singleton
public class DeviceInfoImpl implements DeviceInfo {

    private final Context mContext;
    private String mId;
    private String mModel;
    private int mPlatform;
    private String mVersion;
    private String mBrand;
    private String mPlayerId;
    private String mPushToken;

    @Inject
    public DeviceInfoImpl(Context context) {
        mContext = context;
        mModel = Build.MODEL;
        mVersion = Build.VERSION.RELEASE;
        mPlatform = 0;
        mBrand = Build.BRAND;
    }

    @Override
    public String getId() {
        if (!TextUtils.isEmpty(mId))
            return mId;

        try
        {
            mId = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        catch(Exception ex)
        {
            Timber.w("DeviceInfo: Unable to get id: %s", ex.toString());
        }
        return "";
    }


    @Override
    public String getModel() {
        return mModel;
    }

    @Override
    public int getPlatform() {
        return mPlatform;
    }

    @Override
    public String getVersion() {
        return mVersion;
    }

    @Override
    public String getBrand() {
        return mBrand;
    }

    @Override
    public String getBrandModel() {
        return String.format("%s %s", mBrand, mModel);
    }

    @Override
    public String getPlayerId() {
        return mPlayerId;
    }

    @Override
    public void setPlayerId(String playerId) {
        mPlayerId = playerId;
    }

    @Override
    public String getPushToken() {
        return mPushToken;
    }

    @Override
    public void setPushToken(String pushToken) {
        mPushToken = pushToken;
    }
}
