/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/5/18 5:11 PM, by Admin
 */

package vn.homecredit.hcvn.service;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ResourceServiceImpl implements ResourceService {

    private Context context;

    @Inject
    public ResourceServiceImpl(Context context) {
        this.context = context;
    }


    @Override
    public String getStringById(int resourceId) {
        if(this.context == null)
            return "";

        return this.context.getResources().getString(resourceId);
    }

    @Override
    public int getColorById(int colorId) {
        if(this.context == null)
            return 0;

        return this.context.getResources().getColor(colorId);
    }
}
