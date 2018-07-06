/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/6/18 11:41 AM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.service;

import io.reactivex.Single;
import vn.homecredit.hcvn.data.model.api.ProfileResp;

public interface ProfileService {
    Single<ProfileResp>  SyncProfile();
    ProfileResp.ProfileRespData GetProfile();
}
