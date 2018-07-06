/*
 * MemoryHelperImpl.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/14/18 1:39 PM
 */

package vn.homecredit.hcvn.data.local.memory;

import javax.inject.Inject;
import javax.inject.Singleton;

import vn.homecredit.hcvn.data.model.api.ProfileResp;
import vn.homecredit.hcvn.data.model.api.VersionResp;

@Singleton
public class MemoryHelperImpl implements MemoryHelper {

    private VersionResp.VersionRespData mVersionRespData;
    private ProfileResp.ProfileRespData mProfileRespData;

    @Inject
    public MemoryHelperImpl() {
    }

    @Override
    public VersionResp.VersionRespData getVersionRespData() {
        return mVersionRespData;
    }

    @Override
    public void setVersionRespData(VersionResp.VersionRespData versionRespData) {
        mVersionRespData = versionRespData;
    }

    @Override
    public ProfileResp.ProfileRespData getProfileRespData() {
        return mProfileRespData;
    }

    @Override
    public void setProfileRespData(ProfileResp.ProfileRespData profileRespData) {
        mProfileRespData = profileRespData;
    }
}
