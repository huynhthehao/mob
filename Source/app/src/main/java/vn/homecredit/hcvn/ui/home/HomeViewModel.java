package vn.homecredit.hcvn.ui.home;

import android.content.Context;

import javax.inject.Inject;

import vn.homecredit.hcvn.data.DataManager;
import vn.homecredit.hcvn.service.ProfileService;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class HomeViewModel extends BaseViewModel {

    private final ProfileService mProfileService;
    private String mUserName;

    @Inject
    public HomeViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, ProfileService profileService) {
        super(dataManager, schedulerProvider);
        mProfileService = profileService;
        mUserName = mProfileService.GetProfile().getFullName();
    }

    public String getUserName() {
        return mUserName;
    }
}
