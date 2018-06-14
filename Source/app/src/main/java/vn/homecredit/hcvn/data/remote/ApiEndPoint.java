/*
 * EndPointService.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/12/18 12:59 PM
 */

package vn.homecredit.hcvn.data.remote;

import vn.homecredit.hcvn.BuildConfig;

public final class ApiEndPoint {
    public static final String ENDPOINT_APP = BuildConfig.BASE_URL + "/api";
    public static final String ENDPOINT_CLW = BuildConfig.BASE_URL + "/api/clw";
    public static final String ENDPOINT_TOKEN = BuildConfig.BASE_URL + "/token";
}
