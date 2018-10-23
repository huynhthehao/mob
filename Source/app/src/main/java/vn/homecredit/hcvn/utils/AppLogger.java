/*
 * AppLogger.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/12/18 11:46 AM
 */

package vn.homecredit.hcvn.utils;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import timber.log.Timber;
import vn.homecredit.hcvn.BuildConfig;

public final class AppLogger implements RemoteLoggerKey {
    private static final String FORMAT_KEY = "%s ~ %s";
    private static Gson gson;

    private AppLogger() {
        // This utility class is not publicly instantiable
    }

    public static void d(String s, Object... objects) {
        Timber.d(s, objects);
    }

    public static void d(Throwable throwable, String s, Object... objects) {
        Timber.d(throwable, s, objects);
    }

    public static void e(String s, Object... objects) {
        Timber.e(s, objects);
    }

    public static void e(Throwable throwable, String s, Object... objects) {
        Timber.e(throwable, s, objects);
    }

    public static void i(String s, Object... objects) {
        Timber.i(s, objects);
    }

    public static void i(Throwable throwable, String s, Object... objects) {
        Timber.i(throwable, s, objects);
    }

    public static void init() {
        if (gson == null)
            gson = new Gson();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    private static String asString(Object target) {
        try {
            return gson.toJson(target);
        } catch (JsonIOException e) {
            return target + ":" + e.getMessage();
        }
    }

    public static Throwable createLog(String key, Object target) {
        return new Throwable(String.format(FORMAT_KEY, key, asString(target)));
    }

    public static void w(String s, Object... objects) {
        Timber.w(s, objects);
    }

    public static void w(Throwable throwable, String s, Object... objects) {
        Timber.w(throwable, s, objects);
    }
}
