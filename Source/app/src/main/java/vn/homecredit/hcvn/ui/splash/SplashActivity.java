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
import android.support.annotation.Nullable;

import javax.inject.Inject;

import io.reactivex.Single;
import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.api.ApiResponse;
import vn.homecredit.hcvn.databinding.ActivitySplashBinding;
import vn.homecredit.hcvn.ui.base.BaseActivity;

public class SplashActivity extends BaseActivity<ActivitySplashBinding, SplashViewModel> implements SplashNavigator {

    @Inject
    SplashViewModel mSplashViewModel;

    public static Intent newIntent(Context context) {
        return new Intent(context, SplashActivity.class);
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
        return mSplashViewModel;
    }

    @Override
    public void openWelcomeActivity() {
        Single<ApiResponse> single = this.mSplashViewModel.getRestService().CheckUpdateAsync();
        single.doOnSuccess(response -> {
            System.out.println("Do" + response);
        }).subscribeOn(getViewModel().getSchedulerProvider().io()).observeOn(getViewModel().getSchedulerProvider().ui()).subscribe(apiResponse -> {
            System.out.println("Done " + apiResponse);
        }, throwable -> {
            System.out.println(throwable.getMessage());
        });
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
