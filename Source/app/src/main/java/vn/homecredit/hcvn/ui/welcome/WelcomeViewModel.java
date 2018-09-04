/*
 * SplashViewModel.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/12/18 5:16 PM
 */

package vn.homecredit.hcvn.ui.welcome;

import android.content.Context;
import android.text.TextUtils;

import javax.inject.Inject;

import vn.homecredit.hcvn.data.acl.AclDataManager;
import vn.homecredit.hcvn.data.repository.AccountRepository;
import vn.homecredit.hcvn.helpers.prefs.PreferencesHelper;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class WelcomeViewModel extends BaseViewModel<WelcomeListener> {
    private final AclDataManager mAclDataManager;
    private final PreferencesHelper preferencesHelper;
    private final AccountRepository accountRepository;
    private WelcomeListener listener;

    @Inject
    public WelcomeViewModel(SchedulerProvider schedulerProvider, AclDataManager aclDataManager, PreferencesHelper preferencesHelper, AccountRepository accountRepository) {
        super(schedulerProvider);
        mAclDataManager = aclDataManager;
        this.preferencesHelper = preferencesHelper;
        this.accountRepository = accountRepository;
    }

    public void setListener(WelcomeListener listener){
        this.listener = listener;
    }

    public void onLoginClick() {
        if(listener != null)
            listener.openLoginActivity();
    }

    public void onLanguageFlagClick() {
        preferencesHelper.changeLanguage();

        if(listener != null)
            listener.changeLanguage();
    }

    public String getCurrentLanguageCode() {
        return preferencesHelper.getLanguageCode();
    }

    public void onSignupClick() {
        if(this.listener != null)
            listener.openSignupActivity();
    }

    public void onCashloanClick() {
        String aclAccessToken = mAclDataManager.getAclAccessToken();
        if(listener == null)
            return;

        if (TextUtils.isEmpty(aclAccessToken)) {
            listener.openIntroActivity();
        } else {
            listener.openAclApplicationForm();
        }
    }

    public void processLogout(Context context) {
        accountRepository.logout(context);
    }
}
