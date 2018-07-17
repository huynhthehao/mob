package vn.homecredit.hcvn.ui.settings;

import javax.inject.Inject;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.databinding.ActivitySettingsBinding;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.BR;

public class SettingsActivity extends BaseActivity<ActivitySettingsBinding, SettingsViewModel> {
    @Inject
    SettingsViewModel settingsViewModel;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    public SettingsViewModel getViewModel() {
        return settingsViewModel;
    }
}
