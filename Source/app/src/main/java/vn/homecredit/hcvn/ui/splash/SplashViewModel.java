/*
 * SplashViewModel.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/12/18 11:27 AM
 */

package vn.homecredit.hcvn.ui.splash;

import vn.homecredit.hcvn.data.DataManager;
import vn.homecredit.hcvn.data.remote.RestService;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class SplashViewModel extends BaseViewModel<SplashNavigator> {
    public SplashViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void hardCode()
    {
        getNavigator().openWelcomeActivity();
    }
}
