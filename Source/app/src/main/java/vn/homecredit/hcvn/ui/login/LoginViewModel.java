/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/18/18 2:28 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.ui.login;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import javax.inject.Inject;

import dagger.Module;
import io.reactivex.disposables.Disposable;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.api.HcApiException;
import vn.homecredit.hcvn.data.repository.AccountRepository;
import vn.homecredit.hcvn.helpers.fingerprint.FingerPrintHelper;
import vn.homecredit.hcvn.service.ResourceService;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.FingerPrintAuthValue;
import vn.homecredit.hcvn.utils.StringUtils;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

@Module
public class LoginViewModel extends BaseViewModel<LoginNavigator> {
    public ObservableField<String> username = new ObservableField();
    public ObservableField<String> password = new ObservableField();
    public ObservableBoolean  showFingerPrint = new ObservableBoolean();

    private final AccountRepository accountRepository;
    private final FingerPrintHelper fingerPrintHelper;
    private final ResourceService resourceService;

    @Inject
    public LoginViewModel(AccountRepository accountRepository, SchedulerProvider schedulerProvider, FingerPrintHelper fingerPrintHelper, ResourceService resourceService) {
        super(schedulerProvider);
        this.accountRepository = accountRepository;
        this.fingerPrintHelper = fingerPrintHelper;
        this.resourceService = resourceService;

        FingerPrintAuthValue fingerSupportStatus = fingerPrintHelper.getFingerPrintAuthValue();
        showFingerPrint.set(fingerSupportStatus != FingerPrintAuthValue.NOT_SUPPORT);
    }


    public boolean validate() {
        if (StringUtils.isNullOrWhiteSpace(username.get()))
            return false;
        if (StringUtils.isNullOrWhiteSpace(password.get()))
            return false;

        return true;
    }

    public void onLoginClick() {
        if (!validate()) {
            String message = resourceService.getStringById(R.string.login_invalid_input);
            showMessage(message);
            return;
        }

        login(username.get(),password.get());
    }

    public void onFingerPrintClick(){
        FingerPrintAuthValue fingerSupportStatus = fingerPrintHelper.getFingerPrintAuthValue();
        //if()
    }

    public void onForgotPasswordClick(){
        getNavigator().forgetPassword();
    }

    /*public void login(String phoneNumber, String password) {
        setIsLoading(true);
        startSafeProcess(restService
                .getToken(phoneNumber, password)
                .flatMap(tokenResp -> {
                    String token = tokenResp.getAccessToken();
                    preferencesHelper.setAccessToken(token);
                    restService.getApiHeader().getProtectedApiHeader().setAccessToken(token);
                    return profileService.syncProfile();
                })
                .subscribe(tokenResp -> {
                    setIsLoading(false);
                    getNavigator().openHomeActivity();
                }, throwable -> {
                    setIsLoading(false);
                    if (throwable instanceof HcApiException) {
                        showMessage(((HcApiException) throwable).getErrorResponseMessage());
                    }
                }));
    }*/

    public void login(String phoneNumber, String password) {
        setIsLoading(true);
        Disposable subscribe = accountRepository.signIn(phoneNumber, password)
                .subscribe(profileResp -> {
                    setIsLoading(false);
                    if (profileResp == null) return;
                    if (profileResp.getResponseCode() != 0) {
                        showMessage(profileResp.getResponseMessage());
                    } else {
                        getNavigator().openHomeActivity();
                    }
                }, throwable -> {
                    setIsLoading(false);
                    if (throwable instanceof HcApiException) {
                        showMessage(((HcApiException) throwable).getErrorResponseMessage());
                    }
                });
        getCompositeDisposable().add(subscribe);
    }
}
