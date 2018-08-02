package vn.homecredit.hcvn.ui.notification;

import android.arch.lifecycle.ViewModelProvider;

import dagger.Module;
import dagger.Provides;
import vn.homecredit.hcvn.di.ViewModelProviderFactory;

@Module
public class NotificationFragmentModule {
    @Provides
    ViewModelProvider.Factory notificationViewModelFactory(NotificationViewModel viewModel) {
        return new ViewModelProviderFactory<>(viewModel);
    }
}
