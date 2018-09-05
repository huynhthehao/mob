package vn.homecredit.hcvn.ui.payment.summary;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import org.parceler.Parcels;

import javax.inject.Inject;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.databinding.ActivityPaymentSummaryBinding;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.ui.home.HomeActivity;
import vn.homecredit.hcvn.ui.payment.summary.model.PaymentSummaryModel;

public class PaymentSummaryActivity extends BaseActivity<ActivityPaymentSummaryBinding, PaymentSummaryViewModel> {
    public static final String PAYMENT_SUMMARY_DATA = "PAYMENT_SUMMARY_DATA";

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    public static void start(Context context, PaymentSummaryModel model) {
        Intent intent = new Intent(context, PaymentSummaryActivity.class);
        intent.putExtra(PAYMENT_SUMMARY_DATA, Parcels.wrap(model));
        context.startActivity(intent);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_payment_summary;
    }

    @Override
    public PaymentSummaryViewModel getViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(PaymentSummaryViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent.hasExtra(PAYMENT_SUMMARY_DATA)) {
            PaymentSummaryModel model = Parcels.unwrap(intent.getParcelableExtra(PAYMENT_SUMMARY_DATA));
            getViewDataBinding().setSummaryModel(model);
        }
        getViewModel().getDoneClickEvent().observe(this, mBoolean -> {
            if (mBoolean) {
                startHomeActivity();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startHomeActivity();
    }

    private void startHomeActivity() {
        Intent intentHome = new Intent(this, HomeActivity.class);
        intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intentHome);
        finish();
    }
}
