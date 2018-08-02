package vn.homecredit.hcvn.ui.notification;

import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import vn.homecredit.hcvn.data.repository.NotificationRepository;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.ui.notification.model.NotificationModel;
import vn.homecredit.hcvn.ui.notification.model.NotificationResp;
import vn.homecredit.hcvn.utils.Log;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class NotificationViewModel extends BaseViewModel {
    private final NotificationRepository repository;
    private MutableLiveData<List<NotificationModel>> dataNotifications = new MutableLiveData<>();
    private MutableLiveData<Boolean> modelIsRefreshing = new MutableLiveData<>();
    private MutableLiveData<Integer> modelNotificationUnreadCount = new MutableLiveData<>();

    @Inject
    public NotificationViewModel(SchedulerProvider schedulerProvider, NotificationRepository notificationRepository) {
        super(schedulerProvider);
        this.repository = notificationRepository;
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
                            cacheNotification(notificationResp);
                            loadAndDisplayCachedNotifications();
                        },
                        throwable -> {
                            modelIsRefreshing.setValue(false);
                            handleError(throwable);
                        });
        getCompositeDisposable().add(disposableNotifications);
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
        modelNotificationUnreadCount.setValue(count);
    }

    public void markNotificationAsRead(NotificationModel model) {
        // Will remove later when apply notification details page.
        if (model.isRead()) {
            return;
        }
        setIsLoading(true);
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
}
