package vn.homecredit.hcvn.data.model;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import timber.log.Timber;

public class DeviceInfoModel {
    private String mId = "";
    private String mModel;
    private int mPlatform;
    private String mVersion;
    private String mBrand;
    private String mPlayerId = "";
    private String mPushToken = "";

    public DeviceInfoModel(Context context) {
        this.mModel = Build.MODEL;
        this.mPlatform = 2;
        this.mVersion = Build.VERSION.RELEASE;
        this.mBrand = Build.BRAND;

        // init device id
        if (!TextUtils.isEmpty(mId))
            return;
        try {
            mId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception ex) {
            Timber.w("DeviceInfo: Unable to get id: %s", ex.toString());
        }
    }

    public String getmId() {
        return mId;
    }

    public String getmModel() {
        return mModel;
    }

    public void setmModel(String mModel) {
        this.mModel = mModel;
    }

    public int getmPlatform() {
        return mPlatform;
    }

    public void setmPlatform(int mPlatform) {
        this.mPlatform = mPlatform;
    }

    public String getmVersion() {
        return mVersion;
    }

    public void setmVersion(String mVersion) {
        this.mVersion = mVersion;
    }

    public String getmBrand() {
        return mBrand;
    }

    public void setmBrand(String mBrand) {
        this.mBrand = mBrand;
    }

    public String getmPlayerId() {
        return mPlayerId;
    }

    public void setmPlayerId(String mPlayerId) {
        this.mPlayerId = mPlayerId;
    }

    public String getmPushToken() {
        return mPushToken;
    }

    public void setmPushToken(String mPushToken) {
        this.mPushToken = mPushToken;
    }
}
