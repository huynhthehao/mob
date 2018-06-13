/*
 * SplashActivityModule.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/12/18 5:17 PM
 */

package vn.homecredit.hcvn.ui.login;

import dagger.Module;
import dagger.Provides;
import vn.homecredit.hcvn.data.DataManager;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

@Module
public class LoginActivityModule {
    @Provides
    LoginViewModel provideLoginViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new LoginViewModel(dataManager, schedulerProvider);
    }
}
