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
import vn.homecredit.hcvn.ui.acl.introduction.AclIntroduction.AclIntroductionActivity;
import vn.homecredit.hcvn.ui.acl.introduction.AclIntroduction.AclIntroductionActivityModule;
import vn.homecredit.hcvn.ui.acl.introduction.AclSelectLoanType.AclSelectLoanTypeFragmentProvider;
import vn.homecredit.hcvn.ui.acl.validation.AclValidationActivity;
import vn.homecredit.hcvn.ui.acl.validation.AclValidationActivityModule;
import vn.homecredit.hcvn.ui.home.HomeActivity;
import vn.homecredit.hcvn.ui.login.LoginActivity;
import vn.homecredit.hcvn.ui.login.LoginActivityModule;
import vn.homecredit.hcvn.ui.splash.SplashActivity;
import vn.homecredit.hcvn.ui.splash.SplashActivityModule;
import vn.homecredit.hcvn.ui.welcome.WelcomeActivity;
import vn.homecredit.hcvn.ui.welcome.WelcomeActivityModule;

@Module
public abstract class ActivityBuilder {

    //    @ContributesAndroidInjector(modules = {
//            FeedActivityModule.class,
//            BlogFragmentProvider.class,
//            OpenSourceFragmentProvider.class})
//    abstract FeedActivity bindFeedActivity();
//
//    @ContributesAndroidInjector(modules = LoginActivityModule.class)
//    abstract LoginActivity bindLoginActivity();
//
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

    @ContributesAndroidInjector(modules = LoginActivityModule.class)
    abstract LoginActivity bindLoginActivity();

    @ContributesAndroidInjector()
    abstract HomeActivity bindHomeActivity();

    @ContributesAndroidInjector(modules = {
            AclIntroductionActivityModule.class,
            AclSelectLoanTypeFragmentProvider.class})
    abstract AclIntroductionActivity bindAclIntroductionActivity();

    @ContributesAndroidInjector(modules = { AclValidationActivityModule.class})
    abstract AclValidationActivity bindAclValidationActivity();
}
