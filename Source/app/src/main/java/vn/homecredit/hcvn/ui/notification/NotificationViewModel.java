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
    private final NotificationRepository notificationRepository;

    public MutableLiveData<List<NotificationModel>> getDataNotitifications() {
        return dataNotitifications;
    }

    private MutableLiveData<List<NotificationModel>> dataNotitifications = new MutableLiveData<>();

    @Inject
    public NotificationViewModel(SchedulerProvider schedulerProvider, NotificationRepository notificationRepository) {
        super(schedulerProvider);
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void init() {
        loadNotifications();
    }

    private void loadNotifications() {
        Disposable disposableNotifications = notificationRepository.getNotifications()
                .subscribe(notificationResp -> dataNotitifications.setValue(notificationResp.getData()),
                        throwable -> {
                            setIsLoading(false);
                            handleError(throwable);
                        });
        getCompositeDisposable().add(disposableNotifications);
    }
}
