package vn.homecredit.hcvn.ui.momo.paymentMomo;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import org.parceler.Parcels;

import java.util.Map;

import javax.inject.Inject;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.BuildConfig;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.api.contract.HcContract;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.databinding.ActivityPaymentMomoBinding;
import vn.homecredit.hcvn.ui.momo.MomoForTestActivity;
import vn.homecredit.hcvn.ui.payment.PaymentMomoEventValueBuilder;
import vn.homecredit.hcvn.ui.payment.model.PaymentMomoRequestModel;
import vn.homecredit.hcvn.ui.payment.summary.PaymentSummaryActivity;
import vn.homecredit.hcvn.ui.payment.summary.model.PaymentSummaryModel;
import vn.momo.momo_partner.AppMoMoLib;

import static java.lang.Boolean.TRUE;

public class PaymentMomoActivity extends BaseActivity<ActivityPaymentMomoBinding, PaymentMomoViewModel> {

    public static final String BUNDLE_CONTRACT_PARAM = "BUNDLE_CONTRACT_PARAM";


    @Inject
    ViewModelProvider.Factory viewModelFactory;

    public static void start(Context context, HcContract hcContract) {
        Intent intent = new Intent(context, PaymentMomoActivity.class);
        intent.putExtra(BUNDLE_CONTRACT_PARAM, Parcels.wrap(hcContract));
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
        if (BuildConfig.FLAVOR == "product") {
            AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.PRODUCTION);
        } else {
            AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT);
        }
        if (!getIntent().hasExtra(BUNDLE_CONTRACT_PARAM)) return;
        HcContract hcContract = Parcels.unwrap(getIntent().getParcelableExtra(BUNDLE_CONTRACT_PARAM));
        getViewModel().setContract(hcContract);
        getViewModel().getRepayment();

        getViewModel().getModelRequestPaymentViaMomo().observe(this, paymentMomoRequestModel -> {
            if (paymentMomoRequestModel != null) {
                requestPaymentViaMomo(paymentMomoRequestModel);
            }
        });
        getViewDataBinding().toolbar.setNavigationOnClickListener(v -> onBackPressed());

        getViewModel().getPaymentMomoSuccess().observe(this, paymentSummary -> {
            PaymentSummaryActivity.start(this, paymentSummary);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getViewModel().onActivityResult(requestCode, resultCode, data);
    }

    private void requestPaymentViaMomo(PaymentMomoRequestModel paymentMomoRequestModel) {
        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT);
        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN);
        Map<String, Object> eventValue = new PaymentMomoEventValueBuilder(this)
                .setAmount(String.valueOf(paymentMomoRequestModel.getRePaymentData().getAmount()))
                .setContractNumber(paymentMomoRequestModel.getRePaymentData().getContractNumber())
                .setMerchantCode(paymentMomoRequestModel.getMerchantCode())
                .setMerchantName(paymentMomoRequestModel.getMerchantName())
                .setLanguageCode(paymentMomoRequestModel.getLanguageCode())
                .create();
        AppMoMoLib.getInstance().requestMoMoCallBack(this, eventValue);
    }
}
