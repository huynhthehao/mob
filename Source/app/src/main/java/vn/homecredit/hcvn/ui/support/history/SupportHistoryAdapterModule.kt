package vn.homecredit.hcvn.ui.support.history

import dagger.Module
import dagger.Provides
import vn.homecredit.hcvn.di.ViewModelProviderFactory

@Module
class SupportHistoryAdapterModule {
    @Provides
    fun providesAdapterModule(supportAdapterModule: SupportHistoryAdapterModule) = ViewModelProviderFactory<SupportHistoryAdapterModule>(supportAdapterModule)
}