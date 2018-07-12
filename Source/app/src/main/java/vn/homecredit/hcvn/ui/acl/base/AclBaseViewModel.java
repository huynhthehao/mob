package vn.homecredit.hcvn.ui.acl.base;

import vn.homecredit.hcvn.data.DataManager;
import vn.homecredit.hcvn.data.acl.AclDataManager;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public abstract class AclBaseViewModel<T> extends BaseViewModel<T> {

    private AclDataManager mAclDataManager;

    public AclBaseViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, AclDataManager aclDataManager) {
        super(dataManager, schedulerProvider);
        mAclDataManager = aclDataManager;
    }

    public AclDataManager getAclDataManager() {
        return mAclDataManager;
    }
}
