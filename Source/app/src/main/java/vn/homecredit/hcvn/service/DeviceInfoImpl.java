/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/4/18 1:35 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.service;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DeviceInfoImpl implements DeviceInfo {

    private String mId;
    private String mModel;
    private String mVersion;
    private String mBrand;
    private String mPlayerId;
    private String mPushToken;

    @Inject
    public DeviceInfoImpl() {
    }

    @Override
    public String getId() {
        return mId;
    }

    @Override
    public void setId(String id) {
        mId = id;
    }

    @Override
    public String getModel() {
        return mModel;
    }

    @Override
    public void setModel(String model) {
        mModel = model;
    }

    @Override
    public String getVersion() {
        return mVersion;
    }

    @Override
    public void setVersion(String version) {
        mVersion = version;
    }

    @Override
    public String getBrand() {
        return mBrand;
    }

    @Override
    public void setBrand(String brand) {
        mBrand = brand;
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
