package vn.homecredit.hcvn.ui.contract.detail;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.inject.Inject;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.databinding.ActivityContractDetailBinding;
import vn.homecredit.hcvn.ui.base.BaseActivity;

public class ContractDetailActivity extends BaseActivity<ActivityContractDetailBinding, ContractDetailViewModel> {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    public static void start(Context context) {
        Intent intent = new Intent(context, ContractDetailActivity.class);
        context.startActivity(intent);
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
    }
}
