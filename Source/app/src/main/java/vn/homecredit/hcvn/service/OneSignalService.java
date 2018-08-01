/*
 * OneSignalService.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/15/18 1:32 PM
 */

package vn.homecredit.hcvn.service;

public interface OneSignalService {
    void sendTags(String key, String value);
    void setSubscription(boolean subscribe);
    void deleteTag(String key);
    String jsonString(Object object);

    void tryGetPlayerId();
}
