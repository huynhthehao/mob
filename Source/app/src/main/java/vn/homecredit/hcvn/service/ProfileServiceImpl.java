/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/6/18 11:41 AM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.service;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import vn.homecredit.hcvn.data.DataManager;
import vn.homecredit.hcvn.data.local.prefs.PreferencesHelper;
import vn.homecredit.hcvn.data.model.api.ProfileResp;

@Singleton
public class ProfileServiceImpl implements ProfileService {

    private final DataManager mDataManager;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public ProfileServiceImpl(DataManager dataManager, PreferencesHelper preferencesHelper) {
        mDataManager = dataManager;
        mPreferencesHelper = preferencesHelper;
    }

    @Override
    public Single<ProfileResp> syncProfile() {
        return mDataManager.getProfile()
                .doOnSuccess(response -> mPreferencesHelper.saveProfile(response.getData()));
    }

    @Override
    public ProfileResp.ProfileRespData getProfile() {
        return mPreferencesHelper.loadProfile();
    }
}
