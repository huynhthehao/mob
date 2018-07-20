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

import javax.inject.Inject;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.OtpFlow;
import vn.homecredit.hcvn.data.model.OtpPassParam;
import vn.homecredit.hcvn.data.model.api.OtpTimerRespData;
import vn.homecredit.hcvn.databinding.ActivityOtpBinding;
import vn.homecredit.hcvn.ui.acl.applicationForm.AclApplicationForm.AclApplicationFormActivity;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.ui.setpassword.SetPasswordActivity;


public class OtpActivity extends BaseActivity<ActivityOtpBinding, OtpViewModel> implements OtpNavigator {

    public static final String PHONE_NUMBER_KEY = "PhoneNumber";
    public static final String CONTRACT_ID_KEY = "ContractId";
    public static final String FLOW_TYPE_KEY = "FlowType";
    public static final String OTP_LIVE_TIME_KEY = "OtpLiveTime";
    public static final String OTP_TIME_RESEND_KEY = "OtpTimeResend";
    public static final String OTP_TIME_REMAIN_KEY = "OtpRemainingTime";
    public static final String OTP_SOURCE_KEY = "OtpSource";

    @Inject
    OtpViewModel otpViewModel;

    private OtpFlow currentOtpFlow;

    public static void start(Context context,OtpPassParam otpPassParam) {
        Intent intent = getNewIntent(context, otpPassParam);
        context.startActivity(intent);
    }

    public static Intent getNewIntent(Context context, OtpPassParam otpPassParam) {
        Intent newInstance = new Intent(context, OtpActivity.class);
        try {
            if (otpPassParam != null) {
                Bundle currentBundle = new Bundle();
                currentBundle.putInt(FLOW_TYPE_KEY, otpPassParam.getOtpFlow().getValue());
                currentBundle.putString(PHONE_NUMBER_KEY, otpPassParam.getPhoneNumber());
                currentBundle.putString(CONTRACT_ID_KEY, otpPassParam.getContractId());

                currentBundle.putInt(OTP_LIVE_TIME_KEY, otpPassParam.getOtpTimerResp().getData().getOtpLiveTime());
                currentBundle.putInt(OTP_TIME_REMAIN_KEY, otpPassParam.getOtpTimerResp().getData().getRemainingTime());
                currentBundle.putInt(OTP_TIME_RESEND_KEY, otpPassParam.getOtpTimerResp().getData().getOtpTimeResend());
                currentBundle.putString(OTP_SOURCE_KEY, otpPassParam.getOtpTimerResp().getData().getSource());

                newInstance.putExtras(currentBundle);
            }
        }catch (Exception ex){}

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

        Bundle currentBundle = getIntent().getExtras();
        if (currentBundle != null) {
            OtpTimerRespData timerData = new OtpTimerRespData();

            timerData.setOtpLiveTime(currentBundle.getInt(OTP_LIVE_TIME_KEY));
            timerData.setRemainingTime(currentBundle.getInt(OTP_TIME_REMAIN_KEY));
            timerData.setOtpTimeResend(currentBundle.getInt(OTP_TIME_RESEND_KEY));
            timerData.setSource(currentBundle.getString(OTP_SOURCE_KEY));

            currentOtpFlow = OtpFlow.parse(currentBundle.getInt(FLOW_TYPE_KEY));
            otpViewModel.initData(
                    currentBundle.getString(PHONE_NUMBER_KEY),
                    currentBundle.getString(CONTRACT_ID_KEY),
                    currentOtpFlow,
                    timerData);
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
            case SignUp:
                openSetPassword(data);
                break;
            case CashLoanWalkin: {
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
    private void openSelectLoan(){
        Intent intent = AclApplicationFormActivity.newIntent(this);
        startActivity(intent);
        finish();
    }
}


