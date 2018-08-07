package vn.homecredit.hcvn.ui.contract.masterContractSuccess;

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
import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.data.model.OtpPassParam;
import vn.homecredit.hcvn.databinding.ActivityMasterContractSignBinding;
import vn.homecredit.hcvn.databinding.ActivityMasterContractSuccessBinding;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.ui.contract.masterContractSign.MasterContractSignViewModel;
import vn.homecredit.hcvn.ui.home.HomeActivity;
import vn.homecredit.hcvn.ui.map.PayMapActivity;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MasterContractSuccessActivity extends BaseActivity<ActivityMasterContractSuccessBinding, MasterContractSuccessViewModel> {
    public static final String BUNDLE_OTPPARAM = "BUNDLE_OTPPARAM";

    public static void start(Context context, OtpPassParam otpPassParam) {
        Intent intent = new Intent(context, MasterContractSuccessActivity.class);
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
        return R.layout.activity_master_contract_success;
    }

    @Override
    public MasterContractSuccessViewModel getViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(MasterContractSuccessViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().hasExtra(BUNDLE_OTPPARAM)) {
            OtpPassParam otpPassParam = Parcels.unwrap(getIntent().getParcelableExtra(BUNDLE_OTPPARAM));
            getViewModel().setOtpParam(otpPassParam);
        }

        getViewModel().getModelDisbursementLocation().observe(this, aBoolean -> {
            if (aBoolean != null && aBoolean == Boolean.TRUE) {
                showDisbursementLocation();
            }
        });

        getViewModel().getModelDone().observe(this, aBoolean -> {
            if (aBoolean != null && aBoolean == Boolean.TRUE) {
                showHome();
            }
        });
    }

    private void showHome() {
        Intent intent = HomeActivity.newIntent(this);
        intent.addFlags(FLAG_ACTIVITY_CLEAR_TASK | FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void showDisbursementLocation() {
        PayMapActivity.start(this, PayMapActivity.DISBURSEMENT_MODE);
    }


}
