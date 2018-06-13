/*
 * SplashActivity.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/13/18 10:55 AM
 */

package vn.homecredit.hcvn.ui.welcome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import io.reactivex.Single;
import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.api.VersionResp;
import vn.homecredit.hcvn.databinding.ActivitySplashBinding;
import vn.homecredit.hcvn.databinding.ActivityWelcomeBinding;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.ui.splash.SplashNavigator;
import vn.homecredit.hcvn.ui.splash.SplashViewModel;

public class WelcomeActivity extends BaseActivity<ActivityWelcomeBinding, WelcomeViewModel> implements SplashNavigator {

    @Inject
    WelcomeViewModel mWelcomeViewModel;

    public static Intent newIntent(Context context) {
        return new Intent(context, WelcomeActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public WelcomeViewModel getViewModel() {
        return mWelcomeViewModel;
    }

    @Override
    public void openWelcomeActivity() {

    }

    @Override
    public void openHomeActivity() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        openWelcomeActivity();
    }
}
