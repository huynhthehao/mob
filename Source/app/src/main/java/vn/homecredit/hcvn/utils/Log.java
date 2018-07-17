/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 10/07/18 2:14 PM, by An.NguyenN1
 */

package vn.homecredit.hcvn.utils;

import vn.homecredit.hcvn.BuildConfig;

public class Log {
    public static final boolean DEBUG = BuildConfig.DEBUG;
    public static final String TAG = "HCVN";
    public static void printStackTrace(Throwable e) {
        if (!DEBUG) return;
        e.printStackTrace();
    }

    public static void debug(String tag, String msg) {
        if (!DEBUG) return;
        android.util.Log.d(tag, msg);
    }

    public static void debug(String msg) {
        Log.debug(TAG, msg);
    }
}
