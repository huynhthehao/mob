package vn.homecredit.hcvn.service;

import android.text.TextUtils;
import android.util.Log;

import com.onesignal.NotificationExtenderService;
import com.onesignal.OSNotificationReceivedResult;

import javax.inject.Inject;

import vn.homecredit.hcvn.HCVNApp;
import vn.homecredit.hcvn.helpers.prefs.PreferencesHelper;

public class AppNotificationExtenderService extends NotificationExtenderService {
    @Inject
    PreferencesHelper preferencesHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        ((HCVNApp) getApplicationContext()).getAppComponent().inject(this);
    }

    @Override
    protected boolean onNotificationProcessing(OSNotificationReceivedResult notification) {
        if (preferencesHelper != null) {
            if (!preferencesHelper.getNotificationSetting() || TextUtils.isEmpty(preferencesHelper.getAccessToken())) {
                return true;
            }
        }
        return false;
    }
}
