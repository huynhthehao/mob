package vn.homecredit.hcvn.ui.contract.paymentHistory;

import android.arch.lifecycle.ViewModelProvider;

import dagger.Module;
import dagger.Provides;
import vn.homecredit.hcvn.di.ViewModelProviderFactory;

@Module
public class PaymentHistoryActivityModule {
    @Provides
    ViewModelProvider.Factory contractPaymentHistoryModule(PaymentHistoryViewModel paymentHistoryViewModel) {
        return new ViewModelProviderFactory<>(paymentHistoryViewModel);
    }
}
