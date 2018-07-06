/*
 * RestService.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/12/18 1:11 PM
 */

package vn.homecredit.hcvn.data.remote;

import javax.inject.Singleton;

import io.reactivex.Single;
import vn.homecredit.hcvn.data.model.api.ProfileResp;
import vn.homecredit.hcvn.data.model.api.TokenResp;
import vn.homecredit.hcvn.data.model.api.VersionResp;

public interface RestService {
    Single<VersionResp> checkUpdate();
    Single<TokenResp> getToken(String phoneNumber, String password);
    Single<ProfileResp> getProfile();
    ApiHeader getApiHeader();
}
