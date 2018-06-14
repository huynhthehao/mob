/*
 * SplashViewModel.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/13/18 11:19 AM
 */

package vn.homecredit.hcvn.ui.login;

import android.text.TextUtils;

import vn.homecredit.hcvn.data.DataManager;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class LoginViewModel extends BaseViewModel<LoginNavigator> {
    public LoginViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public boolean Validate(String phoneNumber, String password)
    {
        if (TextUtils.isEmpty(phoneNumber)) {
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            return false;
        }
        return true;
    }

    public void onLoginClick()
    {
        getNavigator().login();
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
                    getNavigator().openHomeActivity();
                }, throwable -> {
                    setIsLoading(false);
                }));
    }
}
