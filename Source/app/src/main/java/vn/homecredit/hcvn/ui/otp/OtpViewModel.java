/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/5/18 1:05 PM, by Admin
 */

package vn.homecredit.hcvn.ui.otp;

import android.databinding.ObservableBoolean;
import android.support.design.widget.TextInputEditText;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.DataManager;
import vn.homecredit.hcvn.data.acl.AclDataManager;
import vn.homecredit.hcvn.data.model.OtpFlow;
import vn.homecredit.hcvn.data.model.OtpPassParam;
import vn.homecredit.hcvn.data.model.api.OtpTimerResp;
import vn.homecredit.hcvn.data.model.api.OtpTimerRespData;
import vn.homecredit.hcvn.service.DeviceInfo;
import vn.homecredit.hcvn.service.ResourceService;
import vn.homecredit.hcvn.ui.acl.base.AclBaseViewModel;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.SpanBuilder;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class OtpViewModel extends AclBaseViewModel<OtpNavigator> {

    public static final String PHONE_NUMBER_KEY = "PhoneNumber";
    public static final String CONTRACT_ID_KEY = "ContractId";
    public static final String FLOW_TYPE_KEY = "FlowType";

    public static final String OTP_LIVE_TIME_KEY = "OtpLiveTime";
    public static final String OTP_TIME_RESEND_KEY = "OtpTimeResend";
    public static final String OTP_TIME_REMAIN_KEY = "OtpRemainingTime";
    public static final String OTP_SOURCE_KEY = "OtpSource";

    private String phoneNumber;
    private String contractId;
    private OtpTimerRespData otpTimerInfo;
    private OtpFlow otpFlow = OtpFlow.CashLoanWalkin;

    private static String remainingTimeText;
    private static long _remainingTime;

    private Timer timer;
    private boolean inffected = true;
    private int interval = 1000;
    private final ResourceService resourceService;
    private final DeviceInfo deviceInfo;

    public ObservableBoolean resendVisibile = new ObservableBoolean(false);
    public ObservableBoolean agreementTermVisibile = new ObservableBoolean(false);

    @Inject
    public OtpViewModel(DataManager dataManager,
                        SchedulerProvider schedulerProvider,
                        ResourceService resourceService, DeviceInfo deviceInfo, AclDataManager aclDataManager) {
        super(dataManager, schedulerProvider, aclDataManager);

        this.resourceService = resourceService;
        this.deviceInfo = deviceInfo;
    }

    public void initData(String phoneNumber, String contractId, OtpFlow otpFlow, OtpTimerRespData otpTimerInfo) {
        this.phoneNumber = phoneNumber;
        this.contractId = contractId;
        this.otpFlow = otpFlow;
        agreementTermVisibile.set(otpFlow == OtpFlow.CashLoanWalkin);
        this.otpTimerInfo = otpTimerInfo;

        if (inffected)
            _remainingTime = otpTimerInfo.getRemainingTime();

        if (!inffected || _remainingTime <= 0) {
            _remainingTime = otpTimerInfo.getOtpLiveTime();
        }

        resendVisibile.set(false);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                resetDisplay();
            }
        }, 0, interval);
    }

    public void onNextClick()
    {
        switch (otpFlow){
            case SignUp:   break;
            case CashLoanWalkin: {
                verifyOtpForCLW();
            }
        }
    }

    public void onResendOtp(){
        switch (otpFlow){
            case SignUp:   break;
            case CashLoanWalkin: {
                resendOtpForCLW();
            }
        }
    }

    private void resendOtpForCLW() {
        setIsLoading(true);
        getCompositeDisposable().add(getAclDataManager()
                .verifyPersonal(phoneNumber, contractId, deviceInfo.getPlayerId())
                .doOnSuccess(response -> System.out.print(response.toString()))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    initData(phoneNumber, contractId, OtpFlow.CashLoanWalkin, response.getData());
                }, throwable -> {
                    setIsLoading(false);
                }));
    }

    private void verifyOtpForCLW() {
        setIsLoading(true);
        TextInputEditText otpInput = getNavigator().getControlById(R.id.otpInput);

        OtpTimerResp otpTimerResp = new OtpTimerResp();
        otpTimerResp.setData(otpTimerInfo);
        OtpPassParam otpPassParam = new OtpPassParam(otpTimerResp, phoneNumber, contractId, otpFlow);
        getCompositeDisposable().add(getAclDataManager()
                .verifyPersonalOtp(otpPassParam, otpInput.getText().toString())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);

                    if (response.getResponseCode() == 0 || response.getResponseCode() == 64) {
                        if(timer != null)
                            timer.cancel();
                        getNavigator().next();
                    } else {
                        getNavigator().showError(response.getResponseMessage());
                    }
                }, throwable -> {
                    setIsLoading(false);
                }));
    }


    private void setCountingDisplay() {
        TextView countingView = getNavigator().getControlById(R.id.countingText);

        SpanBuilder spanBuilder = new SpanBuilder();
        String otpHintRes = resourceService.getStringById(R.string.otp_hint);
        int primaryColor = resourceService.getColorById(R.color.colorPrimary);

        spanBuilder.appendWithSpace(String.format(otpHintRes, phoneNumber));
        spanBuilder.append(remainingTimeText, new ForegroundColorSpan(primaryColor));

        countingView.setText(spanBuilder.build());
    }


    private void resetDisplay() {
        try {
            if (_remainingTime < 1)
                return;

            _remainingTime -= interval;

            if (_remainingTime <= (otpTimerInfo.getOtpLiveTime() - otpTimerInfo.getOtpTimeResend())) {
                resendVisibile.set(true);
            } else {
                resendVisibile.set(false);
            }

            Date date = new Date(_remainingTime);
            DateFormat formatter = new SimpleDateFormat("mm:ss");
            remainingTimeText = formatter.format(date);
            setCountingDisplay();
        } catch (Exception ex) {
        }
    }
}
