/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/9/18 2:02 PM, by Admin
 */


package vn.homecredit.hcvn.ui.otp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.parceler.Parcels;

import javax.inject.Inject;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.enums.OtpFlow;
import vn.homecredit.hcvn.data.model.OtpPassParam;
import vn.homecredit.hcvn.databinding.ActivityOtpBinding;
import vn.homecredit.hcvn.ui.acl.applicationForm.AclApplicationForm.AclApplicationFormActivity;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.ui.setpassword.SetPasswordActivity;


public class OtpActivity extends BaseActivity<ActivityOtpBinding, OtpViewModel> implements OtpNavigator {

    public static final String BUNDLE_OTPPARAM = "BUNDLE_OTPPARAM";

    @Inject
    OtpViewModel otpViewModel;

    private OtpFlow currentOtpFlow;

    public static void start(Context context, OtpPassParam otpPassParam) {
        Intent intent = getNewIntent(context, otpPassParam);
        context.startActivity(intent);
    }

    public static Intent getNewIntent(Context context, OtpPassParam otpPassParam) {
        Intent newInstance = new Intent(context, OtpActivity.class);
        if (otpPassParam != null) {
            Bundle currentBundle = new Bundle();
            currentBundle.putParcelable(BUNDLE_OTPPARAM, Parcels.wrap(otpPassParam));
            newInstance.putExtras(currentBundle);
        }
        return newInstance;
    }


    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_otp;
    }

    @Override
    public OtpViewModel getViewModel() {
        return otpViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        otpViewModel.setNavigator(this);

        if (getIntent().hasExtra(BUNDLE_OTPPARAM)) {
            OtpPassParam otpPassParam = Parcels.unwrap(getIntent().getParcelableExtra(BUNDLE_OTPPARAM));
            otpViewModel.initData(otpPassParam);
            currentOtpFlow = otpPassParam.getOtpFlow();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @SuppressWarnings("TypeParameterUnusedInFormals")
    @Override
    public <T extends View> T getControlById(@IdRes int id) {
        return findViewById(id);
    }


    @Override
    public void next(OtpPassParam data) {
        switch (currentOtpFlow){
            case MASTER_CONTRACT:
            case MASTER_CONTRACT_CREDIT_CARD:
                openMasterContractSign(data);
                break;
            case SIGN_UP:
            case FORGOT_PASSWORD:
                openSetPassword(data);
                break;
            case CASH_LOAN_WALKIN: {
                openSelectLoan();
            }
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void openSetPassword(OtpPassParam otpPassParam) {
        SetPasswordActivity.start(this, otpPassParam);
    }

    private void openSelectLoan() {
        Intent intent = AclApplicationFormActivity.newIntent(this);
        startActivity(intent);
        finish();
    }

    private void openMasterContractSign(OtpPassParam data) {

    }
}


