package vn.homecredit.hcvn.data.repository;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import vn.homecredit.hcvn.data.remote.ApiHeader;
import vn.homecredit.hcvn.data.remote.RestService;
import vn.homecredit.hcvn.helpers.prefs.PreferencesHelper;
import vn.homecredit.hcvn.service.OneSignalService;
import vn.homecredit.hcvn.ui.notification.model.NotificationResp;

public class NotificationRepositoryImpl implements NotificationRepository {

    private final RestService restService;
    private final PreferencesHelper preferencesHelper;
    private final ApiHeader apiHeader;
    private final OneSignalService oneSignalService;

    @Inject
    public NotificationRepositoryImpl(RestService restService, PreferencesHelper preferencesHelper, ApiHeader apiHeader, OneSignalService oneSignalService) {
        this.restService = restService;
        this.preferencesHelper = preferencesHelper;
        this.apiHeader = apiHeader;
        this.oneSignalService = oneSignalService;
    }

    @Override
    public Single<NotificationResp> getNotifications() {
        return restService.getNotifications()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
