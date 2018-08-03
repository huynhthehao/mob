package vn.homecredit.hcvn.ui.contract.scheduleDetail;

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
import vn.homecredit.hcvn.databinding.ActivityScheduleDetailBinding;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.ui.custom.AppDataView;
import vn.homecredit.hcvn.ui.custom.AppDataViewState;

public class ScheduleDetailActivity extends BaseActivity<ActivityScheduleDetailBinding, ScheduleDetailViewModel> {

    public static final String BUNDLE_CONTRACT_NUMBER = "BUNDLE_CONTRACT_NUMBER";

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    AppDataView appDataView;
    InstalmentAdapter instalmentAdapter;
    RecyclerView rvNotifications;

    public static void start(Context context, String contractNumber) {
        Intent intent = getNewIntent(context, contractNumber);
        context.startActivity(intent);
    }

    public static Intent getNewIntent(Context context, String contractNumber) {
        Intent newInstance = new Intent(context, ScheduleDetailActivity.class);
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
        return R.layout.activity_schedule_detail;
    }

    @Override
    public ScheduleDetailViewModel getViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(ScheduleDetailViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewModel().init();
        getViewDataBinding().toolbar.setNavigationOnClickListener(v -> finish());

        if (getIntent().hasExtra(BUNDLE_CONTRACT_NUMBER)) {
            String contractNumber = getIntent().getStringExtra(BUNDLE_CONTRACT_NUMBER);

            appDataView = getViewDataBinding().advNotifications;
            rvNotifications = new RecyclerView(this);
            initAdapter();
            appDataView.initContentView(rvNotifications, 0, 0, null, () -> getViewModel().pullToRefreshCollection());
            getViewModel().init();
            getViewModel().setContractNumber(contractNumber);
            getViewModel().getDataCollection().observe(this, notificationModels -> instalmentAdapter.swapData(notificationModels));
            getViewModel().getModelIsRefreshing().observe(this, isRefreshing -> {
                if (!isRefreshing) appDataView.updateViewState(AppDataViewState.HIDE_RELOADING);
            });
        }

    }

    private void initAdapter() {
        instalmentAdapter = new InstalmentAdapter(this, model -> {

        });
        rvNotifications.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvNotifications.setAdapter(instalmentAdapter);
    }
}
