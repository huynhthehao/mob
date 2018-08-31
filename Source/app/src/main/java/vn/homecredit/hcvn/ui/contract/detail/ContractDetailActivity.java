package vn.homecredit.hcvn.ui.contract.detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import org.parceler.Parcels;

import javax.inject.Inject;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.api.contract.HcContract;
import vn.homecredit.hcvn.databinding.ActivityContractDetailBinding;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.ui.contract.paymentHistory.PaymentHistoryActivity;
import vn.homecredit.hcvn.ui.contract.scheduleDetail.ScheduleDetailActivity;
import vn.homecredit.hcvn.ui.map.PayMapActivity;
import vn.homecredit.hcvn.ui.momo.paymentMomo.PaymentMomoActivity;

import static java.lang.Boolean.TRUE;

public class ContractDetailActivity extends BaseActivity<ActivityContractDetailBinding, ContractDetailViewModel> {

    public static final String BUNDLE_CONTRACT_PARAM = "BUNDLE_CONTRACT_PARAM";

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    public static void start(Context context, HcContract hcContract) {
        Intent intent = getNewIntent(context, hcContract);
        context.startActivity(intent);
    }

    public static Intent getNewIntent(Context context, HcContract hcContract) {
        Intent newInstance = new Intent(context, ContractDetailActivity.class);
        if (hcContract != null) {
            Bundle currentBundle = new Bundle();
            currentBundle.putParcelable(BUNDLE_CONTRACT_PARAM, Parcels.wrap(hcContract));
            newInstance.putExtras(currentBundle);
        }
        return newInstance;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_contract_detail;
    }

    @Override
    public ContractDetailViewModel getViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(ContractDetailViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewModel().init();
        getViewDataBinding().toolbar.setNavigationOnClickListener(v -> finish());
        if (getIntent().hasExtra(BUNDLE_CONTRACT_PARAM)) {
            HcContract hcContract = Parcels.unwrap(getIntent().getParcelableExtra(BUNDLE_CONTRACT_PARAM));
            getViewModel().setHcContract(hcContract);
            getViewDataBinding().setHcContractModel(new ContractDetailViewModel.ContractViewModel(hcContract));
        }
        getViewModel().getModelPaymentSchedule().observe(this, contractNumber -> {
            if (!TextUtils.isEmpty(contractNumber)) {
                showPaymentSchedule(contractNumber);
            }
        });
        getViewModel().getModelPaymentHistory().observe(this, contractNumber -> {
            if (!TextUtils.isEmpty(contractNumber)) {
                showPaymentHistory(contractNumber);
            }
        });
        getViewModel().getModelLocation().observe(this, aBoolean -> {
            if (aBoolean != null && aBoolean == TRUE) {
                showMap();
            }
        });
        getViewModel().getModelMomo().observe(this, aBoolean -> {
            if (aBoolean != null && aBoolean == TRUE) {
                showMomoDetail();
            }
        });
    }

    private void showMomoDetail() {
        PaymentMomoActivity.start(this, getViewModel().getHcContract());
    }

    private void showPaymentHistory(String contractNumber) {
        PaymentHistoryActivity.start(this, contractNumber);
    }

    private void showPaymentSchedule(String contractNumber) {
        ScheduleDetailActivity.start(this, contractNumber);
    }

    private void showMap() {
        PayMapActivity.start(this, PayMapActivity.PAYMENT_MODE);
    }
}
