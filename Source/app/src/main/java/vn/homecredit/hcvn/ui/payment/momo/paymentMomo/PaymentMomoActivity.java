package vn.homecredit.hcvn.ui.payment.momo.paymentMomo;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.Map;

import javax.inject.Inject;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.BuildConfig;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.api.contract.HcContract;
import vn.homecredit.hcvn.data.model.momo.RePaymentData;
import vn.homecredit.hcvn.databinding.ActivityPaymentMomoBinding;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.ui.payment.PaymentMomoEventValueBuilder;
import vn.homecredit.hcvn.ui.payment.model.PaymentMomoRequestModel;
import vn.homecredit.hcvn.ui.payment.summary.PaymentSummaryActivity;
import vn.momo.momo_partner.AppMoMoLib;

public class PaymentMomoActivity
        extends BaseActivity<ActivityPaymentMomoBinding, PaymentMomoViewModel>
        implements NumberFormatTextWatcher.StringAsNumberChangeListener {

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
        if (BuildConfig.FLAVOR == "product") {
            AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.PRODUCTION);
        } else {
            AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT);
        }
        getViewDataBinding().toolbar.setNavigationOnClickListener(v -> onBackPressed());
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
        getViewModel().getModelRequestPaymentViaMomo().observe(this, paymentMomoRequestModel -> {

            sendEvent(R.string.ga_event_momo_category, R.string.ga_event_momo_action, R.string.ga_event_momo_label_pay_via_momo);
            if (paymentMomoRequestModel != null) {
                requestPaymentViaMomo(paymentMomoRequestModel);
            }
        });
        EditText partial = getViewDataBinding().etPartialAmount;
        partial.addTextChangedListener(new NumberFormatTextWatcher(partial, this));
        getViewDataBinding().toolbar.setNavigationOnClickListener(v -> onBackPressed());
        getViewDataBinding().setPaymentChange((ignored, isChecked) -> {
            if (isChecked) {
                partial.setText("0");
                partial.setSelection(1);
            }
        });

        getViewModel().getPaymentMomoSuccess().observe(this, paymentSummary -> {
            PaymentSummaryActivity.start(this, paymentSummary);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getViewModel().onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onChange(@NotNull String value) {
        int amount = Integer.parseInt(value);
        RePaymentData data = getViewModel().getRePaymentData().get();
        if (data != null) {
            data.setAmount(amount);
        }

        int fullAmount = getViewModel().getFullAmount().get();
        if (amount > fullAmount) {
            EditText partial = getViewDataBinding().etPartialAmount;
            partial.setText(String.valueOf(fullAmount));
            partial.setSelection(partial.getText().length());
            return;
        }
        getViewModel().getRePaymentData().notifyChange();
    }

    private void requestPaymentViaMomo(PaymentMomoRequestModel paymentMomoRequestModel) {
        try {
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
        }catch (Exception ex){
            showMessage(R.string.paymomo_unknown_exception);
        }
    }
}
