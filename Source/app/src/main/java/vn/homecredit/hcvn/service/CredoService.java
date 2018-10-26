/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/4/18 4:40 PM, by Admin
 */

package vn.homecredit.hcvn.service;

import android.content.Context;

import java.io.ByteArrayOutputStream;

import vn.homecredit.hcvn.data.model.api.CredoConsent;

public interface CredoService {
    ByteArrayOutputStream collectData(Context context);
    String getCredoConsent(String userId, String username, String appVersion);
}
