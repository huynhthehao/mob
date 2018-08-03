package vn.homecredit.hcvn.ui.contract.scheduleDetail;


import android.arch.lifecycle.ViewModelProvider;

import dagger.Module;
import dagger.Provides;
import vn.homecredit.hcvn.di.ViewModelProviderFactory;

@Module
public class ScheduleDetailActivityModule {
    @Provides
    ViewModelProvider.Factory contractDetailViewModelFactory(ScheduleDetailViewModel scheduleDetailViewModel) {
        return new ViewModelProviderFactory<>(scheduleDetailViewModel);
    }

}
