/*
 * OneSignalServiceImp.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/15/18 1:32 PM
 */

package vn.homecredit.hcvn.service;

import com.onesignal.OneSignal;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

@Singleton
public class OneSignalServiceImpl implements OneSignalService {
    private final DeviceInfo mDeviceInfo;

    @Inject
    public OneSignalServiceImpl(DeviceInfo deviceInfo) {
        mDeviceInfo = deviceInfo;
    }

    @Override
    public void SendTags(String key, String value) {
        OneSignal.sendTag(key, value);
    }

    @Override
    public void SetSubscription(boolean subscribe) {
        OneSignal.setSubscription(subscribe);
    }

    @Override
    public void DeleteTag(String key) {
        OneSignal.deleteTag(key);
    }

    @Override
    public String JsonString(Object object) {
        return object.toString();
    }

    @Override
    public void tryGetPlayerId() {
        OneSignal.idsAvailable((id, token) -> {
            mDeviceInfo.setPlayerId(id);
            mDeviceInfo.setPushToken(token);

            Timber.v(String.format("PlayerId: %s", id));
            Timber.v(String.format("Push token: %s", token));

        });
    }
}
