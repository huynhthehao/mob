package vn.homecredit.hcvn.ui.payment.summary;

import android.arch.lifecycle.ViewModelProvider;

import dagger.Module;
import dagger.Provides;
import vn.homecredit.hcvn.di.ViewModelProviderFactory;

@Module
public class PaymentSummaryModule {
    @Provides
    ViewModelProvider.Factory summaryViewModelFactory(PaymentSummaryViewModel viewModel) {
        return new ViewModelProviderFactory<>(viewModel);
    }
}
