package vn.homecredit.hcvn.ui.support;

import android.arch.lifecycle.ViewModelProvider;

import dagger.Module;
import dagger.Provides;
import vn.homecredit.hcvn.di.ViewModelProviderFactory;

@Module
public class SupportFragmentModule {
    @Provides
    ViewModelProvider.Factory supportViewModelFactory(SupportViewModel viewModel) {
        return new ViewModelProviderFactory<>(viewModel);
    }
}
