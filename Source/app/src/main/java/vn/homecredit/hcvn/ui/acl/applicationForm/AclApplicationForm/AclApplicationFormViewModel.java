package vn.homecredit.hcvn.ui.acl.applicationForm.AclApplicationForm;

import vn.homecredit.hcvn.data.DataManager;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class AclApplicationFormViewModel extends BaseViewModel {
    public AclApplicationFormViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
