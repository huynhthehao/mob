package vn.homecredit.hcvn.ui.notification;

import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import vn.homecredit.hcvn.data.repository.NotificationRepository;
import vn.homecredit.hcvn.helpers.prefs.PreferencesHelper;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.ui.notification.model.NotificationModel;
import vn.homecredit.hcvn.ui.notification.model.NotificationResp;
import vn.homecredit.hcvn.utils.CountryValue;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class NotificationViewModel extends BaseViewModel {
    private final NotificationRepository repository;
    private final PreferencesHelper preferencesHelper;
    private MutableLiveData<List<NotificationModel>> dataNotifications = new MutableLiveData<>();
    private MutableLiveData<Boolean> modelIsRefreshing = new MutableLiveData<>();
    private MutableLiveData<Integer> modelNotificationUnreadCount = new MutableLiveData<>();
    private MutableLiveData<String> modelOpenNotificationMarketingType = new MutableLiveData<>();

    @Inject
    public NotificationViewModel(SchedulerProvider schedulerProvider, NotificationRepository notificationRepository, PreferencesHelper preferencesHelper) {
        super(schedulerProvider);
        this.repository = notificationRepository;
        this.preferencesHelper = preferencesHelper;
    }

    @Override
    public void init() {
        modelIsRefreshing.setValue(true);
        loadAndDisplayCachedNotifications();
        refreshNotifications();
    }

    private void refreshNotifications() {
        Disposable disposableNotifications = repository.getNotifications()
                .subscribe(
                        notificationResp ->
                        {
                            modelIsRefreshing.setValue(false);
                            if (notificationResp == null) {
                                return;
                            }
                            if (notificationResp.getResponseCode() != 0) {
                                showMessage(notificationResp.getResponseMessage());
                                return;
                            }
                            cacheNotification(notificationResp);
                            loadAndDisplayCachedNotifications();
                            saveCurrentBadgeCount(notificationResp.getData());
                        },
                        throwable -> {
                            modelIsRefreshing.setValue(false);
                            handleError(throwable);
                        });
        getCompositeDisposable().add(disposableNotifications);
    }

    private void saveCurrentBadgeCount(List<NotificationModel> notificationModels) {
        if (notificationModels == null) return;
        int count = 0;
        for (int i = 0; i < notificationModels.size(); i++) {
            if (!notificationModels.get(i).isRead()) {
                count = count + 1;
            }
        }
    }

    private void cacheNotification(NotificationResp notificationResp) {
        repository.cacheNotifications(notificationResp.getData());
    }

    private void loadAndDisplayCachedNotifications() {
        repository.getCachedNotifications()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(notificationModels -> {
                    if (notificationModels != null) {
                        dataNotifications.setValue(notificationModels);
                        updateNotificationCountUnRead(notificationModels);
                    }
                });
    }

    private void updateNotificationCountUnRead(List<NotificationModel> notificationModels) {
        int count = 0;
        for (int i = 0; i < notificationModels.size(); i++) {
            if (!notificationModels.get(i).isRead()) {
                count = count + 1;
            }
        }
        preferencesHelper.setCurrentBadgeCount(count);
        modelNotificationUnreadCount.setValue(count);
    }

    public void onNotificationItemClicked(NotificationModel model) {
        if (!model.isRead()) {
            if (model.getType() != NotificationType.MARKETING.getType()) {
                setIsLoading(true);
            }
            markAsReadNotificationToServer(model);
        }
        if (model.getType() == NotificationType.MARKETING.getType()) {
            modelOpenNotificationMarketingType.setValue(getMarketingUrl(model));
        }
    }

    private void markAsReadNotificationToServer(NotificationModel model) {
        Disposable disposableMarkAsRead = repository.markNotificationAsRead(model.getId())
                .subscribe(
                        baseResp ->
                        {
                            setIsLoading(false);
                            if (baseResp.getResponseCode() == 0) {
                                updateNotificationAsRead(model.getId());
                                loadAndDisplayCachedNotifications();
                            }
                        },
                        throwable -> {
                            setIsLoading(false);
                            handleError(throwable);
                        });
        getCompositeDisposable().add(disposableMarkAsRead);
    }

    private String getMarketingUrl(NotificationModel model) {
        if (preferencesHelper.getLanguageCode().equalsIgnoreCase(CountryValue.VIETNAMESE.getLanguageCode())) {
            return model.getMarketingUrlVi();
        } else {
            return model.getMarketingUrlEn();
        }
    }

    private void updateNotificationAsRead(String notificationId) {
        repository.makeNotificationAsRead(notificationId);
    }

    public void pullToRefreshNotifications() {
        refreshNotifications();
    }

    public MutableLiveData<List<NotificationModel>> getDataNotifications() {
        return dataNotifications;
    }

    public MutableLiveData<Boolean> getModelIsRefreshing() {
        return modelIsRefreshing;
    }

    public MutableLiveData<Integer> getModelNotificationUnreadCount() {
        return modelNotificationUnreadCount;
    }

    public MutableLiveData<String> getModelOpenNotificationMarketingType() {
        return modelOpenNotificationMarketingType;
    }
}
