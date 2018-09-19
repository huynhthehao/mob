package vn.homecredit.hcvn.ui.setpassword;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import org.parceler.Parcels;

import javax.inject.Inject;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.enums.FirstComeFlow;
import vn.homecredit.hcvn.data.model.OtpPassParam;
import vn.homecredit.hcvn.databinding.ActivitySetpasswordBinding;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.ui.home.HomeActivity;
import vn.homecredit.hcvn.ui.settings.changepass.DialogChangePassSuccess;
import vn.homecredit.hcvn.helpers.DialogContractsHelp;
import vn.homecredit.hcvn.ui.welcome.WelcomeActivity;

public class SetPasswordActivity extends BaseActivity<ActivitySetpasswordBinding, SetPasswordViewModel> {

    private static final String BUNDLE_OTP_PARAM = "BUNDLE_OTP_PARAM";
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    public static void start(Context context, OtpPassParam otpParam) {
        Intent intent = new Intent(context, SetPasswordActivity.class);
        intent.putExtra(BUNDLE_OTP_PARAM, Parcels.wrap(otpParam));
        context.startActivity(intent);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setpassword;
    }

    @Override
    public SetPasswordViewModel getViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(SetPasswordViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().hasExtra(BUNDLE_OTP_PARAM)) {
            OtpPassParam otpPassParam = Parcels.unwrap(getIntent().getParcelableExtra(BUNDLE_OTP_PARAM));
            getViewModel().setOtpParam(otpPassParam);
        }

        getViewModel().getModelSignIn().observe(this, aBoolean -> {
            if (aBoolean != null && aBoolean == true) {
                HomeActivity.start(SetPasswordActivity.this, true, FirstComeFlow.AFTER_SIGNUP);
            }
        });

        getViewModel().getModelForgetPasswordSuccess().observe(this, aBoolean -> {
            if (aBoolean != null && aBoolean == true) {
                DialogChangePassSuccess.showDialog(getSupportFragmentManager(), () -> {
                    Intent intent = WelcomeActivity.newIntent(SetPasswordActivity.this);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                });
            }
        });

        getViewModel().getModelDialogPasswordHelp().observe(this, phoneSupport -> {
            if (phoneSupport != null) {
                showDialogPasswordHelp(phoneSupport);
            }
        });
        getViewModel().getModelErrorMessage().observe(this, resIdMessage -> {
            if (resIdMessage != null && resIdMessage != 0) {
                showMessage(getResources().getString(resIdMessage));
            }
        });
        getViewDataBinding().toolbar.setNavigationOnClickListener(v -> finish());

    }
    private void showDialogPasswordHelp(String phoneSupport) {
        String title = getResources().getString(R.string.dialog_password_help_title);
        String content = getResources().getString(R.string.dialog_password_help_content, phoneSupport);
        DialogContractsHelp.showDialog(getSupportFragmentManager(), title, content, phoneSupport);
    }
}
