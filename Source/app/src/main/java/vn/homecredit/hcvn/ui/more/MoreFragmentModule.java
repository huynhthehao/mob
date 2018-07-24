/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 10/07/18 4:21 PM, by An.NguyenN1
 */

package vn.homecredit.hcvn.ui.more;

import android.arch.lifecycle.ViewModelProvider;

import dagger.Module;
import dagger.Provides;
import vn.homecredit.hcvn.ViewModelProviderFactory;

@Module
public class MoreFragmentModule {
    @Provides
    ViewModelProvider.Factory moreViewModelFactory(MoreViewModel moreViewModel) {
        return new ViewModelProviderFactory<>(moreViewModel);
    }
}
