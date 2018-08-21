/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 8/17/18 9:21 AM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public final class NetworkUtils {
    private NetworkUtils() { }

    public static boolean isNetworkConnected(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            }
            return false;
        } catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
}
