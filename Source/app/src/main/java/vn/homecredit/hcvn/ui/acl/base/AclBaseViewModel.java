package vn.homecredit.hcvn.ui.acl.base;

import vn.homecredit.hcvn.data.DataManager;
import vn.homecredit.hcvn.data.acl.AclDataManager;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public abstract class AclBaseViewModel<T> extends BaseViewModel<T> {

    private AclDataManager mAclDataManager;
    private int mCurrentStep;


    public AclBaseViewModel(SchedulerProvider schedulerProvider, AclDataManager aclDataManager, int currentStep) {
        super(schedulerProvider);
        mAclDataManager = aclDataManager;
        mCurrentStep = currentStep;
    }

    public AclDataManager getAclDataManager() {
        return mAclDataManager;
    }

    public int getCurrentStep() {
        return mCurrentStep;
    }
}
