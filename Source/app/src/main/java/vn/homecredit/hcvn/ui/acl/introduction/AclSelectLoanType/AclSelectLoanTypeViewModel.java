/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/5/18 5:18 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.ui.acl.introduction.AclSelectLoanType;

import javax.inject.Inject;

import timber.log.Timber;
import vn.homecredit.hcvn.data.DataManager;
import vn.homecredit.hcvn.data.acl.AclDataManager;
import vn.homecredit.hcvn.data.model.api.acl.CashLoanResponseCode;
import vn.homecredit.hcvn.data.model.api.acl.SuggestOfferResp;
import vn.homecredit.hcvn.data.model.message.MessageQuestion;
import vn.homecredit.hcvn.ui.acl.base.AclBaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class AclSelectLoanTypeViewModel extends AclBaseViewModel<AclSelectLoanTypeNavigator> {

    public static final int CURRENT_STEP = 0;

    @Inject
    public AclSelectLoanTypeViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, AclDataManager aclDataManager) {
        super(dataManager, schedulerProvider, aclDataManager, CURRENT_STEP);
    }

    public void onACLOClick() {
        getNavigator().goACLO();
    }


}
