package vn.homecredit.hcvn.ui.settings.changepass;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.databinding.ActivityChangepasswordBinding;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.ui.login.LoginActivity;
import vn.homecredit.hcvn.helpers.DialogContractsHelp;

public class ChangePassActivity extends BaseActivity<ActivityChangepasswordBinding, ChangePassViewModel> {

    public static void start(Context context) {
        Intent intent = new Intent(context, ChangePassActivity.class);
        context.startActivity(intent);
    }

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_changepassword;
    }

    @Override
    public ChangePassViewModel getViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(ChangePassViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewModel().getModelDialogPasswordHelp().observe(this, phoneSupport -> {
            if (phoneSupport != null) {
                showDialogPasswordHelp(phoneSupport);
            }
        });
        getViewModel().getModelChangePassSuccess().observe(this, success -> {
            if (success) {
                showDialogChangePassSuccess();
            }
        });
        getViewDataBinding().toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void showDialogChangePassSuccess() {
        DialogChangePassSuccess.showDialog(getSupportFragmentManager(), () -> showLogin());
    }

    private void showLogin() {
        Intent intent = LoginActivity.newIntent(this);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void showDialogPasswordHelp(String phoneSupport) {
        String title = getResources().getString(R.string.dialog_password_help_title);
        String content = getResources().getString(R.string.dialog_password_help_content, phoneSupport);
        DialogContractsHelp.showDialog(getSupportFragmentManager(), title, content, phoneSupport);
    }
}
