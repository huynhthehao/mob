package vn.homecredit.hcvn.ui.support.history

import android.arch.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import vn.homecredit.hcvn.di.ViewModelProviderFactory

@Module
class SupportHistoryActivityModule {
    @Provides
    fun providesSupportHistoryModule(supportHistoryViewModel: SupportHistoryViewModel): ViewModelProvider.Factory = ViewModelProviderFactory<SupportHistoryViewModel>(supportHistoryViewModel)

//    @Provides
//    fun providesAdapterModule(supportAdapterModule: SupportHistoryAdapterModule): ViewModelProvider.Factory = ViewModelProviderFactory<SupportHistoryAdapterModule>(supportAdapterModule)
}