package vn.homecredit.hcvn.data.repository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import vn.homecredit.hcvn.data.model.api.base.BaseApiResponse;
import vn.homecredit.hcvn.data.remote.RestService;
import vn.homecredit.hcvn.service.OneSignalService;
import vn.homecredit.hcvn.database.dao.NotificationDao;
import vn.homecredit.hcvn.ui.notification.model.NotificationModel;
import vn.homecredit.hcvn.ui.notification.model.NotificationResp;

public class NotificationRepositoryImpl implements NotificationRepository {
    private final RestService restService;
    private NotificationDao notificationDao;

    @Inject
    public NotificationRepositoryImpl(RestService restService, NotificationDao notificationDao) {
        this.restService = restService;
        this.notificationDao = notificationDao;
    }

    @Override
    public Single<NotificationResp> getNotifications() {
        return restService.getNotifications()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<BaseApiResponse> markNotificationAsRead(String notificationID) {
        return restService.markNotificationAsRead(notificationID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void cacheNotifications(List<NotificationModel> notificationModels) {
        notificationDao.insert(notificationModels);
    }

    @Override
    public Flowable<List<NotificationModel>> getCachedNotifications() {
        return notificationDao.loadNotifications();
    }

    @Override
    public void makeNotificationAsRead(String notificationId) {
        notificationDao.markAsRead(notificationId);
    }
}
