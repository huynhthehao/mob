/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/16/18 4:56 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import javax.inject.Inject;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.databinding.ActivityLoginBinding;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.ui.forgetpassword.ForgetPasswordActivity;
import vn.homecredit.hcvn.ui.home.HomeActivity;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> implements LoginNavigator {

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Inject
    LoginViewModel mLoginViewModel;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginViewModel getViewModel() {
        return mLoginViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adjustBottom();
    }

    public void openHomeActivity() {
        HomeActivity.start(LoginActivity.this, true);
        finish();
    }

    @Override
    public void forgetPassword() {
        ForgetPasswordActivity.start(this);
    }

    private void adjustBottom() {
        View bottomGroupView = getViewDataBinding().bottomGroupView;
        View scrollView = getViewDataBinding().scrollView;
        View topGroupView = getViewDataBinding().topGroupView;

        int marginTop = scrollView.getHeight() - topGroupView.getHeight() - bottomGroupView.getHeight();

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, marginTop, 0, 0);
        bottomGroupView.setLayoutParams(lp);
    }
}

