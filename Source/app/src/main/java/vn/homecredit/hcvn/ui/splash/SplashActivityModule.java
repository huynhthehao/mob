/*
 * SplashActivityModule.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/12/18 11:27 AM
 */

package vn.homecredit.hcvn.ui.splash;

import dagger.Module;
import dagger.Provides;
import vn.homecredit.hcvn.data.DataManager;
import vn.homecredit.hcvn.data.remote.RestService;
import vn.homecredit.hcvn.service.OneSignalService;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

@Module
public class SplashActivityModule {
    @Provides
    SplashViewModel provideSplashViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, OneSignalService oneSignalService) {
        return new SplashViewModel(dataManager, schedulerProvider, oneSignalService);
    }
}
