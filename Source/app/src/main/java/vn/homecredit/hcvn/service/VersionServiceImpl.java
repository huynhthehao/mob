/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/4/18 1:58 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.service;

import android.content.Context;
import android.content.pm.PackageManager;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class VersionServiceImpl implements VersionService {
    private Context mContext;

    @Inject
    public VersionServiceImpl(Context context) {
        mContext = context;
    }

    @Override
    public String getVersion() {
        try {
            return mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), PackageManager.GET_META_DATA).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
    }
}
