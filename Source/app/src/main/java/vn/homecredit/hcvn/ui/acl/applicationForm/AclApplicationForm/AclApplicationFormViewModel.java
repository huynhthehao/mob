package vn.homecredit.hcvn.ui.acl.applicationForm.AclApplicationForm;

import javax.inject.Inject;

import vn.homecredit.hcvn.data.DataManager;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class AclApplicationFormViewModel extends BaseViewModel {

    @Inject
    public AclApplicationFormViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
