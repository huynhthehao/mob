package vn.homecredit.hcvn.ui.contract.masterContractSign;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import org.parceler.Parcels;

import javax.inject.Inject;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.OtpPassParam;
import vn.homecredit.hcvn.databinding.ActivityMasterContractSignBinding;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.ui.contract.masterContractDoc.MasterContractDocViewModel;
import vn.homecredit.hcvn.ui.contract.masterContractSuccess.MasterContractSuccessActivity;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static java.lang.Boolean.TRUE;

public class MasterContractSignActivity extends BaseActivity<ActivityMasterContractSignBinding, MasterContractSignViewModel> {
    public static final String BUNDLE_OTPPARAM = "BUNDLE_OTPPARAM";

    public static void start(Context context, OtpPassParam otpPassParam) {
        Intent intent = new Intent(context, MasterContractSignActivity.class);
        intent.putExtra(BUNDLE_OTPPARAM, Parcels.wrap(otpPassParam));
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
        return R.layout.activity_master_contract_sign;
    }

    @Override
    public MasterContractSignViewModel getViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(MasterContractSignViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().hasExtra(BUNDLE_OTPPARAM)) {
            OtpPassParam otpPassParam = Parcels.unwrap(getIntent().getParcelableExtra(BUNDLE_OTPPARAM));
            getViewModel().setOtpParam(otpPassParam);
        }
        getViewModel().getModelSuccess().observe(this, aBoolean -> {
            if (aBoolean == null && aBoolean == TRUE) {
                showSuccessPage();
            }
        });
    }

    private void showSuccessPage() {
        MasterContractSuccessActivity.start(this, getViewModel().getOtpParam());
    }
}
