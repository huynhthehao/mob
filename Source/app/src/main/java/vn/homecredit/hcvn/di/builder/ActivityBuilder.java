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
import vn.homecredit.hcvn.di.module.support.SupportDetailModule;
import vn.homecredit.hcvn.ui.acl.applicationForm.AclAfSelectLoan.AclAfSelectLoanFragmentProvider;
import vn.homecredit.hcvn.ui.acl.applicationForm.AclApplicationForm.AclApplicationFormActivity;
import vn.homecredit.hcvn.ui.acl.applicationForm.AclApplicationForm.AclApplicationFormModule;
import vn.homecredit.hcvn.ui.acl.introduction.AclIntroduction.AclIntroductionActivity;
import vn.homecredit.hcvn.ui.acl.introduction.AclIntroduction.AclIntroductionActivityModule;
import vn.homecredit.hcvn.ui.acl.introduction.AclSelectLoanType.AclSelectLoanTypeFragmentProvider;
import vn.homecredit.hcvn.ui.acl.validation.AclValidationActivity;
import vn.homecredit.hcvn.ui.acl.validation.AclValidationActivityModule;
import vn.homecredit.hcvn.ui.contract.creditcard.detail.CreditCardDetailActivity;
import vn.homecredit.hcvn.ui.contract.creditcard.detail.CreditCardDetailViewModel;
import vn.homecredit.hcvn.ui.contract.creditcard.list.CreditCardListActivity;
import vn.homecredit.hcvn.ui.contract.creditcard.list.CreditCardListViewModel;
import vn.homecredit.hcvn.ui.contract.creditcard.transaction.TransactionDetailActivity;
import vn.homecredit.hcvn.ui.contract.creditcard.transaction.TransactionDetailViewModel;
import vn.homecredit.hcvn.ui.contract.creditcard.transaction.TransactionListActivity;
import vn.homecredit.hcvn.ui.contract.creditcard.transaction.TransactionListViewModel;
import vn.homecredit.hcvn.ui.contract.detail.ContractDetailActivity;
import vn.homecredit.hcvn.ui.contract.detail.ContractDetailActivityModule;
import vn.homecredit.hcvn.ui.contract.masterContractDoc.MasterContractDocActivity;
import vn.homecredit.hcvn.ui.contract.masterContractDoc.MasterContractDocActivityModule;
import vn.homecredit.hcvn.ui.contract.masterContractSign.MasterContractSignActivity;
import vn.homecredit.hcvn.ui.contract.masterContractSign.MasterContractSignActivityModule;
import vn.homecredit.hcvn.ui.contract.masterContractSuccess.MasterContractSuccessActivity;
import vn.homecredit.hcvn.ui.contract.masterContractSuccess.MasterContractSuccessActivityModule;
import vn.homecredit.hcvn.ui.contract.paymentHistory.PaymentHistoryActivity;
import vn.homecredit.hcvn.ui.contract.paymentHistory.PaymentHistoryActivityModule;
import vn.homecredit.hcvn.ui.contract.scheduleDetail.ScheduleDetailActivity;
import vn.homecredit.hcvn.ui.contract.scheduleDetail.ScheduleDetailActivityModule;
import vn.homecredit.hcvn.ui.contract.statement.StatementActivityModule;
import vn.homecredit.hcvn.ui.contract.statement.StatementsActivity;
import vn.homecredit.hcvn.ui.contract.statement.statementdetails.StatementDetailsActivity;
import vn.homecredit.hcvn.ui.contract.statement.statementdetails.StatementDetailsModule;
import vn.homecredit.hcvn.ui.contract.summaryContract.SummaryContractActivity;
import vn.homecredit.hcvn.ui.contract.summaryContract.SummaryContractActivityModule;
import vn.homecredit.hcvn.ui.forgetpassword.ForgetPasswordActivity;
import vn.homecredit.hcvn.ui.forgetpassword.ForgetPasswordActivityModule;
import vn.homecredit.hcvn.ui.home.HomeActivity;
import vn.homecredit.hcvn.ui.login.LoginActivity;
import vn.homecredit.hcvn.ui.login.LoginViewModel;
import vn.homecredit.hcvn.ui.map.PayMapActivity;
import vn.homecredit.hcvn.ui.otp.OtpActivity;
import vn.homecredit.hcvn.ui.otp.OtpViewModel;
import vn.homecredit.hcvn.ui.profile.ProfileActivity;
import vn.homecredit.hcvn.ui.setpassword.SetPasswordActivity;
import vn.homecredit.hcvn.ui.setpassword.SetPasswordActivityModule;
import vn.homecredit.hcvn.ui.settings.SettingsActivity;
import vn.homecredit.hcvn.ui.settings.changepass.ChangePassActivity;
import vn.homecredit.hcvn.ui.settings.changepass.ChangePassActivityModule;
import vn.homecredit.hcvn.ui.signup.SignUpActivity;
import vn.homecredit.hcvn.ui.signup.SignUpActivityModule;
import vn.homecredit.hcvn.ui.splash.SplashActivity;
import vn.homecredit.hcvn.ui.splash.SplashActivityModule;
import vn.homecredit.hcvn.ui.support.detail.SupportDetailActivity;
import vn.homecredit.hcvn.ui.support.history.SupportHistoryActivity;
import vn.homecredit.hcvn.ui.support.history.SupportHistoryActivityModule;
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

    @ContributesAndroidInjector(modules = ForgetPasswordActivityModule.class)
    abstract ForgetPasswordActivity bindForgetPasswordActivity();

    @ContributesAndroidInjector(modules = SummaryContractActivityModule.class)
    abstract SummaryContractActivity bindSigningActivity();

    @ContributesAndroidInjector(modules = OtpViewModel.class)
    abstract OtpActivity bindOtpActivity();

    @ContributesAndroidInjector(modules = CreditCardListViewModel.class)
    abstract CreditCardListActivity bindCreditCardListActivity();

    @ContributesAndroidInjector(modules = CreditCardDetailViewModel.class)
    abstract CreditCardDetailActivity bindCreditCardDetailActivity();

    @ContributesAndroidInjector(modules = TransactionListViewModel.class)
    abstract TransactionListActivity bindTransactionListActivity();

    @ContributesAndroidInjector(modules = TransactionDetailViewModel.class)
    abstract TransactionDetailActivity bindTransactionDetailActivity();

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

    @ContributesAndroidInjector()
    abstract ProfileActivity bindProfileActivity();

    @ContributesAndroidInjector(modules = MasterContractDocActivityModule.class)
    abstract MasterContractDocActivity bindMastercontractDocActivity();

    @ContributesAndroidInjector()
    abstract PayMapActivity bindPayMapActivity();

    @ContributesAndroidInjector(modules = ContractDetailActivityModule.class)
    abstract ContractDetailActivity bindContractDetailActivity();

    @ContributesAndroidInjector(modules = MasterContractSignActivityModule.class)
    abstract MasterContractSignActivity bindMasterContractSignActivity();

    @ContributesAndroidInjector(modules = ScheduleDetailActivityModule.class)
    abstract ScheduleDetailActivity bindScheduleDetailActivity();

    @ContributesAndroidInjector(modules = PaymentHistoryActivityModule.class)
    abstract PaymentHistoryActivity bindPaymentHistoryActivity();

    @ContributesAndroidInjector(modules = SupportHistoryActivityModule.class)
    abstract SupportHistoryActivity bindSupportHistoryActivity();

    @ContributesAndroidInjector(modules = SupportDetailModule.class)
    abstract SupportDetailActivity bindSupportDetailActivity();

    @ContributesAndroidInjector(modules = MasterContractSuccessActivityModule.class)
    abstract MasterContractSuccessActivity bindMasterContractSuccessActivity();


    @ContributesAndroidInjector(modules = StatementActivityModule.class)
    abstract StatementsActivity bindStatementActivity();

    @ContributesAndroidInjector(modules = StatementDetailsModule.class)
    abstract StatementDetailsActivity bindStatementDetailsActivity();

}
