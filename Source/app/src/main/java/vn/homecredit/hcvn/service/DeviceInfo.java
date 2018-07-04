/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/4/18 1:35 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.service;

public interface DeviceInfo {
    String getId();

    void setId(String id);

    String getModel();

    void setModel(String model);

    String getVersion();

    void setVersion(String version);

    String getBrand();

    void setBrand(String brand);

    String getBrandModel();

    String getPlayerId();

    void setPlayerId(String playerId);

    String getPushToken();

    void setPushToken(String pushToken);
}
