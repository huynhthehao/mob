/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/2/18 4:29 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.ui.acl.validation;

import android.icu.util.VersionInfo;

import dagger.Module;
import dagger.Provides;
import vn.homecredit.hcvn.data.DataManager;
import vn.homecredit.hcvn.rules.acl.AclRuleFactory;
import vn.homecredit.hcvn.service.DeviceInfo;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

@Module
public class AclValidationActivityModule {
    @Provides
    AclValidationViewModel provideLoginViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, AclRuleFactory aclRuleFactory, DeviceInfo deviceInfo) {
        return new AclValidationViewModel(dataManager, schedulerProvider, aclRuleFactory, deviceInfo);
    }
}
