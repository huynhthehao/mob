package vn.homecredit.hcvn.ui.momo.paymentMomo;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import javax.inject.Inject;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.data.model.momo.RePaymentData;
import vn.homecredit.hcvn.ui.momo.NumberFormatTextWatcher;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.api.contract.HcContract;
import vn.homecredit.hcvn.databinding.ActivityPaymentMomoBinding;
import vn.homecredit.hcvn.ui.base.BaseActivity;

import static java.lang.Boolean.TRUE;

public class PaymentMomoActivity
        extends BaseActivity<ActivityPaymentMomoBinding, PaymentMomoViewModel>
        implements NumberFormatTextWatcher.StringAsNumberChangeListener {

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
        if (!getIntent().hasExtra(BUNDLE_CONTRACT_PARAM)) return;
        HcContract hcContract = Parcels.unwrap(getIntent().getParcelableExtra(BUNDLE_CONTRACT_PARAM));
        getViewModel().setContract(hcContract);
        getViewModel().getRepayment();
        getViewModel().getModelPaymentViaMomo().observe(this, aBoolean -> {
            if (aBoolean != null && aBoolean == TRUE) {
                paymentViaMomo();
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
    }

    private void paymentViaMomo() {
        Toast.makeText(this, "pay via momo", Toast.LENGTH_SHORT).show();
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
}
