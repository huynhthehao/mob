/*
 * ActivityBuilder.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/12/18 10:56 AM
 */

package vn.homecredit.hcvn.di.builder;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import vn.homecredit.hcvn.ui.acl.applicationForm.AclAfSelectLoan.AclAfSelectLoanFragmentProvider;
import vn.homecredit.hcvn.ui.acl.applicationForm.AclApplicationForm.AclApplicationFormActivity;
import vn.homecredit.hcvn.ui.acl.applicationForm.AclApplicationForm.AclApplicationFormModule;
import vn.homecredit.hcvn.ui.acl.introduction.AclIntroduction.AclIntroductionActivity;
import vn.homecredit.hcvn.ui.acl.introduction.AclIntroduction.AclIntroductionActivityModule;
import vn.homecredit.hcvn.ui.acl.introduction.AclSelectLoanType.AclSelectLoanTypeFragmentProvider;
import vn.homecredit.hcvn.ui.acl.validation.AclValidationActivity;
import vn.homecredit.hcvn.ui.acl.validation.AclValidationActivityModule;
import vn.homecredit.hcvn.ui.home.HomeActivity;
import vn.homecredit.hcvn.ui.login.LoginActivity;
//import vn.homecredit.hcvn.ui.login.LoginActivityModule;
import vn.homecredit.hcvn.ui.login.LoginViewModel;
import vn.homecredit.hcvn.ui.otp.OtpActivity;
//import vn.homecredit.hcvn.ui.otp.OtpActivityModule;
import vn.homecredit.hcvn.ui.otp.OtpViewModel;
import vn.homecredit.hcvn.ui.setpassword.SetPasswordActivity;
import vn.homecredit.hcvn.ui.setpassword.SetPasswordActivityModule;
import vn.homecredit.hcvn.ui.settings.changepass.ChangePassActivity;
import vn.homecredit.hcvn.ui.settings.changepass.ChangePassActivityModule;
import vn.homecredit.hcvn.ui.signup.SignUpActivity;
import vn.homecredit.hcvn.ui.signup.SignUpActivityModule;
import vn.homecredit.hcvn.ui.settings.SettingsActivity;
import vn.homecredit.hcvn.ui.splash.SplashActivity;
import vn.homecredit.hcvn.ui.splash.SplashActivityModule;
import vn.homecredit.hcvn.ui.welcome.WelcomeActivity;
import vn.homecredit.hcvn.ui.welcome.WelcomeActivityModule;

@Module
public abstract class ActivityBuilder {

//    @ContributesAndroidInjector(modules = {
//            MainActivityModule.class,
//            AboutFragmentProvider.class,
//            RateUsDialogProvider.class})
//    abstract MainActivity bindMainActivity();
//
    @ContributesAndroidInjector(modules = SplashActivityModule.class)
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector(modules = WelcomeActivityModule.class)
    abstract WelcomeActivity bindWelcomeActivity();

    @ContributesAndroidInjector(modules = LoginViewModel.class)
    abstract LoginActivity bindLoginActivity();

    @ContributesAndroidInjector(modules = SignUpActivityModule.class)
    abstract SignUpActivity bindSignupActivity();

    @ContributesAndroidInjector(modules = SetPasswordActivityModule.class)
    abstract SetPasswordActivity bindSetPasswordActivity();

    @ContributesAndroidInjector(modules = ChangePassActivityModule.class)
    abstract ChangePassActivity bindChangePassActivity();

    @ContributesAndroidInjector(modules = OtpViewModel.class)
    abstract OtpActivity bindOtpActivity();

    @ContributesAndroidInjector()
    abstract HomeActivity bindHomeActivity();

    @ContributesAndroidInjector(modules = {
            AclIntroductionActivityModule.class,
            AclSelectLoanTypeFragmentProvider.class})
    abstract AclIntroductionActivity bindAclIntroductionActivity();

    @ContributesAndroidInjector(modules = AclValidationActivityModule.class)
    abstract AclValidationActivity bindAclValidationActivity();

    @ContributesAndroidInjector(modules = {
            AclApplicationFormModule.class,
            AclAfSelectLoanFragmentProvider.class})
    abstract AclApplicationFormActivity bindAclApplicationFormActivity();

    @ContributesAndroidInjector()
    abstract SettingsActivity bindSettingsActivity();
}
