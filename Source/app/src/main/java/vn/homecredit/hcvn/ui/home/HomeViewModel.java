package vn.homecredit.hcvn.ui.home;

import javax.inject.Inject;

import vn.homecredit.hcvn.data.DataManager;
import vn.homecredit.hcvn.service.ProfileService;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class HomeViewModel extends BaseViewModel {

    private final ProfileService mProfileService;
    private String mUserName;

    @Inject
    public HomeViewModel(SchedulerProvider schedulerProvider, ProfileService profileService) {
        super(schedulerProvider);
        mProfileService = profileService;
        if (mProfileService != null
                && mProfileService.getProfile() != null
                && mProfileService.getProfile().getFullName() != null) {
            mUserName = mProfileService.getProfile().getFullName();
        }else {
            mUserName = "";
        }
    }

    public String getUserName() {
        return mUserName;
    }
}
