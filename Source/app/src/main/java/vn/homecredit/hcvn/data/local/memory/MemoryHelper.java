/*
 * MemoryHelper.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/14/18 1:38 PM
 */

package vn.homecredit.hcvn.data.local.memory;

import vn.homecredit.hcvn.data.model.api.ProfileResp;
import vn.homecredit.hcvn.data.model.api.VersionResp;

public interface MemoryHelper {
    VersionResp.VersionRespData getVersionRespData();

    void setVersionRespData(VersionResp.VersionRespData versionRespData);

    ProfileResp.ProfileRespData getProfileRespData();

    void setProfileRespData(ProfileResp.ProfileRespData profileRespData);
}
