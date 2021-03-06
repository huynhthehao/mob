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

import javax.inject.Inject;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.LanguageCode;
import vn.homecredit.hcvn.databinding.ActivityWelcomeBinding;
import vn.homecredit.hcvn.helpers.LocaleHelper;
import vn.homecredit.hcvn.ui.acl.applicationForm.AclApplicationForm.AclApplicationFormActivity;
import vn.homecredit.hcvn.ui.acl.introduction.AclIntroduction.AclIntroductionActivity;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.ui.login.LoginActivity;
import vn.homecredit.hcvn.ui.offers.NoOfferFragment;
import vn.homecredit.hcvn.ui.offers.OfferActivity;
import vn.homecredit.hcvn.ui.signup.SignUpActivity;

public class WelcomeActivity extends BaseActivity<ActivityWelcomeBinding, WelcomeViewModel> implements WelcomeListener {
    public static final String IS_FORCE_LOGOUT = "is_force_logout";

    boolean mHasAnimationStarted = false;
    @Inject
    WelcomeViewModel viewModel;

    ActivityWelcomeBinding mActivityWelcomeBinding;

    public static void start(Context context) {
        Intent intent = new Intent(context, WelcomeActivity.class);
        context.startActivity(intent);
    }

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
        return viewModel;
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
    }

    @Override
    public void openSignupActivity() {
        SignUpActivity.start(this);
    }

    @Override
    public void openIntroActivity() {
        Intent intent = AclIntroductionActivity.newIntent(WelcomeActivity.this);
        startActivity(intent);
    }

    @Override
    public void openAclApplicationForm() {
        Intent intent = AclApplicationFormActivity.newIntent(WelcomeActivity.this);
        startActivity(intent);
    }


    @Override
    public void changeLanguage() {
        String langCode = viewModel.getCurrentLanguageCode();
        LocaleHelper.setLocale(getBaseContext(), langCode);

        ImageView flagImage = findViewById(R.id.languageFlag);

        if (langCode.equals("vi")) {
            flagImage.setImageResource(R.drawable.ic_flag_en);
        } else {
            flagImage.setImageResource(R.drawable.ic_flag_vn);
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityWelcomeBinding = getViewDataBinding();
        viewModel.setListener(this);

        String langCode = viewModel.getCurrentLanguageCode();
        ImageView flagImage = findViewById(R.id.languageFlag);

        if (langCode.equals(LanguageCode.ENGLISH)) {
            flagImage.setImageResource(R.drawable.ic_flag_en);
        } else {
            flagImage.setImageResource(R.drawable.ic_flag_vn);
        }

        Intent intent = getIntent();
        if (intent.hasExtra(IS_FORCE_LOGOUT)) {
            boolean isForceLogout = intent.getBooleanExtra(IS_FORCE_LOGOUT, false);
            if (!isForceLogout)
                return;
            viewModel.processLogout(this);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && !mHasAnimationStarted) {
            mHasAnimationStarted = true;

            final Handler handler = new Handler();
            handler.postDelayed(() -> animateLogo(), 600);
        }
    }


    private void animateLogo() {
        ImageView logoWelcomeImageViewFront = getViewDataBinding().logoWelcomeImageViewFront;
        ImageView logoWelcomeImageView = getViewDataBinding().logoWelcomeImageView;
        RelativeLayout root = getViewDataBinding().animateViewHolder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            logoWelcomeImageViewFront.animate()
                    .translationX(logoWelcomeImageView.getX() - root.getWidth() / 2 + logoWelcomeImageViewFront.getWidth() / 2)
                    .translationY(logoWelcomeImageView.getY() - root.getHeight() / 2 + logoWelcomeImageViewFront.getHeight() / 2)
                    .setInterpolator(new AccelerateInterpolator()).withEndAction(() -> root.setVisibility(View.GONE))
                    .setDuration(600);
        } else {
            root.setVisibility(View.GONE);
        }
    }
}
