/*
 * SplashViewModel.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/12/18 5:16 PM
 */

package vn.homecredit.hcvn.ui.welcome;

import vn.homecredit.hcvn.data.DataManager;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class WelcomeViewModel extends BaseViewModel<WelcomeNavigator> {
    public WelcomeViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void hardCode()
    {
        getNavigator().openWelcomeActivity();
    }

    public void onLoginClick()
    {
        this.getNavigator().openLoginActivity();
    }
    public void onSignupClick()
    {
        this.getNavigator().openSignupActivity();
    }

    public void startIntro()
    {
        this.getNavigator().startIntro();
    }
}
