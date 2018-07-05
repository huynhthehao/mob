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
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import javax.inject.Inject;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.databinding.ActivityWelcomeBinding;
import vn.homecredit.hcvn.ui.acl.introduction.AclIntroductionActivity;
import vn.homecredit.hcvn.ui.acl.validation.AclValidationActivity;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.ui.login.LoginActivity;

public class WelcomeActivity extends BaseActivity<ActivityWelcomeBinding, WelcomeViewModel> implements WelcomeNavigator {

    boolean mHasAnimationStarted = false;

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
//        finish();
    }

    @Override
    public void openSignupActivity() {
        animateLogo();
    }

    @Override
    public void openIntroActivity() {
//        Intent intent = AclValidationActivity.newIntent(WelcomeActivity.this);
        Intent intent = AclIntroductionActivity.newIntent(WelcomeActivity.this);
        startActivity(intent);
    }

    @Override
    public void startIntro() {
        animateLogo();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityWelcomeBinding = getViewDataBinding();
        mWelcomeViewModel.setNavigator(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && !mHasAnimationStarted) {
            mHasAnimationStarted = true;

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getViewModel().startIntro();
                }
            }, 1000);
        }
    }

    public void animateLogo() {
        ImageView logoWelcomeImageViewFront = getViewDataBinding().logoWelcomeImageViewFront;
        ImageView logoWelcomeImageView = getViewDataBinding().logoWelcomeImageView;
        RelativeLayout root = getViewDataBinding().animateViewHolder;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            logoWelcomeImageViewFront.animate()
                    .translationX(logoWelcomeImageView.getX() - root.getWidth() / 2 + logoWelcomeImageViewFront.getWidth() / 2)
                    .translationY(logoWelcomeImageView.getY() - root.getHeight() / 2 + logoWelcomeImageViewFront.getHeight() / 2)
                    .setInterpolator(new AccelerateInterpolator()).withEndAction(new Runnable() {
                @Override
                public void run() {
                    root.setVisibility(View.GONE);
                }
            }).setDuration(1000);
        }
        else
        {
            root.setVisibility(View.GONE);
        }
    }
}
