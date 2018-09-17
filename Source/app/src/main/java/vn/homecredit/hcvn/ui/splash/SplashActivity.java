/*
 * SplashActivity.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/12/18 11:27 AM
 */

package vn.homecredit.hcvn.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.databinding.ActivitySplashBinding;
import vn.homecredit.hcvn.ui.base.BaseStatefulActivity;
import vn.homecredit.hcvn.ui.custom.MyStatefulLayout;
import vn.homecredit.hcvn.ui.home.HomeActivity;
import vn.homecredit.hcvn.ui.welcome.WelcomeActivity;
import vn.homecredit.hcvn.utils.CommonUtils;

public class SplashActivity extends BaseStatefulActivity<ActivitySplashBinding, SplashViewModel> implements SplashListener {

    @Inject
    SplashViewModel viewModel;

    public static Intent newIntent(Context context) {
        return new Intent(context, SplashActivity.class);
    }

    @Override
    protected boolean getLoadingEnable() {
        return false;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public SplashViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public MyStatefulLayout getStatefulLayout() {
        return getViewDataBinding().statefulLayout;
    }

    @Override
    public void openWelcomeActivity() {
        Intent intent = WelcomeActivity.newIntent(SplashActivity.this);
        startActivity(intent);
        overridePendingTransition(0,0);
        finish();
    }

    @Override
    public void openHomeActivity() {
        HomeActivity.start(this,true);
        finish();
    }

    @Override
    public void retryCheckUpdate() {
        this.showOffline(view -> getViewModel().checkUpdate());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setListener(this);
    }

    @Override
    protected void init()  {
        if (!isTaskRoot()
                && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER)
                && getIntent().getAction() != null
                && getIntent().getAction().equals(Intent.ACTION_MAIN)) {
            finish();
            return;
        }
        super.init();
    }

    @Override
    public void showLoading() {
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(this);
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(0,0);
    }
}
