package vn.homecredit.hcvn.ui.contract.summaryContract;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import org.parceler.Parcels;

import javax.inject.Inject;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.data.model.api.contract.HcContract;
import vn.homecredit.hcvn.data.model.api.contract.MasterContract;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.databinding.ActivityContractSummaryBinding;
import vn.homecredit.hcvn.ui.contract.masterContractDoc.MasterContractDocActivity;
import vn.homecredit.hcvn.ui.custom.ESigningLoginDialogFragment;
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
        getViewDataBinding().toolbar.setNavigationOnClickListener(v -> onBackPressed());

        getViewModel().getModelViewDoc().observe(this, aBoolean -> {
            if (aBoolean != null && aBoolean == TRUE) {
                showMasterContractDocActivity(getViewModel().getMasterContract());
            }
        });
        getViewModel().getModelShowFingerprintDialog().observe(this, isShowFingerprint -> {
            if (isShowFingerprint != null && isShowFingerprint == TRUE) {
                showFingerDialogLogin(true);
            }
        });
        getViewModel().getModelShowPasswordDialog().observe(this, isShowPasswordDialog -> {
            if (isShowPasswordDialog != null && isShowPasswordDialog == TRUE) {
                showFingerDialogLogin(false);
            }
        });

    }

    private void showFingerDialogLogin(boolean userFingerprint) {
        ESigningLoginDialogFragment.showDialog(getSupportFragmentManager(), userFingerprint, new ESigningLoginDialogFragment.OnESingingListener() {
            @Override
            public void onFingerprint() {
                onValidatedSuccess();
            }

            @Override
            public void onPassword(String password) {
                getViewModel().doLoginWithPassword(password);
            }
        });

    }

    private void onValidatedSuccess() {
        getViewModel().autoLogin();
    }


    private void showMasterContractDocActivity(MasterContract masterContract) {
        MasterContractDocActivity.start(this, masterContract);
    }
}
