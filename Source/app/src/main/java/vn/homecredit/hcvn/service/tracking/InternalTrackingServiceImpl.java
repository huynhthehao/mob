package vn.homecredit.hcvn.service.tracking;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import vn.homecredit.hcvn.data.model.api.ProfileResp;
import vn.homecredit.hcvn.data.model.tracking.InternalTrackingModel;
import vn.homecredit.hcvn.data.remote.RestService;
import vn.homecredit.hcvn.helpers.prefs.PreferencesHelper;
import vn.homecredit.hcvn.service.DeviceInfo;
import vn.homecredit.hcvn.ui.notification.model.NotificationAnalyticData;
import vn.homecredit.hcvn.utils.DateUtils;
import vn.homecredit.hcvn.utils.Log;
import vn.homecredit.hcvn.utils.StringUtils;

public class InternalTrackingServiceImpl implements AnalyticsService {

    public static final String CATEGORY_PAGE = "Page";
    public static final String CATEGORY_NOTIFICATION = "Notification";
    public static final String ACTION_PAGE_APPEAR = "Page Appear";
    public static final String ACTION_NOTIF_RECEIVED = "Notification Received";
    public static final String ACTION_NOTIF_OPENED = "Notification Opened";

    private RestService restService;
    private PreferencesHelper preferencesHelper;
    private DeviceInfo deviceInfo;

    @Inject
    public InternalTrackingServiceImpl(RestService restService, PreferencesHelper preferencesHelper, DeviceInfo deviceInfo) {
        this.restService = restService;
        this.preferencesHelper = preferencesHelper;
        this.deviceInfo = deviceInfo;
    }

    @Override
    public void sendEvent(@NotNull String category, @NotNull String action, @NotNull String label) {
        InternalTrackingModel internalTrackingModel = createModel();
        internalTrackingModel.setCategory(category);
        internalTrackingModel.setAction(action);
        internalTrackingModel.setLabel(label);
        processTracking(internalTrackingModel);
    }

    @Override
    public void sendView(@NotNull String screen) {
        resumeScreen(screen);
    }

    public void notificationReceived(NotificationAnalyticData notificationAnalyticData) {
        trackNotification(notificationAnalyticData, ACTION_NOTIF_RECEIVED);
    }

    public void notificationOpened(NotificationAnalyticData notificationAnalyticData) {
        trackNotification(notificationAnalyticData, ACTION_NOTIF_OPENED);
    }

    private void trackNotification(NotificationAnalyticData notificationAnalyticData, String action) {
        if (notificationAnalyticData == null || notificationAnalyticData.getUtmContent() == null) {
            return;
        }
        InternalTrackingModel internalTrackingModel = createModel();
        internalTrackingModel.setCategory(CATEGORY_NOTIFICATION);
        internalTrackingModel.setAction(action);
        internalTrackingModel.setLabel(notificationAnalyticData.getNotificationContent());
        internalTrackingModel.setUtmSource(notificationAnalyticData.getUtmSource());
        internalTrackingModel.setUtmCampaign(notificationAnalyticData.getUtmCampaign());
        internalTrackingModel.setUtmContent(notificationAnalyticData.getUtmContent());
        internalTrackingModel.setUtmlMedium(notificationAnalyticData.getUtmMedium());
        processTracking(internalTrackingModel);

    }

    private void processTracking(InternalTrackingModel internalTrackingModel) {
        if (!StringUtils.isNullOrEmpty(internalTrackingModel.getUserId())) {
            restService.trackingAuthenticated(internalTrackingModel)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(s -> Log.debug("trackingAuthenticated " + s), throwable -> throwable.printStackTrace());
        } else {
            restService.trackingAnonymous(internalTrackingModel)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(s -> Log.debug("trackingAnonymous " + s), throwable -> throwable.printStackTrace());
        }
    }

    private void resumeScreen(String screen) {
        InternalTrackingModel internalTrackingModel = createModel();
        internalTrackingModel.setCategory(CATEGORY_PAGE);
        internalTrackingModel.setAction(ACTION_PAGE_APPEAR);
        internalTrackingModel.setLabel(screen);
        internalTrackingModel.setPage(screen);
        processTracking(internalTrackingModel);
    }

    private InternalTrackingModel createModel() {
        ProfileResp.ProfileRespData profileRespData = preferencesHelper.getProfile();
        InternalTrackingModel internalTrackingModel = new InternalTrackingModel();
        if (profileRespData == null) {
            internalTrackingModel.setCuid("");
            internalTrackingModel.setUserId("");
            internalTrackingModel.setUserName("");
        } else {
            internalTrackingModel.setCuid(profileRespData.getcUID() == null ? "" : profileRespData.getcUID());
            internalTrackingModel.setUserId(profileRespData.getUserId() == null ? "" : profileRespData.getUserId());
            internalTrackingModel.setUserName(profileRespData.getUserName() == null ? "" : profileRespData.getUserName());
        }
        if (deviceInfo == null) {
            return internalTrackingModel;
        }
        internalTrackingModel.setDeviceName(deviceInfo.getModel());
        internalTrackingModel.setActionTime(DateUtils.getCurrentUTCTime());
        internalTrackingModel.setDeviceModel(deviceInfo.getModel());
        internalTrackingModel.setDeviceBrand(deviceInfo.getBrand());
        internalTrackingModel.setDeviceId(deviceInfo.getId());
        internalTrackingModel.setPlatform(String.valueOf(deviceInfo.getPlatform()));
        internalTrackingModel.setAppVersion(deviceInfo.getVersion());
        internalTrackingModel.setLanguage(preferencesHelper.getLanguageCode());
        return internalTrackingModel;
    }


}
