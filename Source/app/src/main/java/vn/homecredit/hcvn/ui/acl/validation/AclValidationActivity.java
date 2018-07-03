/*
 * AclValidationPage.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/2/18 3:08 PM
 */

package vn.homecredit.hcvn.ui.acl.validation;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import javax.inject.Inject;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.OtpPassParam;
import vn.homecredit.hcvn.databinding.ActivityAclValidationBinding;
import vn.homecredit.hcvn.ui.base.BaseActivity;

public class AclValidationActivity extends BaseActivity<ActivityAclValidationBinding, AclValidationViewModel> implements AclValidationNavigator {

    @Inject
    AclValidationViewModel mAclValidationViewModel;
    private ActivityAclValidationBinding mActivityLoginBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, AclValidationActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_acl_validation;
    }

    @Override
    public AclValidationViewModel getViewModel() {
        return mAclValidationViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityLoginBinding = getViewDataBinding();
        mAclValidationViewModel.setNavigator(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void next() {
        String phoneNumber = mActivityLoginBinding.phoneNumberTextView.getText().toString();
        String idNumber = mActivityLoginBinding.idNumberTextView.getText().toString();
        if (isValid()) {
            hideKeyboard();
            getViewModel().verifyPersonal(phoneNumber, idNumber);
        } else {
            showError("Thông tin không hợp lệ");
        }
    }

    @Override
    public void openOtpActivity(OtpPassParam otpPassParam) {
        Toast.makeText(this, "TODO: Open OTP" + otpPassParam.toString(), Toast.LENGTH_SHORT).show();
//        Intent intent = OtpActivity.newIntent(OtpActivity.this);
//        startActivity(intent);
//        finish();
    }

    protected boolean isValid() {
        return !mActivityLoginBinding.phoneNumberTIL.isErrorEnabled() && !mActivityLoginBinding.idNumberTIL.isErrorEnabled();
    }
}
