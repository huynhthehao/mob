package vn.homecredit.hcvn.data.repository;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import vn.homecredit.hcvn.ui.notification.model.NotificationModel;
import vn.homecredit.hcvn.ui.notification.model.NotificationResp;

public interface NotificationRepository {
    Single<NotificationResp> getNotifications();

    void cacheNotifications(List<NotificationModel> notificationModels);

    Flowable<List<NotificationModel>> getCachedNotifications();
}
