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

public interface PreferencesHelper {

    String getAccessToken();
    void setAccessToken(String accessToken);

    String getAclAccessToken();
    void setAclAccessToken(String aclAccessToken);

    ProfileResp.ProfileRespData loadProfile();
    void saveProfile(ProfileResp.ProfileRespData profileRespData);
}
