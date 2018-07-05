/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/5/18 5:27 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.ui.acl.introduction.AclSelectLoanType;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AclSelectLoanTypeFragmentProvider {
    @ContributesAndroidInjector(modules = AclSelectLoanTypeFragmentModule.class)
    abstract AclSelectLoanTypeFragment provideAboutFragmentFactory();
}
