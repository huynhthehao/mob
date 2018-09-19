package vn.homecredit.hcvn.ui.signup;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.OtpPassParam;
import vn.homecredit.hcvn.databinding.ActivitySignupBinding;
import vn.homecredit.hcvn.helpers.DialogContractsHelp;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.ui.otp.OtpActivity;

public class SignUpActivity extends BaseActivity<ActivitySignupBinding, SignUpViewModel> {

    @Inject ViewModelProvider.Factory viewModelFactory;

    public static void start(Context context) {
        Intent intent = new Intent(context, SignUpActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_signup;
    }

    @Override
    public SignUpViewModel getViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(SignUpViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewModel().getModelOtpPassParam().observe(this, otpPassParam -> {
            if (otpPassParam != null) {
                showOTPPage(otpPassParam);
            }
        });

        getViewModel().getModelDialogContractshelp().observe(this, phoneSupport -> {
            if (phoneSupport != null) {
                showDialogContactHelp(phoneSupport);
            }
        });
        getViewDataBinding().toolbar.setNavigationOnClickListener(v -> finish());

    }

    private void showOTPPage(OtpPassParam otpPassParam) {
        OtpActivity.start(this, otpPassParam);
    }

    private void showDialogContactHelp(String phoneSupport) {
        String title = getResources().getString(R.string.dialog_contact_help_title);
        String content = getResources().getString(R.string.dialog_contact_help_content, phoneSupport);
        DialogContractsHelp.showDialog(getSupportFragmentManager(), title, content, phoneSupport);
    }
}
