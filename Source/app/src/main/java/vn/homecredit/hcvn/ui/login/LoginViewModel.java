/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/18/18 2:28 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.ui.login;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;

import javax.inject.Inject;

import dagger.Module;
import io.reactivex.disposables.Disposable;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.local.prefs.PreferencesHelper;
import vn.homecredit.hcvn.data.model.api.HcApiException;
import vn.homecredit.hcvn.data.remote.RestService;
import vn.homecredit.hcvn.data.repository.AccountRepository;
import vn.homecredit.hcvn.service.ResourceService;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.Log;
import vn.homecredit.hcvn.utils.StringUtils;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

@Module
public class LoginViewModel extends BaseViewModel<LoginNavigator> {
    public ObservableField<String> username = new ObservableField();
    public ObservableField<String> password = new ObservableField();
    private final AccountRepository accountRepository;
    private final ResourceService resourceService;

    @Inject
    public LoginViewModel(AccountRepository accountRepository, SchedulerProvider schedulerProvider,  ResourceService resourceService) {
        super(schedulerProvider);
        this.accountRepository = accountRepository;
        this.resourceService = resourceService;
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

    public void onForgotPasswordClick(){
        getNavigator().forgetPassword();
    }

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
