package vn.homecredit.hcvn.data.repository;

import io.reactivex.Single;
import vn.homecredit.hcvn.ui.notification.model.NotificationResp;

public interface NotificationRepository {
    Single<NotificationResp> getNotifications();
}
