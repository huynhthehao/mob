/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/5/18 5:18 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.ui.acl.introduction.AclSelectLoanType;

import vn.homecredit.hcvn.data.DataManager;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class AclSelectLoanTypeViewModel extends BaseViewModel<AclSelectLoanTypeNavigator> {

    public AclSelectLoanTypeViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onACLOClick() {
        getNavigator().goACLO();
    }
}
