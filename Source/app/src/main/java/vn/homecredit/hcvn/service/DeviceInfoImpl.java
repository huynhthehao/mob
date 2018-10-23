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
import vn.homecredit.hcvn.BuildConfig;
import vn.homecredit.hcvn.helpers.CryptoHelper;
import vn.homecredit.hcvn.utils.DateUtils;
import vn.homecredit.hcvn.utils.StringUtils;

@Singleton
public class DeviceInfoImpl implements DeviceInfo {

    public static final String PREFIX_KEY = "c96aa9d8d9dd1059098f4400ee6c978918286f3d537b648a2f99466f6e22b0f3";

    private final Context mContext;
    private String mId;
    private String privateId;
    private String mModel;
    private int mPlatform;
    private String mVersion;
    private String mBrand;
    private String mPushToken;

    @Inject
    public DeviceInfoImpl(Context context) {
        mContext = context;
        mModel = Build.MODEL;
        mVersion = BuildConfig.VERSION_NAME;
        mPlatform = 0;
        mBrand = Build.BRAND;
        mPushToken = "";
    }

    @Override
    public String getId() {
        if (!TextUtils.isEmpty(mId))
            return mId;

        try {
            mId = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
            return mId;
        } catch (Exception ex) {
            Timber.w("DeviceInfo: Unable to get id: %s", ex.toString());
            return "";
        }
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
    public String getPushToken() {
        return mPushToken;
    }

    @Override
    public void setPushToken(String pushToken) {
        mPushToken = pushToken;
    }

    @Override
    public String trackingKey() {
        String deviceId = getId();
        String date = DateUtils.nowSimple();
        String key = String.format("%s|%s|%s", PREFIX_KEY, date, deviceId);
        return CryptoHelper.getMd5Hash(key);
    }

    @Override
    public String getPrivateId(){
        if(!StringUtils.isNullOrWhiteSpace(privateId))
            return privateId;

        String deviceId = getId();
        deviceId = String.format("%s-%s-%s-%s",
                Build.MODEL,
                Build.FINGERPRINT,
                Build.PRODUCT,
                deviceId);
        String hashId = CryptoHelper.getSha1Hash(deviceId);
        privateId = CryptoHelper.splitStringWithChar(hashId, 5);
        return privateId;
    }
}
