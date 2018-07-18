package vn.homecredit.hcvn.ui.profile;

import javax.inject.Inject;

import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class ProfileViewModel extends BaseViewModel {
    @Inject
    public ProfileViewModel(SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
    }
}
