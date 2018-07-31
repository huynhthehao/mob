package vn.homecredit.hcvn.ui.notification;

import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import vn.homecredit.hcvn.data.repository.NotificationRepository;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.ui.notification.model.NotificationModel;
import vn.homecredit.hcvn.ui.notification.model.NotificationResp;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class NotificationViewModel extends BaseViewModel {
    private final NotificationRepository repository;
    private MutableLiveData<List<NotificationModel>> dataNotifications = new MutableLiveData<>();
    private MutableLiveData<Boolean> modelIsRefreshing = new MutableLiveData<>();

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
        Completable.fromAction(() -> repository.cacheNotifications(notificationResp.getData()));
    }

    private void loadAndDisplayCachedNotifications() {
        repository.getCachedNotifications()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(notificationModels -> {
                    if (notificationModels != null) {
                        dataNotifications.setValue(notificationModels);
                    }
                });
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
}
