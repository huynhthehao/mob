/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/4/18 4:40 PM, by Admin
 */

package vn.homecredit.hcvn.service;

import android.content.Context;

public interface ResourceService {
    String getStringById(int id);
    Context getContext();
    int getColorById(int colorId);
}
