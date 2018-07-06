/*
 * SplashViewModel.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/13/18 11:19 AM
 */

package vn.homecredit.hcvn.ui.login;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.util.Log;

import com.androidnetworking.error.ANError;

import javax.inject.Inject;

import vn.homecredit.hcvn.data.DataManager;
import vn.homecredit.hcvn.data.model.api.TokenResp;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.AppLogger;
import vn.homecredit.hcvn.utils.CommonUtils;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class LoginViewModel extends BaseViewModel {

    private ObservableField<String> username = new ObservableField<>("");
    private ObservableField<String> password = new ObservableField<>("");
    private MutableLiveData<Boolean> modelLoginSuccess = new MutableLiveData<>();

    @Inject
    public LoginViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public ObservableField<String> getUsername() {
        return username;
    }

    public ObservableField<String> getPassword() {
        return password;
    }

    public MutableLiveData<Boolean> getModelLoginSuccess() {
        return modelLoginSuccess;
    }

    public boolean validate(String phoneNumber, String password) {
        if (TextUtils.isEmpty(phoneNumber)) {
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            return false;
        }
        return true;
    }

    public void onLoginClick() {
        if (validate(username.get(), password.get())) {
           login(username.get(),password.get());
        }else {
            setModelErrorMessage("Số điện thoại hoặc mật khẩu không hợp lệ");
        }
    }

    public void login(String phoneNumber, String password) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .GetToken(phoneNumber, password)
                .doOnSuccess(response -> {
                    System.out.print(response.toString());
                })
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    modelLoginSuccess.setValue(true);
                }, throwable -> {
                    String t = (((ANError) throwable).getErrorAsObject(TokenResp.class)).getErrorDescription();
                    setModelErrorMessage(t);
                    setIsLoading(false);
                }));
    }
}
