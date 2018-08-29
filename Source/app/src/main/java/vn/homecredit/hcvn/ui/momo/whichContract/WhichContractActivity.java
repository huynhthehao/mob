package vn.homecredit.hcvn.ui.momo.whichContract;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.androidnetworking.utils.Utils;

import javax.inject.Inject;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.databinding.ActivityWhichContractBinding;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.ui.momo.myContract.MyContractActivity;

import static java.lang.Boolean.TRUE;

public class WhichContractActivity extends BaseActivity<ActivityWhichContractBinding, WhichContractViewModel> {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    public static void start(Context context) {
        Intent intent = new Intent(context, WhichContractActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_which_contract;
    }

    @Override
    public WhichContractViewModel getViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(WhichContractViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getViewModel().getModelMyContract().observe(this, aBoolean -> {
            if (aBoolean != null && aBoolean == TRUE) {
                showMyContracts();
            }
        });

        getViewModel().getModelPayForOther().observe(this, aBoolean -> {
            if (aBoolean != null && aBoolean == TRUE) {
                showPayForOther();
            }
        });
        getViewDataBinding().toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void showMyContracts() {
        MyContractActivity.start(this);
    }

    private void showPayForOther() {

    }

}
