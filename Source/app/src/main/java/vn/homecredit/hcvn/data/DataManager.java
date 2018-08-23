/*
 * DataManager.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/12/18 4:46 PM
 */

package vn.homecredit.hcvn.data;

import io.reactivex.Single;
import vn.homecredit.hcvn.data.model.api.ProfileResp;
import vn.homecredit.hcvn.data.model.api.TokenResp;
import vn.homecredit.hcvn.data.model.api.VersionResp;
import vn.homecredit.hcvn.data.remote.ApiHeader;
import vn.homecredit.hcvn.utils.FingerPrintAuthValue;

public interface DataManager  {
    Single<VersionResp> checkUpdate();
    ApiHeader getApiHeader();

    VersionResp.VersionRespData getVersionRespData();

    void setVersionRespData(VersionResp.VersionRespData versionRespData);

    ProfileResp.ProfileRespData getProfileRespData();

    void setProfileRespData(ProfileResp.ProfileRespData profileRespData);

    String getAccessToken();
    void setAccessToken(String accessToken);

    boolean getIsShowDashboard();
    void setIsShowDashboard(boolean value);

    void logout();
    FingerPrintAuthValue getFingerPrintAuthValue();
    boolean getNotificationSetting();
    void setNotificationSetting(boolean isEnable);

    boolean getFingerPrintSetting();
    void setFingerPrintSetting(boolean isEnable);

    void setLanguageCode(String languageId);
    String getLanguageCode();
}
