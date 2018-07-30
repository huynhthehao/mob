package vn.homecredit.hcvn.data.repository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import vn.homecredit.hcvn.data.remote.ApiHeader;
import vn.homecredit.hcvn.data.remote.RestService;
import vn.homecredit.hcvn.helpers.prefs.PreferencesHelper;
import vn.homecredit.hcvn.service.OneSignalService;
import vn.homecredit.hcvn.ui.database.dao.NotificationDao;
import vn.homecredit.hcvn.ui.notification.model.NotificationModel;
import vn.homecredit.hcvn.ui.notification.model.NotificationResp;

public class NotificationRepositoryImpl implements NotificationRepository {
    private final RestService restService;
    private NotificationDao notificationDao;
    private final OneSignalService oneSignalService;

    @Inject
    public NotificationRepositoryImpl(RestService restService, NotificationDao notificationDao, OneSignalService oneSignalService) {
        this.restService = restService;
        this.notificationDao = notificationDao;
        this.oneSignalService = oneSignalService;
    }

    @Override
    public Single<NotificationResp> getNotifications() {
        return restService.getNotifications()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void cacheNotifications(List<NotificationModel> notificationModels) {
        notificationDao.insert(notificationModels);
    }

    @Override
    public List<NotificationModel> getCachedNotifications() {
        return notificationDao.loadNotifications();
    }
}
