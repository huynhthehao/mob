/*
 * AppDataManager.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/12/18 4:46 PM
 */

package vn.homecredit.hcvn.data;

import android.content.Context;

import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import vn.homecredit.hcvn.data.local.prefs.PreferencesHelper;
import vn.homecredit.hcvn.data.model.api.VersionResp;
import vn.homecredit.hcvn.data.model.api.base.BaseApiResponse;
import vn.homecredit.hcvn.data.remote.RestService;

@Singleton
public class AppDataManager implements DataManager {
    private final Context mContext;
    private final PreferencesHelper mPreferencesHelper;
    private final RestService mRestService;
    private final Gson mGson;

    @Inject
    public AppDataManager(Context context, PreferencesHelper preferencesHelper, RestService restService, Gson gson) {
        mContext = context;
        mPreferencesHelper = preferencesHelper;
        mRestService = restService;
        mGson = gson;
    }

    @Override
    public Single<VersionResp> CheckUpdateAsync() {
        return mRestService.CheckUpdateAsync();
    }
}
