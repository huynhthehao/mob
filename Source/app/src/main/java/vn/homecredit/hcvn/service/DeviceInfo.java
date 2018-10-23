/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/4/18 1:35 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.service;

public interface DeviceInfo {
    String getId();

    String getModel();

    int getPlatform();

    String getVersion();

    String getBrand();

    String getBrandModel();

    String getPlayerId();

    String getPushToken();

    void setPushToken(String pushToken);

    String trackingKey();

    String getPrivateId();
}
