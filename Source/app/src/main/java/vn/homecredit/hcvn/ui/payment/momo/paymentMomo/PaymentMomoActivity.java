package vn.homecredit.hcvn.ui.payment.momo.paymentMomo;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import org.parceler.Parcels;

import javax.inject.Inject;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.api.contract.HcContract;
import vn.homecredit.hcvn.data.model.momo.RePaymentData;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.databinding.ActivityPaymentMomoBinding;
import vn.homecredit.hcvn.ui.momo.paymentMomo.PaymentMomoViewModel;

import static java.lang.Boolean.TRUE;

public class PaymentMomoActivity extends BaseActivity<ActivityPaymentMomoBinding, PaymentMomoViewModel> {

    public static final String BUNDLE_CONTRACT_PARAM = "BUNDLE_CONTRACT_PARAM";
    public static final String BUNDLE_REPAYMENT_PARAM = "BUNDLE_REPAYMENT_PARAM";


    @Inject
    ViewModelProvider.Factory viewModelFactory;

    public static void start(Context context, HcContract hcContract) {
        Intent intent = new Intent(context, PaymentMomoActivity.class);
        intent.putExtra(BUNDLE_CONTRACT_PARAM, Parcels.wrap(hcContract));
        context.startActivity(intent);
    }

    public static void start(Context context, RePaymentData rePaymentData) {
        Intent intent = new Intent(context, PaymentMomoActivity.class);
        intent.putExtra(BUNDLE_REPAYMENT_PARAM, Parcels.wrap(rePaymentData));
        context.startActivity(intent);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_payment_momo;
    }

    @Override
    public PaymentMomoViewModel getViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(PaymentMomoViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RePaymentData rePaymentData = null;
        HcContract hcContract = null;
        boolean hasIntentData = false;

        if (getIntent().hasExtra(BUNDLE_CONTRACT_PARAM)) {
            hcContract = Parcels.unwrap(getIntent().getParcelableExtra(BUNDLE_CONTRACT_PARAM));
            hasIntentData = true;
            getViewModel().initData(false, hcContract, rePaymentData);
        }

        if (getIntent().hasExtra(BUNDLE_REPAYMENT_PARAM)) {
            rePaymentData = Parcels.unwrap(getIntent().getParcelableExtra(BUNDLE_REPAYMENT_PARAM));
            hasIntentData = true;
            getViewModel().initData(true, hcContract, rePaymentData);
        }

        if(!hasIntentData)
            return;

        getViewModel().getModelPaymentViaMomo().observe(this, aBoolean -> {
            if (aBoolean != null && aBoolean == TRUE) {
                paymentViaMomo();
            }
        });
        getViewDataBinding().toolbar.setNavigationOnClickListener(v -> onBackPressed());

    }

    private void paymentViaMomo() {
        Toast.makeText(this, "pay via momo", Toast.LENGTH_SHORT).show();
    }
}
