/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/17/18 3:21 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.ui.acl.introduction.AclSelectLoanType;

import javax.inject.Inject;

import vn.homecredit.hcvn.data.acl.AclDataManager;
import vn.homecredit.hcvn.ui.acl.base.AclBaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class AclSelectLoanTypeViewModel extends AclBaseViewModel<AclSelectLoanTypeNavigator> {

    public static final int CURRENT_STEP = 0;

    @Inject
    public AclSelectLoanTypeViewModel(SchedulerProvider schedulerProvider, AclDataManager aclDataManager) {
        super(schedulerProvider, aclDataManager, CURRENT_STEP);
    }

    public void onACLOClick() {
        // TODO: Need to modified
        //getNavigator().goACLO();
    }

    public void onMapClick() {
        // TODO: Need to modified
        //getNavigator().goPos();
    }

}
