package vn.homecredit.hcvn.ui.home;

import android.content.Context;

import vn.homecredit.hcvn.data.DataManager;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class HomeViewModel extends BaseViewModel {

    public HomeViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

}
