package vn.homecredit.hcvn.ui.contract.summaryContract;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.support.annotation.Nullable;

import org.parceler.Parcels;

import javax.inject.Inject;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.data.model.api.contract.HcContract;
import vn.homecredit.hcvn.data.model.api.contract.MasterContract;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.databinding.ActivityContractSummaryBinding;
import vn.homecredit.hcvn.ui.contract.masterContractDoc.MasterContractDocActivity;
import vn.homecredit.hcvn.ui.custom.FingerprintAuthenticationDialogFragment;

import static java.lang.Boolean.TRUE;

public class SummaryContractActivity extends BaseActivity< ActivityContractSummaryBinding, SummaryContractViewModel>{

    private static final String BUNDLE_CONTRACT = "BUNDLE_CONTRACT";
    private HcContract hcContract;
    @Inject
    ViewModelProvider.Factory viewmodelFactory;

    public static void start(Context context, HcContract hcContract) {
        Intent intent = new Intent(context, SummaryContractActivity.class);
        intent.putExtra(BUNDLE_CONTRACT, Parcels.wrap(hcContract));
        context.startActivity(intent);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_contract_summary;
    }

    @Override
    public SummaryContractViewModel getViewModel() {
        return ViewModelProviders.of(this, viewmodelFactory).get(SummaryContractViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().hasExtra(BUNDLE_CONTRACT)) {
            hcContract = Parcels.unwrap(getIntent().getParcelableExtra(BUNDLE_CONTRACT));
            getViewModel().setContractsId(hcContract.getContractNumber());
        }

        getViewModel().getModelViewDoc().observe(this, aBoolean -> {
            if (aBoolean != null && aBoolean == TRUE) {
                showMasterContractDocActivity(getViewModel().getMasterContract());
            }
        });
        getViewModel().getModelShowFingerprintDialog().observe(this, isShowFingerprint -> {
            if (isShowFingerprint != null && isShowFingerprint == TRUE) {
                showFingerDialogLogin();
            }
        });
        getViewModel().getModelShowPasswordDialog().observe(this, isShowPasswordDialog -> {
            if (isShowPasswordDialog != null && isShowPasswordDialog == TRUE) {
                showPasswordDialogLogin();
            }
        });

    }

    private void showFingerDialogLogin() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            FingerprintManager fingerprintManager = getSystemService(FingerprintManager.class);
            FingerprintAuthenticationDialogFragment fragment = new FingerprintAuthenticationDialogFragment(fingerprintManager);
            fragment.setOnValidateSuccess(this::onValidatedSuccess);
            fragment.setOnDismiss(this::onFingerprintDialogDismissed);
            fragment.setCryptoObject(null);
            fragment.show(getFragmentManager(), "FingerPrintDialog");
        }

    }

    private void onFingerprintDialogDismissed() {

    }

    private void onValidatedSuccess() {
        getViewModel().autoLogin();
    }

    private void showPasswordDialogLogin() {

    }

    private void showMasterContractDocActivity(MasterContract masterContract) {
        MasterContractDocActivity.start(this, masterContract);
    }
}
