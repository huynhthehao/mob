/*
 * OneSignalService.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/15/18 1:32 PM
 */

package vn.homecredit.hcvn.service;

import android.content.Context;

import com.onesignal.OSNotification;
import com.onesignal.OSNotificationOpenResult;

public interface OneSignalService {
    void sendTags(String key, String value);
    void setSubscription(boolean subscribe);
    void deleteTag(String key);
    String jsonString(Object object);

    void trygetPrivateId();

    void notificationReceived(Context context, OSNotification notification);

    void notificationOpenHandler(Context context, OSNotificationOpenResult result);
}
