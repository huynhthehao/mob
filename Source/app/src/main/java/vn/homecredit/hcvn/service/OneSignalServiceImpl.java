/*
 * OneSignalServiceImp.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/15/18 1:32 PM
 */

package vn.homecredit.hcvn.service;

import android.content.Context;

import com.onesignal.OSPermissionSubscriptionState;
import com.onesignal.OneSignal;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;
import vn.homecredit.hcvn.data.model.DeviceInfoModel;
import vn.homecredit.hcvn.helpers.prefs.PreferencesHelper;

@Singleton
public class OneSignalServiceImpl implements OneSignalService {
    private final DeviceInfo mDeviceInfo;

    @Inject
    public OneSignalServiceImpl(DeviceInfo deviceInfo) {
        mDeviceInfo = deviceInfo;
    }

    @Override
    public void sendTags(String key, String value) {
        OneSignal.sendTag(key, value);
    }

    @Override
    public void setSubscription(boolean subscribe) {
        OneSignal.setSubscription(subscribe);
    }

    @Override
    public void deleteTag(String key) {
        OneSignal.deleteTag(key);
    }

    @Override
    public String jsonString(Object object) {
        return object.toString();
    }

    @Override
    public void tryGetPlayerId() {
        OSPermissionSubscriptionState status = OneSignal.getPermissionSubscriptionState();
        String userID = status.getSubscriptionStatus().getUserId();
        String token = status.getSubscriptionStatus().getPushToken();
        mDeviceInfo.setPlayerId(userID);
        mDeviceInfo.setPushToken(token);
    }
}
