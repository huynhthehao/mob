/*
 * PreferencesHelper.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/12/18 4:49 PM
 */

package vn.homecredit.hcvn.data.local.prefs;

import vn.homecredit.hcvn.data.model.api.ProfileResp;
import vn.homecredit.hcvn.data.model.api.VersionResp;

public interface PreferencesHelper {

    String getAccessToken();
    void setAccessToken(String accessToken);

    ProfileResp.ProfileRespData loadProfile();
    void saveProfile(ProfileResp.ProfileRespData profileRespData);

    VersionResp.VersionRespData getVersionRespData();
    void setVersionRespData(VersionResp.VersionRespData versionRespData);

    boolean getIsShowDashboard();
    void setIsShowDashboard(boolean value);

    void logout();
    String langId();

    boolean getNotificationSetting();
    void setNotificationSetting(boolean isEnable);

    boolean getFingerPrintSetting();
    void setFingerPrintSetting(boolean isEnable);

}
