package vn.homecredit.hcvn.ui.support.history

import dagger.Module
import dagger.Provides
import vn.homecredit.hcvn.di.ViewModelProviderFactory

@Module
class SupportHistoryActivityModule {
    @Provides
    fun providesSupportHistoryModule(supportHistoryViewModel: SupportHistoryViewModel) = ViewModelProviderFactory<SupportHistoryViewModel>(supportHistoryViewModel)
}