/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/6/18 4:57 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.helpers.memory;

import vn.homecredit.hcvn.data.model.api.ProfileResp;
import vn.homecredit.hcvn.data.model.api.VersionResp;

public interface MemoryHelper {
    VersionResp.VersionRespData getVersionRespData();

    void setVersionRespData(VersionResp.VersionRespData versionRespData);

    ProfileResp.ProfileRespData getProfileRespData();

    void setProfileRespData(ProfileResp.ProfileRespData profileRespData);
}
