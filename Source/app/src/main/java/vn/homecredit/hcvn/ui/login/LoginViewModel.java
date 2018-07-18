/*
 * SplashViewModel.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/13/18 11:19 AM
 */

package vn.homecredit.hcvn.ui.login;

import android.databinding.ObservableField;
import com.androidnetworking.error.ANError;
import javax.inject.Inject;

import dagger.Module;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.ObservableString;
import vn.homecredit.hcvn.data.local.prefs.PreferencesHelper;
import vn.homecredit.hcvn.data.model.api.TokenResp;
import vn.homecredit.hcvn.data.remote.RestService;
import vn.homecredit.hcvn.service.ProfileService;
import vn.homecredit.hcvn.service.ResourceService;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.StringUtils;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

@Module
public class LoginViewModel extends BaseViewModel<LoginNavigator> {

    private final RestService restService;
    private final PreferencesHelper preferencesHelper;
    private final ProfileService profileService;
    public ObservableField<String> username = new ObservableField();
    public ObservableField<String> password = new ObservableField();
    private final ResourceService resourceService;

    @Inject
    public LoginViewModel(RestService restService, PreferencesHelper preferencesHelper, SchedulerProvider schedulerProvider, ProfileService profileService, ResourceService resourceService) {
        super(schedulerProvider);
        this.restService = restService;
        this.preferencesHelper = preferencesHelper;
        this.profileService = profileService;
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
        showMessage("Go to Forgot Password");
    }

    public void login(String phoneNumber, String password) {
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
                    String t = (((ANError) throwable).getErrorAsObject(TokenResp.class)).getErrorDescription();
                    showMessage(t);
                }));
    }
}
