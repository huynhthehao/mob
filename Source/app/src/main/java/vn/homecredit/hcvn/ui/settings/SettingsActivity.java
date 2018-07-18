package vn.homecredit.hcvn.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.databinding.ActivitySettingsBinding;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.ui.login.LoginActivity;
import vn.homecredit.hcvn.utils.AppUtils;

public class SettingsActivity extends BaseActivity<ActivitySettingsBinding, SettingsViewModel> {
    @Inject
    SettingsViewModel settingsViewModel;

    public static Intent newIntent(Context context) {
        return new Intent(context, SettingsActivity.class);
    }

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsViewModel.init();
        settingsViewModel.getModelBack().observe(this, aBoolean -> {
            if (aBoolean != null && aBoolean == true) {
                finish();
            }
        });
        settingsViewModel.getModelAppRating().observe(this, aBoolean -> {
            if (aBoolean != null && aBoolean == true) {
                AppUtils.openPlayStoreForApp(this);
            }
        });
        settingsViewModel.getModelLanguage().observe(this, languageCode -> {
            if (languageCode != null && !languageCode.equals("")) {
                //TODO change language with this language code
            }
        });
    }
}
