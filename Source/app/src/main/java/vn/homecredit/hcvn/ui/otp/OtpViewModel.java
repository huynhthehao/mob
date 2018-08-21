/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/5/18 1:05 PM, by Admin
 */

package vn.homecredit.hcvn.ui.otp;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.v4.provider.FontsContractCompat;
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
import vn.homecredit.hcvn.data.acl.AclDataManager;
import vn.homecredit.hcvn.data.model.api.contract.MasterContractVerifyResp;
import vn.homecredit.hcvn.data.model.enums.OtpFlow;
import vn.homecredit.hcvn.data.model.OtpPassParam;
import vn.homecredit.hcvn.data.model.api.OtpTimerResp;
import vn.homecredit.hcvn.data.model.api.OtpTimerRespData;
import vn.homecredit.hcvn.data.repository.AccountRepository;
import vn.homecredit.hcvn.data.repository.ContractRepository;
import vn.homecredit.hcvn.service.DeviceInfo;
import vn.homecredit.hcvn.service.ResourceService;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.Log;
import vn.homecredit.hcvn.utils.SpanBuilder;
import vn.homecredit.hcvn.utils.StringUtils;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

@Module
public class OtpViewModel extends BaseViewModel<OtpNavigator> {

    private String phoneNumber;
    private String phonePrimary;
    private String contractId;
    private OtpTimerRespData otpTimerInfo;
    private OtpFlow otpFlow = OtpFlow.CASH_LOAN_WALKIN;

    private static String remainingTimeText;
    private static long _remainingTime;

    private Timer timer;
    private boolean inffected = true;
    private int interval = 1000;
    private final ResourceService resourceService;
    private final DeviceInfo deviceInfo;
    private final AccountRepository accountRepository;
    private final ContractRepository contractRepository;
    private final AclDataManager aclDataManager;

    public ObservableBoolean resendVisibile = new ObservableBoolean(false);
    public ObservableBoolean agreementTermVisibile = new ObservableBoolean(false);
    public ObservableField<String> otp = new ObservableField("");
    private OtpPassParam otpPassParam;


    @Inject
    public OtpViewModel(SchedulerProvider schedulerProvider,
                        ResourceService resourceService,
                        DeviceInfo deviceInfo,
                        AccountRepository accountRepository,
                        ContractRepository contractRepository,
                        AclDataManager aclDataManager) {
        super(schedulerProvider);

        this.resourceService = resourceService;
        this.deviceInfo = deviceInfo;
        this.accountRepository = accountRepository;
        this.contractRepository = contractRepository;
        this.aclDataManager = aclDataManager;
    }


    public void initData(OtpPassParam otpPassParam) {
        if (otpPassParam != null
                && otpPassParam.getOtpTimerResp() != null
                && otpPassParam.getOtpTimerResp().getData() != null) {
            phonePrimary = otpPassParam.getOtpTimerResp().getData().getPrimaryPhone();
        }
        this.otpPassParam = otpPassParam;
        initData(otpPassParam.getPhoneNumber(), otpPassParam.getContractId(), otpPassParam.getOtpFlow(), otpPassParam.getOtpTimerResp().getData());
    }

