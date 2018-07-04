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
    void SendTags(String key, String value);
    void SetSubscription(boolean subscribe);
    void DeleteTag(String key);
    String JsonString(Object object);

    void tryGetPlayerId();
}
