/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/2/18 4:29 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.ui.acl.validation;

import vn.homecredit.hcvn.data.DataManager;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class AclValidationViewModel extends BaseViewModel<AclValidationNavigator> {
    public AclValidationViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
