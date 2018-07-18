/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/17/18 3:23 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.ui.acl.introduction.AclIntroduction;

import dagger.Module;
import dagger.Provides;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

@Module
public class AclIntroductionActivityModule {
    @Provides
    AclIntroductionViewModel provideLoginViewModel(SchedulerProvider schedulerProvider) {
        return new AclIntroductionViewModel(schedulerProvider);
    }
}
