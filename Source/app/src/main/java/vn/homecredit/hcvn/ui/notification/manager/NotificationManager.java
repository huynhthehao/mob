package vn.homecredit.hcvn.ui.notification.manager;

import android.content.Context;

import vn.homecredit.hcvn.ui.notification.model.NotificationModel;

public interface NotificationManager {
    void openNotification(Context context, NotificationModel model);
}
