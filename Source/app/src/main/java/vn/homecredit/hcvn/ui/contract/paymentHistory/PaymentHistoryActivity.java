package vn.homecredit.hcvn.ui.contract.paymentHistory;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import javax.inject.Inject;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.databinding.ActivityPaymentHistoryBinding;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.ui.custom.AppDataView;
import vn.homecredit.hcvn.ui.custom.AppDataViewState;

public class PaymentHistoryActivity extends BaseActivity<ActivityPaymentHistoryBinding, PaymentHistoryViewModel> {

    public static final String BUNDLE_CONTRACT_NUMBER = "BUNDLE_CONTRACT_NUMBER";

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    AppDataView appDataView;
    PaymentHistoryAdapter paymentAdapter;
    RecyclerView recyclerView;

    public static void start(Context context, String contractNumber) {
        Intent intent = getNewIntent(context, contractNumber);
        context.startActivity(intent);
    }

    public static Intent getNewIntent(Context context, String contractNumber) {
        Intent newInstance = new Intent(context, PaymentHistoryActivity.class);
        if (contractNumber != null) {
            Bundle currentBundle = new Bundle();
            currentBundle.putString(BUNDLE_CONTRACT_NUMBER, contractNumber);
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
        return R.layout.activity_payment_history;
    }

    @Override
    public PaymentHistoryViewModel getViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(PaymentHistoryViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewDataBinding().toolbar.setNavigationOnClickListener(v -> finish());

        if (getIntent().hasExtra(BUNDLE_CONTRACT_NUMBER)) {
            String contractNumber = getIntent().getStringExtra(BUNDLE_CONTRACT_NUMBER);

            appDataView = getViewDataBinding().appDataView;
            recyclerView = new RecyclerView(this);
            initAdapter();
            appDataView.initContentView(recyclerView, 0, R.string.empty_instalment, null,
                    () -> getViewModel().pullToRefreshCollection());
            getViewModel().getDataCollection().observe(this, listData -> paymentAdapter.swapData(listData));
            getViewModel().getModelIsRefreshing().observe(this, isRefreshing -> {
                appDataView.updateViewState(!isRefreshing ? AppDataViewState.HIDE_RELOADING : AppDataViewState.SHOW_RELOADING);
            });
            appDataView.updateViewState(AppDataViewState.SHOW_RELOADING);
            getViewModel().initData(contractNumber);
        }

    }

    private void initAdapter() {
        paymentAdapter = new PaymentHistoryAdapter(this, model -> {

        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(paymentAdapter);
    }
}
