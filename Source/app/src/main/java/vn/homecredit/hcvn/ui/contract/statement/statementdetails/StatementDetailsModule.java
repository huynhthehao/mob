package vn.homecredit.hcvn.ui.contract.statement.statementdetails;

import android.arch.lifecycle.ViewModelProvider;

import dagger.Module;
import dagger.Provides;
import vn.homecredit.hcvn.di.ViewModelProviderFactory;
import vn.homecredit.hcvn.ui.contract.statement.StatementViewModel;

@Module
public class StatementDetailsModule {
    @Provides
    ViewModelProvider.Factory statementDetailsViewModelFactory(StatementDetailsViewModel viewModel) {
        return new ViewModelProviderFactory<>(viewModel);
    }
}
