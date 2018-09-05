/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/18/18 2:28 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.ui.login;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Build;
import android.support.annotation.RequiresApi;

import javax.inject.Inject;

import dagger.Module;
import io.reactivex.disposables.Disposable;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.LoginInformation;
import vn.homecredit.hcvn.data.repository.AccountRepository;
import vn.homecredit.hcvn.helpers.fingerprint.FingerPrintHelper;
import vn.homecredit.hcvn.helpers.prefs.PreferencesHelper;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.FingerPrintAuthValue;
import vn.homecredit.hcvn.utils.StringUtils;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;


@Module
public class LoginViewModel extends BaseViewModel<LoginListener> {
    public ObservableField<String> username = new ObservableField("");
    public ObservableField<String> password = new ObservableField("");
    public ObservableBoolean showFingerPrint = new ObservableBoolean(false);

    private final AccountRepository accountRepository;
    private final FingerPrintHelper fingerPrintHelper;
    private final PreferencesHelper preferencesHelper;
    private LoginListener listener;

    @Inject
    public LoginViewModel(AccountRepository accountRepository, SchedulerProvider schedulerProvider,
                          FingerPrintHelper fingerPrintHelper, PreferencesHelper preferencesHelper, Context context) {
        super(schedulerProvider);
        this.accountRepository = accountRepository;
        this.fingerPrintHelper = fingerPrintHelper;
        this.preferencesHelper = preferencesHelper;
        //this.context = context;

        FingerPrintAuthValue fingerSupportStatus = fingerPrintHelper.getFingerPrintAuthValue();
        showFingerPrint.set(fingerSupportStatus != FingerPrintAuthValue.NOT_SUPPORT);

        String currentUser = accountRepository.getCurrentUser();
        if (!StringUtils.isNullOrWhiteSpace(currentUser)) ;
        username.set(currentUser);
    }

    public void setListener(LoginListener listener){
        this.listener = listener;
    }

    public boolean validate() {
        if (StringUtils.isNullOrWhiteSpace(username.get()))
            return false;
        if (StringUtils.isNullOrWhiteSpace(password.get()))
            return false;

        return true;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onLoginClick() {
       if (!validate()) {
            showMessage(R.string.login_invalid_input);
            return;
        }

        login(username.get(), password.get());
    }

    public boolean fingerPrintEnable() {
        FingerPrintAuthValue fingerSupportStatus = fingerPrintHelper.getFingerPrintAuthValue();
        if (fingerSupportStatus != FingerPrintAuthValue.SUPPORT_AND_ENABLED) {
            return false;
        }

        return preferencesHelper.getFingerPrintSetting();
    }

    public void onForgotPasswordClick() {
        if(listener == null)
            return;

        listener.forgetPassword();
    }

    public void onFingerPrintClick() {
        FingerPrintAuthValue fingerSupportStatus = fingerPrintHelper.getFingerPrintAuthValue();
        if (fingerSupportStatus == FingerPrintAuthValue.SUPPORT_BUT_NOT_ENABLE) {
            showMessage(R.string.fingerprint_system_not_available);
            return;
        }

        boolean fingerPrintEnable = preferencesHelper.getFingerPrintSetting();

        if (!fingerPrintEnable) {
            showMessage(R.string.fingerprint_not_enable);
            return;
        }

        if(listener == null)
            return;

        listener.showFingerPrintAuthDialog();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void autoLogin() {
        LoginInformation loginInformation = accountRepository.getCurrentLoginInfo();
        if (loginInformation == null || StringUtils.isNullOrWhiteSpace(loginInformation.phoneNumber)
                || StringUtils.isNullOrWhiteSpace(loginInformation.password)) {
            showMessage(R.string.fingerprint_login_info_not_found);
            return;
        }

        password.set(loginInformation.password);
        login(loginInformation.phoneNumber, loginInformation.password);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void login(String phoneNumber, String password) {
        setIsLoading(true);
        Disposable subscribe = accountRepository.signIn(phoneNumber, password)
                .subscribe(profileResp -> {
                    setIsLoading(false);

                    if (profileResp == null)
                        return;

                    if (profileResp.getResponseCode() != 0) {
                        showMessage(profileResp.getResponseMessage());
                    } else {
                        accountRepository.saveLoginInfo(phoneNumber, password);

                        if(listener != null)
                            listener.openHomeActivity();
                    }
                }, throwable -> {
                    setIsLoading(false);
                    handleError(throwable);
                });

        startSafeProcess(subscribe);
    }
}
