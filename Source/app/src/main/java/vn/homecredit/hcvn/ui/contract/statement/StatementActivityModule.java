package vn.homecredit.hcvn.ui.contract.statement;

import android.arch.lifecycle.ViewModelProvider;

import dagger.Module;
import dagger.Provides;
import vn.homecredit.hcvn.di.ViewModelProviderFactory;

@Module
public class StatementActivityModule {
    @Provides
    ViewModelProvider.Factory statementViewModelFactory(StatementViewModel viewModel) {
        return new ViewModelProviderFactory<>(viewModel);
    }
}
