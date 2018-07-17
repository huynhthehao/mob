/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/5/18 1:05 PM, by Admin
 */

package vn.homecredit.hcvn.ui.otp;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.design.widget.TextInputEditText;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import dagger.Module;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.DataManager;
import vn.homecredit.hcvn.data.account.AccountRepository;
import vn.homecredit.hcvn.data.acl.AclDataManager;
import vn.homecredit.hcvn.data.model.OtpFlow;
import vn.homecredit.hcvn.data.model.OtpPassParam;
import vn.homecredit.hcvn.data.model.api.OtpTimerResp;
import vn.homecredit.hcvn.data.model.api.OtpTimerRespData;
import vn.homecredit.hcvn.service.DeviceInfo;
import vn.homecredit.hcvn.service.ResourceService;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.Log;
import vn.homecredit.hcvn.utils.SpanBuilder;
import vn.homecredit.hcvn.utils.StringUtils;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

@Module
public class OtpViewModel extends BaseViewModel<OtpNavigator> {

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
    private final AccountRepository accountRepository;
    private final AclDataManager aclDataManager;

    public ObservableBoolean resendVisibile = new ObservableBoolean(false);
    public ObservableBoolean agreementTermVisibile = new ObservableBoolean(false);
    private ObservableField<String> filedOtp = new ObservableField<>("");
    private MutableLiveData<OtpPassParam> modelOtpParamSignUp = new MutableLiveData<>();


    @Inject
    public OtpViewModel(DataManager dataManager,
                        SchedulerProvider schedulerProvider,
                        ResourceService resourceService,
                        DeviceInfo deviceInfo,
                        AccountRepository accountRepository,
                        AclDataManager aclDataManager) {
        super(dataManager, schedulerProvider);

        this.resourceService = resourceService;
        this.deviceInfo = deviceInfo;
        this.accountRepository = accountRepository;
        this.aclDataManager = aclDataManager;
    }

    public MutableLiveData<OtpPassParam> getModelOtpParamSignUp() {
        return modelOtpParamSignUp;
    }

    public ObservableField<String> getFiledOtp() {
        return filedOtp;
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

    public void onNextClick() {
        switch (otpFlow) {
            case SignUp:
                verifyOtpSignUp();
                break;
            case CashLoanWalkin: {
                verifyOtpForCLW();
            }
        }
    }

    public void onResendOtp() {
        stopTimer();
        switch (otpFlow) {
            case SignUp:
                resendOtpForSignUp();
                break;
            case CashLoanWalkin:
                resendOtpForCLW();
                break;
        }
    }

    private void resendOtpForSignUp() {
        setIsLoading(true);
        Disposable resendProcess = accountRepository.signupVerify(phoneNumber, contractId)
                .subscribe(response -> {
                    setIsLoading(false);
                    if (response == null) {
                        return;
                    }
                    if (response.isVerified()) {
                        initData(phoneNumber, contractId, OtpFlow.SignUp, response.getData());
                    }else {
                        setModelErrorMessage(response.getResponseMessage());
                    }
                }, throwable -> {
                    setIsLoading(false);
                });

        startSafeProcess(resendProcess);
    }

    private void verifyOtpSignUp() {
        String inputOtp = filedOtp.get();
        if (StringUtils.isNullOrWhiteSpace(inputOtp)) {
            String warningMessage = resourceService.getStringById(R.string.otp_empty);
            getNavigator().showError(warningMessage);
            return;
        }
        setIsLoading(true);
        Disposable disposableVerityOTPSignUp = accountRepository.verifyOtpSignUp(phoneNumber, contractId, inputOtp)
                .subscribe(otpTimerResp -> {
                    setIsLoading(false);
                    if (otpTimerResp == null) {
                        return;
                    }
                    Log.debug(otpTimerResp.toString());
                    if (otpTimerResp.isVerified()) {
                        stopTimer();
                        modelOtpParamSignUp.setValue(new OtpPassParam(otpTimerResp, phoneNumber, contractId, OtpFlow.SignUp, inputOtp ));
                    } else {
                        setModelErrorMessage(otpTimerResp.getResponseMessage());
                    }
                }, throwable -> {
                    setIsLoading(false);
                    Log.printStackTrace(throwable);
                });
        startSafeProcess(disposableVerityOTPSignUp);
    }

    private void resendOtpForCLW() {
        setIsLoading(true);
        Disposable resendProcess = aclDataManager.verifyPersonal(phoneNumber, contractId, deviceInfo.getPlayerId())
                .subscribe(response -> {
                    setIsLoading(false);
                    initData(phoneNumber, contractId, OtpFlow.CashLoanWalkin, response.getData());
                }, throwable -> {
                    setIsLoading(false);
                });

        startSafeProcess(resendProcess);
    }

    private void verifyOtpForCLW() {
        String inputOtp = filedOtp.get();
        if (StringUtils.isNullOrWhiteSpace(inputOtp)) {
            String warningMessage = resourceService.getStringById(R.string.otp_empty);
            getNavigator().showError(warningMessage);
            return;
        }
        setIsLoading(true);
        OtpTimerResp otpTimerResp = new OtpTimerResp();
        otpTimerResp.setData(otpTimerInfo);
        OtpPassParam otpPassParam = new OtpPassParam(otpTimerResp, phoneNumber, contractId, otpFlow);
        Disposable verifyProcess = aclDataManager.verifyPersonalOtp(otpPassParam, inputOtp)
                .subscribe(response -> {
                    setIsLoading(false);
                    if (response.getResponseCode() == 0 || response.getResponseCode() == 64) {
                        stopTimer();
                        aclDataManager.setAclAccessToken(response.getAccessToken());
                        getNavigator().next();
                    } else {
                        getNavigator().showError(response.getResponseMessage());
                    }
                }, throwable -> setIsLoading(false));
        startSafeProcess(verifyProcess);
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

    private void stopTimer() {
        if (timer != null)
            timer.cancel();
    }

    private void resetDisplay() {
        try {
            _remainingTime -= interval;
            if (_remainingTime <= (otpTimerInfo.getOtpLiveTime() - otpTimerInfo.getOtpTimeResend())) {
                resendVisibile.set(true);
            } else {
                resendVisibile.set(false);
            }
            boolean isTimeout = false;
            if (_remainingTime <= 0) {
                isTimeout = true;
                _remainingTime = 0;
            }
            Date date = new Date(_remainingTime);
            DateFormat formatter = new SimpleDateFormat("mm:ss");
            remainingTimeText = formatter.format(date);
            setCountingDisplay();
            if (isTimeout) {
                stopTimer();
                return;
            }
        } catch (Exception ex) {
        }
    }
}
