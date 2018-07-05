/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/5/18 5:46 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.ui.acl.introduction.AclIntroduction;

import dagger.Module;
import dagger.Provides;
import vn.homecredit.hcvn.data.DataManager;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

@Module
public class AclIntroductionActivityModule {
    @Provides
    AclIntroductionViewModel provideLoginViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new AclIntroductionViewModel(dataManager, schedulerProvider);
    }
}
