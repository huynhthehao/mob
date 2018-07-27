package vn.homecredit.hcvn.ui.notification;

import javax.inject.Inject;

import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class NotificationViewModel extends BaseViewModel {

    @Inject
    public NotificationViewModel(SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
    }

    public void onRefresh() {
        //TODO
    }
}
