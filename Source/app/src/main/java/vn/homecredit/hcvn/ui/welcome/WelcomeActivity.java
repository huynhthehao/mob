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
import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.databinding.ActivityWelcomeBinding;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.ui.login.LoginActivity;

import javax.inject.Inject;

public class WelcomeActivity extends BaseActivity<ActivityWelcomeBinding, WelcomeViewModel> implements WelcomeNavigator {

    @Inject
    WelcomeViewModel mWelcomeViewModel;
    ActivityWelcomeBinding mActivityWelcomeBinding;

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
    public void openLoginActivity() {
        Intent intent = LoginActivity.newIntent(WelcomeActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityWelcomeBinding = getViewDataBinding();
        mWelcomeViewModel.setNavigator(this);
    }
}
