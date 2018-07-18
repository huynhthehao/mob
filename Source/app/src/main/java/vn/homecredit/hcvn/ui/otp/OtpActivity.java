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


public class OtpActivity extends BaseActivity<ActivityOtpBinding, OtpViewModel> implements OtpNavigator {

    @Inject
    OtpViewModel otpViewModel;

    private OtpFlow currentOtpFlow;

    public static Intent getNewIntent(Context context, OtpPassParam otpPassParam) {
        Intent newInstance = new Intent(context, OtpActivity.class);
        try {
            if (otpPassParam != null) {
                Bundle currentBundle = new Bundle();
                currentBundle.putInt(OtpViewModel.FLOW_TYPE_KEY, otpPassParam.getOtpFlow().getValue());
                currentBundle.putString(OtpViewModel.PHONE_NUMBER_KEY, otpPassParam.getPhoneNumber());
                currentBundle.putString(OtpViewModel.CONTRACT_ID_KEY, otpPassParam.getContractId());

                currentBundle.putInt(OtpViewModel.OTP_LIVE_TIME_KEY, otpPassParam.getOtpTimerResp().getData().getOtpLiveTime());
                currentBundle.putInt(OtpViewModel.OTP_TIME_REMAIN_KEY, otpPassParam.getOtpTimerResp().getData().getRemainingTime());
                currentBundle.putInt(OtpViewModel.OTP_TIME_RESEND_KEY, otpPassParam.getOtpTimerResp().getData().getOtpTimeResend());
                currentBundle.putString(OtpViewModel.OTP_SOURCE_KEY, otpPassParam.getOtpTimerResp().getData().getSource());

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

            timerData.setOtpLiveTime(currentBundle.getInt(OtpViewModel.OTP_LIVE_TIME_KEY));
            timerData.setRemainingTime(currentBundle.getInt(OtpViewModel.OTP_TIME_REMAIN_KEY));
            timerData.setOtpTimeResend(currentBundle.getInt(OtpViewModel.OTP_TIME_RESEND_KEY));
            timerData.setSource(currentBundle.getString(OtpViewModel.OTP_SOURCE_KEY));

            currentOtpFlow = OtpFlow.parse(currentBundle.getInt(OtpViewModel.FLOW_TYPE_KEY));
            otpViewModel.initData(
                    currentBundle.getString(OtpViewModel.PHONE_NUMBER_KEY),
                    currentBundle.getString(OtpViewModel.CONTRACT_ID_KEY),
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
    public void next() {
        switch (currentOtpFlow){
            //case SignUp:   break;
            case CashLoanWalkin: {
                openSelectLoan();
                // await PushAsync<CLWOSelectLoanViewModel>();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void openSelectLoan(){
        Intent intent = AclApplicationFormActivity.newIntent(this);
        startActivity(intent);
        finish();
    }
}


