package vn.homecredit.hcvn.ui.setpassword;

import android.arch.lifecycle.Observer;
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
import vn.homecredit.hcvn.data.model.OtpPassParam;
import vn.homecredit.hcvn.databinding.ActivitySetpasswordBinding;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.ui.home.HomeActivity;

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
                HomeActivity.start(SetPasswordActivity.this, true );
            }
        });

    }
}
