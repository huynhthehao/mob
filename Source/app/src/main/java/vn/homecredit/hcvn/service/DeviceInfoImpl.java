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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;
import vn.homecredit.hcvn.utils.DateUtils;
import vn.homecredit.hcvn.utils.Log;

@Singleton
public class DeviceInfoImpl implements DeviceInfo {

    public static final String PREFIX_KEY = "c96aa9d8d9dd1059098f4400ee6c978918286f3d537b648a2f99466f6e22b0f3";

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

        mPlayerId = "";
        mPushToken = "";
    }

    @Override
    public String getId() {
        if (!TextUtils.isEmpty(mId))
            return mId;

        try {
            mId = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception ex) {
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

    @Override
    public String trackingKey() {
        String deviceId = getId();
        String date = DateUtils.nowSimple();
        String key = String.format("%s|%s|%s", PREFIX_KEY, date, deviceId);
        return md5(key);
    }

    public String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
