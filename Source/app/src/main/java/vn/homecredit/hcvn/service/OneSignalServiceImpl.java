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
import javax.inject.Singleton;

@Singleton
public class OneSignalServiceImpl implements OneSignalService {
    public OneSignalServiceImpl() {

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
}
