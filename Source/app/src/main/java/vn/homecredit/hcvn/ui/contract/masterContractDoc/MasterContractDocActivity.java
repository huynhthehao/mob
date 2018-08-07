package vn.homecredit.hcvn.ui.contract.masterContractDoc;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.OtpPassParam;
import vn.homecredit.hcvn.data.model.api.contract.MasterContract;
import vn.homecredit.hcvn.databinding.ActivityMasterContractDocBinding;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.ui.custom.SimpleRecyclerViewFragment;
import vn.homecredit.hcvn.ui.otp.OtpActivity;
import vn.homecredit.hcvn.utils.imageLoader.ImageLoader;

public class MasterContractDocActivity extends BaseActivity<ActivityMasterContractDocBinding, MasterContractDocViewModel> {
    private static final String BUNDLE_MASTER_CONTRACT = "BUNDLE_MASTER_CONTRACT";
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private SimpleRecyclerViewFragment fragmentMasterDoc;

    public static void start(Context context, MasterContract masterContract) {
        Intent intent = new Intent(context, MasterContractDocActivity.class);
        intent.putExtra(BUNDLE_MASTER_CONTRACT, Parcels.wrap(masterContract));
        context.startActivity(intent);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_master_contract_doc;
    }

    @Override
    public MasterContractDocViewModel getViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(MasterContractDocViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentMasterDoc = (SimpleRecyclerViewFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentMasterDoc);
        setupFooter();
        getViewDataBinding().toolbar.setNavigationOnClickListener(v -> onBackPressed());
        if (getIntent().hasExtra(BUNDLE_MASTER_CONTRACT)) {
            MasterContract masterContract = Parcels.unwrap(getIntent().getParcelableExtra(BUNDLE_MASTER_CONTRACT));
            getViewModel().setMasterContract(masterContract);
        }


        getViewModel().getModelMasterContractsDocs().observe(this, strings -> fragmentMasterDoc.updateImages(strings));

        getViewModel().getModelOtpPassParam().observe(this, otpPassParam -> {
            if (otpPassParam != null) {
                showOTPPage(otpPassParam);
            }
        });
    }

    private void showOTPPage(OtpPassParam otpPassParam) {
        OtpActivity.start(this, otpPassParam);
    }

    private void setupFooter() {
        View footer = LayoutInflater.from(this).inflate(R.layout.item_contract_detail_footer, null);
        Button btnAgree = footer.findViewById(R.id.btnAgree);
        SwitchCompat switchAgree = footer.findViewById(R.id.switchAgree);
        btnAgree.setOnClickListener(v -> {
            getViewModel().doApprove();
        });
        switchAgree.setOnCheckedChangeListener((buttonView, isChecked) -> {
            btnAgree.setEnabled(switchAgree.isChecked());
        });
        btnAgree.setEnabled(switchAgree.isChecked());
        fragmentMasterDoc.addFooterView(footer);
    }

}
