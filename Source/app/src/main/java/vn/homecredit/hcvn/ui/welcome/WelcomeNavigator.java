/*
 * SplashNavigator.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/12/18 11:29 AM
 */

package vn.homecredit.hcvn.ui.welcome;

public interface WelcomeNavigator {
    void openWelcomeActivity();
    void openHomeActivity();

    void openLoginActivity();
    void openSignupActivity();
    void openIntroActivity();
    void openAclApplicationForm();
    void startIntro();
}
