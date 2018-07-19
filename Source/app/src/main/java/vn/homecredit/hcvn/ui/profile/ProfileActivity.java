package vn.homecredit.hcvn.ui.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;

import javax.inject.Inject;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.databinding.ActivityProfileBinding;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.utils.AppUtils;

public class ProfileActivity extends BaseActivity<ActivityProfileBinding, ProfileViewModel> {
    @Inject
    ProfileViewModel profileViewModel;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_profile;
    }

    @Override
    public ProfileViewModel getViewModel() {
        return profileViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewDataBinding().tvProfileUpdateMessage.setMovementMethod(LinkMovementMethod.getInstance());
        profileViewModel.init();
        profileViewModel.getModelCustomerServiceCall().observe(this, customerServicePhone -> {
            if (!TextUtils.isEmpty(customerServicePhone)) {
                AppUtils.openDeviceCallDialog(this, customerServicePhone);
            }
        });
        profileViewModel.getModelProfileData().observe(this, profileModel -> {
            if (profileModel != null) {
                getViewDataBinding().setProfileModel(profileModel);
            }
        });
    }
}
