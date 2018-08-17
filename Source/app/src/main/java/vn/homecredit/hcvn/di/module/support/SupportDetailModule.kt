package vn.homecredit.hcvn.di.module.support

import android.arch.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import vn.homecredit.hcvn.di.ViewModelProviderFactory
import vn.homecredit.hcvn.ui.support.detail.SupportDetailViewModel

@Module
class SupportDetailModule {

//    @Provides
//    fun providesSupportHistoryModule(historyViewModel: SupportHistoryViewModel): ViewModelProvider.Factory {
//        return ViewModelProviderFactory<SupportHistoryViewModel>(historyViewModel)
//    }

    @Provides
    fun providesSupportDetailModule(detailViewModel: SupportDetailViewModel): ViewModelProvider.Factory {
        return ViewModelProviderFactory<SupportDetailViewModel>(detailViewModel)
    }
}