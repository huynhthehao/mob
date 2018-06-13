/*
 * RestServiceImpl.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/12/18 1:12 PM
 */

package vn.homecredit.hcvn.data.remote;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.HashMap;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import vn.homecredit.hcvn.data.model.api.VersionResp;
import vn.homecredit.hcvn.data.model.api.base.BaseApiResponse;

@Singleton
public class RestServiceImpl implements RestService {

    private ApiHeader mApiHeader;

    @Inject
    public RestServiceImpl(ApiHeader apiHeader) {
        mApiHeader = apiHeader;
    }

    public Single<VersionResp> CheckUpdateAsync()
    {
        HashMap<String, String> requestHeader = new HashMap<String, String>();
        requestHeader.put("X-DEVICE-ID", "abc");
        requestHeader.put("X-PLAYER-ID", "abc");
        requestHeader.put("X-DEVICE-VERSION", "abc");
        requestHeader.put("X-DEVICE-PLATFORM", "abc");
        requestHeader.put("X-DEVICE-MODEL", "abc");
        requestHeader.put("X-PUSH-TOKEN", "abc");
        requestHeader.put("X-APP-VERSION", "a");

        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_APP + "/version?platform=2")
                .addHeaders(requestHeader)
                .build().getObjectSingle(VersionResp.class);
    }
}
