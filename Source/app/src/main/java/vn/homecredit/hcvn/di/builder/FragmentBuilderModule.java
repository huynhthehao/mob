/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 10/07/18 2:46 PM, by An.NguyenN1
 */

package vn.homecredit.hcvn.di.builder;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import vn.homecredit.hcvn.ui.contract.main.ContractFragment;
import vn.homecredit.hcvn.ui.contract.main.ContractFragmentModule;
import vn.homecredit.hcvn.ui.custom.SimpleRecyclerViewFragment;
import vn.homecredit.hcvn.ui.momo.myContract.MyContractFragment;
import vn.homecredit.hcvn.ui.more.MoreFragment;
import vn.homecredit.hcvn.ui.more.MoreFragmentModule;
import vn.homecredit.hcvn.ui.notification.NotificationFragmentModule;
import vn.homecredit.hcvn.ui.notification.NotificationsFragment;
import vn.homecredit.hcvn.ui.support.SupportFragment;
import vn.homecredit.hcvn.ui.support.SupportFragmentModule;

@Module
public abstract class FragmentBuilderModule {
    @ContributesAndroidInjector(modules = MoreFragmentModule.class)
    abstract MoreFragment moreFragment();

    @ContributesAndroidInjector(modules = NotificationFragmentModule.class)
    abstract NotificationsFragment notificationsFragment();

    @ContributesAndroidInjector(modules = ContractFragmentModule.class)
    abstract ContractFragment contractsFragment();

    @ContributesAndroidInjector(modules = ContractFragmentModule.class)
    abstract MyContractFragment myContractsFragment();

    @ContributesAndroidInjector(modules = SupportFragmentModule.class)
    abstract SupportFragment supportFragment();
}
