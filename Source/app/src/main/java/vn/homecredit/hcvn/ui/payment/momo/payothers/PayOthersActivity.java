/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/9/18 2:02 PM, by Admin
 */


package vn.homecredit.hcvn.ui.payment.momo.payothers;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.momo.RePaymentData;
import vn.homecredit.hcvn.databinding.ActivityPaymomoOthersBinding;
import vn.homecredit.hcvn.helpers.DialogContractsHelp;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.ui.payment.momo.paymentMomo.PaymentMomoActivity;


public class PayOthersActivity extends BaseActivity<ActivityPaymomoOthersBinding, PayOthersViewModel> implements PayOthersListener {

    @Inject
    ViewModelProvider.Factory viewModelFactory;


    public static void start(Context context) {
        Intent newInstance = new Intent(context, PayOthersActivity.class);
        context.startActivity(newInstance);
    }


    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_paymomo_others;
    }

    @Override
    public PayOthersViewModel getViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(PayOthersViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewModel().setListener(this);
        getViewDataBinding().toolbar.setNavigationOnClickListener(v -> finish());
    }


    @Override
    public void onNext(RePaymentData rePaymentData) {
        PaymentMomoActivity.start(this, rePaymentData);
    }


    @Override
    public void onContractHelp(String supportPhoneNumber) {
        showDialogContactHelp(supportPhoneNumber);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void showDialogContactHelp(String supportPhoneNumber) {
        String title = getResources().getString(R.string.dialog_contact_help_pay_other_title);
        String content = getResources().getString(R.string.dialog_contact_help_content, supportPhoneNumber);
        DialogContractsHelp.showDialog(getSupportFragmentManager(), title, content, supportPhoneNumber);
    }
}


