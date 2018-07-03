/*
 * LoginActivity.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/13/18 1:34 PM
 */

package vn.homecredit.hcvn.ui.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.databinding.ActivityLoginBinding;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.ui.home.HomeActivity;
import vn.homecredit.hcvn.ui.welcome.WelcomeActivity;
import vn.homecredit.hcvn.utils.CommonUtils;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> implements LoginNavigator {

    @Inject
    LoginViewModel mLoginViewModel;
    private ActivityLoginBinding mActivityLoginBinding;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

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
        mActivityLoginBinding = getViewDataBinding();
        mLoginViewModel.setNavigator(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adjustBottom();
    }

    @Override
    public void openHomeActivity() {
        Intent intent = HomeActivity.newIntent(LoginActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void login() {
        String phoneNumber = getViewDataBinding().phoneNumberTextView.getText().toString();
        String password = getViewDataBinding().passwordTextView.getText().toString();
        if (getViewModel().Validate(phoneNumber, password)) {
            hideKeyboard();
            getViewModel().login(phoneNumber, password);
        } else {
            showError("Số điện thoại hoặc mật khẩu không hợp lệ");
        }
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
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

