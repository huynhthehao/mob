/*
 * AppUtils.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/12/18 11:39 AM
 */

package vn.homecredit.hcvn.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.net.Uri;
import android.os.Build;

import javax.inject.Inject;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.ui.splash.SplashActivity;
import vn.homecredit.hcvn.ui.welcome.WelcomeActivity;

public final class AppUtils {
    public static final String MOMO_PACKAGE = "com.mservice.momotransfer";
    public static final String HCVN_PACKAGE = "vn.homecredit.hcvn";

    private AppUtils() {
        // This class is not publicly instantiable
    }

    public static void openPlayStoreForApp(Context context) {
        openPlayStoreForApp(context, HCVN_PACKAGE);
    }

    public static void restartApp(Context context) {
        Intent intent = new Intent(context, WelcomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static void openAppMomo(Context context) {
        openApp(context, MOMO_PACKAGE);
    }

    public static void openApp(Context context, String appPackageName) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(appPackageName);
        if (intent != null && intent.resolveActivity(packageManager) != null) {
            context.startActivity(intent);
        } else {
            openPlayStoreForApp(context, appPackageName);
        }

    }

    public static void openPlayStoreForApp(Context context, String appPackageName) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(context
                            .getResources()
                            .getString(R.string.app_market_link) + appPackageName)));
        } catch (android.content.ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(context
                            .getResources()
                            .getString(R.string.app_google_play_store_link) + appPackageName)));
        }
    }

    public static void openExternalBrowser(Context context, String uri) {
        if (!uri.startsWith("http://") && !uri.startsWith("https://")) {
            uri = "http://" + uri;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(uri));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        safeStartActivity(context, intent);
    }

    private static void safeStartActivity(Context context, Intent intent) {
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.printStackTrace(e);
        }
    }

    public static void openDeviceCallDialog(Context context, String phoneNumber) {
        try {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + phoneNumber));
            context.startActivity(callIntent);
        } catch (ActivityNotFoundException activityException) {
        }
    }

}