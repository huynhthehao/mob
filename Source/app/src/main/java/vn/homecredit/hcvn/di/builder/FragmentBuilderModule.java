/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 10/07/18 2:46 PM, by An.NguyenN1
 */

package vn.homecredit.hcvn.di.builder;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import vn.homecredit.hcvn.ui.contract.ContractFragment;
import vn.homecredit.hcvn.ui.contract.ContractFragmentModule;
import vn.homecredit.hcvn.ui.more.MoreFragment;
import vn.homecredit.hcvn.ui.more.MoreFragmentModule;

@Module
public abstract class FragmentBuilderModule {
    @ContributesAndroidInjector(modules = MoreFragmentModule.class)
    abstract MoreFragment moreFragment();

    @ContributesAndroidInjector(modules = ContractFragmentModule.class)
    abstract ContractFragment contractsFragment();
}
