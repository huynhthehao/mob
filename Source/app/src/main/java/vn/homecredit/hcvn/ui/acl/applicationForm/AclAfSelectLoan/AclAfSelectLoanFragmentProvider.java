package vn.homecredit.hcvn.ui.acl.applicationForm.AclAfSelectLoan;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public  abstract class AclAfSelectLoanFragmentProvider {
    @ContributesAndroidInjector(modules = AclAfSelectLoanFragmentModule.class)
    abstract AclAfSelectLoanFragment provideAclAfSelectLoanFragment();
}
