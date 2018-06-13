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
import vn.homecredit.hcvn.ui.splash.SplashActivity;
import vn.homecredit.hcvn.ui.splash.SplashActivityModule;

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
}