    public void initData(String phoneNumber, String contractId, OtpFlow otpFlow, OtpTimerRespData otpTimerInfo) {
        this.phoneNumber = phoneNumber;
        this.contractId = contractId;
        this.otpFlow = otpFlow;
        agreementTermVisibile.set(otpFlow == OtpFlow.CASH_LOAN_WALKIN);
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
            case MASTER_CONTRACT:
            case MASTER_CONTRACT_CREDIT_CARD:
                verifyOtpMasterContract();
                break;
            case FORGOT_PASSWORD:
                verifyOtpForgetPassword();
                break;
            case SIGN_UP:
                verifyOtpSignUp();
                break;
            case CASH_LOAN_WALKIN: {
                verifyOtpForCLW();
            }
        }
    }

    public void onResendOtp() {
        stopTimer();
        switch (otpFlow) {
            case MASTER_CONTRACT:
            case MASTER_CONTRACT_CREDIT_CARD:
                resendOtpMasterContract();
                break;
            case FORGOT_PASSWORD:
                resendOtpForForgetPassword();
                break;
            case SIGN_UP:
                resendOtpForSignUp();
                break;
            case CASH_LOAN_WALKIN:
                resendOtpForCLW();
                break;
        }
    }

    private void verifyOtpMasterContract() {
        if (otpPassParam == null || otpPassParam.getMasterContract() == null) {
            showMessage(R.string.error_system);
            return;
        }
        String inputOtp = otp.get();
        if (StringUtils.isNullOrWhiteSpace(inputOtp)) {
            showMessage(R.string.otp_empty);
            return;
        }
        setIsLoading(true);
        boolean hasDisbursementBankAccount = otpPassParam.getMasterContract().isHasDisbursementBankAccount();
        boolean creditCardContract = otpPassParam.getMasterContract().isCreditCardContract();
        contractRepository.masterContractVerify(contractId, inputOtp, hasDisbursementBankAccount, creditCardContract)
                .subscribe(masterContractVerifyResp -> {
                    setIsLoading(false);
                    if (masterContractVerifyResp == null) {
                        showMessage(R.string.error_system);
                        return;
                    }
                    if (masterContractVerifyResp.isSuccess()) {
                        stopTimer();
                        otpPassParam.setMasterContractVerifyDataResp(masterContractVerifyResp.getMasterContractVerifyDataResp());
                        getNavigator().next(otpPassParam);
                    } else {
                        showMessage(masterContractVerifyResp.getResponseMessage());
                    }

                }, throwable -> {
                    setIsLoading(false);
                    handleError(throwable);
                });
    }

    private void resendOtpMasterContract() {
        if (otpPassParam == null || otpPassParam.getMasterContract() == null) {
            showMessage(R.string.error_system);
            return;
        }
        setIsLoading(true);
        Disposable resendProcess = contractRepository.masterContractApproved(otpPassParam.getMasterContract().getContractNumber())
                .subscribe(response -> {
                    setIsLoading(false);
                    if (response == null) {
                        return;
                    }
                    if (response.isVerified()) {
                        initData(otpPassParam);
                    } else {
                        showMessage(response.getResponseMessage());
                    }
                }, throwable -> {
                    setIsLoading(false);
                    handleError(throwable);
                });

        startSafeProcess(resendProcess);
    }

    private void resendOtpForForgetPassword() {
        setIsLoading(true);
        Disposable resendProcess = accountRepository.forgotPasswordVerify(phoneNumber, contractId)
                .subscribe(response -> {
                    setIsLoading(false);
                    if (response == null) {
                        return;
                    }
                    if (response.isVerified()) {
                        initData(phoneNumber, contractId, OtpFlow.FORGOT_PASSWORD, response.getData());
                    } else {
                        showMessage(response.getResponseMessage());
                    }
                }, throwable -> {
                    setIsLoading(false);
                    handleError(throwable);
                });

        startSafeProcess(resendProcess);
    }

    private void verifyOtpForgetPassword() {
        String inputOtp = otp.get();
        if (StringUtils.isNullOrWhiteSpace(inputOtp)) {
            showMessage(R.string.otp_empty);
            return;
        }
        setIsLoading(true);
        Disposable disposableVerityOTPSignUp = accountRepository.forgotPasswordOtp(phoneNumber, contractId, inputOtp)
                .subscribe(otpTimerResp -> {
                    setIsLoading(false);
                    if (otpTimerResp == null) {
                        return;
                    }
                    Log.debug(otpTimerResp.toString());
                    if (otpTimerResp.isVerified()) {
                        stopTimer();
                        getNavigator().next(new OtpPassParam(otpTimerResp, phoneNumber, contractId, OtpFlow.FORGOT_PASSWORD, inputOtp));
                    } else {
                        showMessage(otpTimerResp.getResponseMessage());
                    }
                }, throwable -> {
                    setIsLoading(false);
                    handleError(throwable);
                    Log.printStackTrace(throwable);
                });
        startSafeProcess(disposableVerityOTPSignUp);
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
                        initData(phoneNumber, contractId, OtpFlow.SIGN_UP, response.getData());
                    } else {
                        showMessage(response.getResponseMessage());
                    }
                }, throwable -> {
                    setIsLoading(false);
                    handleError(throwable);
                });

        startSafeProcess(resendProcess);
    }

    private void verifyOtpSignUp() {
        String inputOtp = otp.get();
        if (StringUtils.isNullOrWhiteSpace(inputOtp)) {
            showMessage(R.string.otp_empty);
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
                        getNavigator().next(new OtpPassParam(otpTimerResp, phoneNumber, contractId, OtpFlow.SIGN_UP, inputOtp));
                    } else {
                        showMessage(otpTimerResp.getResponseMessage());
                    }
                }, throwable -> {
                    setIsLoading(false);
                    handleError(throwable);
                    Log.printStackTrace(throwable);
                });
        startSafeProcess(disposableVerityOTPSignUp);
    }

    private void resendOtpForCLW() {
        setIsLoading(true);
        Disposable resendProcess = aclDataManager.verifyPersonal(phoneNumber, contractId, deviceInfo.getPlayerId())
                .subscribe(response -> {
                    setIsLoading(false);
                    if (response.getResponseCode() == 0 || response.getResponseCode() == 64) {
                        showMessage(R.string.otp_resend_success);

                        initData(phoneNumber, contractId, OtpFlow.CASH_LOAN_WALKIN, response.getData());
                    } else {
                        getNavigator().showMessage(response.getResponseMessage());
                    }
                }, throwable -> {
                    setIsLoading(false);
                    handleError(throwable);
                });

        startSafeProcess(resendProcess);
    }

    private void verifyOtpForCLW() {
        String inputOtp = otp.get();
        if (StringUtils.isNullOrWhiteSpace(inputOtp)) {
            showMessage(R.string.otp_empty);
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
                        getNavigator().next(null);
                    } else {
                        getNavigator().showMessage(response.getResponseMessage());
                    }
                }, throwable -> {
                    setIsLoading(false);
                    handleError(throwable);
                });

        startSafeProcess(verifyProcess);
    }

    private void setCountingDisplay() {
        TextView countingView = getNavigator().getControlById(R.id.countingText);
        SpanBuilder spanBuilder = new SpanBuilder();
        String otpHintRes = resourceService.getStringById(R.string.otp_hint);
        int primaryColor = resourceService.getColorById(R.color.colorPrimary);
        spanBuilder.appendWithSpace(String.format(otpHintRes, phonePrimary == null ? phoneNumber : phonePrimary));
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
