package vn.homecredit.hcvn.ui.contract.detail;


import android.arch.lifecycle.ViewModelProvider;

import dagger.Module;
import dagger.Provides;
import vn.homecredit.hcvn.di.ViewModelProviderFactory;

@Module
public class ContractDetailActivityModule {
    @Provides
    ViewModelProvider.Factory contractDetailViewModelFactory(ContractDetailViewModel contractDetailViewModel) {
        return new ViewModelProviderFactory<>(contractDetailViewModel);
    }

}
