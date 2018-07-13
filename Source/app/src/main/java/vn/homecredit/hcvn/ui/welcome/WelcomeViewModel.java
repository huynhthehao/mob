/*
 * SplashViewModel.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/12/18 5:16 PM
 */

package vn.homecredit.hcvn.ui.welcome;

import android.text.TextUtils;

import javax.inject.Inject;

import vn.homecredit.hcvn.data.DataManager;
import vn.homecredit.hcvn.data.acl.AclDataManager;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class WelcomeViewModel extends BaseViewModel<WelcomeNavigator> {
    private final AclDataManager mAclDataManager;

    @Inject
    public WelcomeViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, AclDataManager aclDataManager) {
        super(dataManager, schedulerProvider);
        mAclDataManager = aclDataManager;
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

    public void onCashloanClick()
    {
        //TODO: Fake Token
//        mAclDataManager.setAclAccessToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJQaG9uZU51bWJlciI6IjA5ODk5OTk5OTkiLCJJZE51bWJlciI6IjEyMzEyMzEyMyIsIkRldmljZUlkIjoiY2M2YzQzYTEtYTQ5NC00ZTE1LWFkZjAtMDY3YjdiYTlkMDVjIiwiQXBwbGljYXRpb25Mb2FuSWQiOjQ0OSwiRXhwaXJlZERhdGUiOiIyMDE4LTA4LTEwVDE3OjM2OjA1LjA0NTg5NjgrMDc6MDAifQ.lnJbZ8rBBWrCnbKtGufDQfPXq2BUgof3tpMpxQO2PRQ");

        String aclAccessToken = mAclDataManager.getAclAccessToken();
        if (TextUtils.isEmpty(aclAccessToken)) {
            this.getNavigator().openIntroActivity();
        }
        else {
            this.getNavigator().openAclApplicationForm();
        }
    }

    public void startIntro()
    {
        this.getNavigator().startIntro();
    }
}
