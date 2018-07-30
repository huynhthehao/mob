package vn.homecredit.hcvn.ui.notification;

import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import vn.homecredit.hcvn.data.repository.NotificationRepository;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.ui.notification.model.NotificationModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class NotificationViewModel extends BaseViewModel {
    private final NotificationRepository repository;
    private MutableLiveData<List<NotificationModel>> dataNotitifications = new MutableLiveData<>();
    private MutableLiveData<Boolean> modelIsRefreshing = new MutableLiveData<>();

    @Inject
    public NotificationViewModel(SchedulerProvider schedulerProvider, NotificationRepository notificationRepository) {
        super(schedulerProvider);
        this.repository = notificationRepository;
    }

    @Override
    public void init() {
        loadNotifications();
    }

    private void loadNotifications() {
        Disposable disposableNotifications = repository.getNotifications()
                .subscribe(
                        notificationResp ->
                        {
                            modelIsRefreshing.setValue(false);
                            repository.cacheNotifications(notificationResp.getData());
                            loadAndDisplayCachedNotifications();
                        },
                        throwable -> {
                            modelIsRefreshing.setValue(false);
                            handleError(throwable);
                        });
        getCompositeDisposable().add(disposableNotifications);
    }

    private void loadAndDisplayCachedNotifications() {
        List<NotificationModel> listData = repository.getCachedNotifications();
        if (listData != null) {
            dataNotitifications.setValue(listData);
        }
    }

    public void pullToRefreshNotifications() {
        loadNotifications();
    }

    public MutableLiveData<List<NotificationModel>> getDataNotitifications() {
        return dataNotitifications;
    }

    public MutableLiveData<Boolean> getModelIsRefreshing() {
        return modelIsRefreshing;
    }
}
