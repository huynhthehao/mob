package vn.homecredit.hcvn.ui.contract.statement;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import javax.inject.Inject;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.databinding.ActivityEStatementBinding;
import vn.homecredit.hcvn.helpers.prefs.PreferencesHelper;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.ui.custom.AppDataView;
import vn.homecredit.hcvn.ui.custom.AppDataViewState;

public class StatementActivity extends BaseActivity<ActivityEStatementBinding, StatementViewModel> {
    public static final String CONTRACT_ID = "contract_id";
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    PreferencesHelper preferencesHelper;

    AppDataView appDataView;
    StatementAdapter statementAdapter;
    RecyclerView rvStatements;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_e_statement;
    }

    @Override
    public StatementViewModel getViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(StatementViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String contractId = getData();
        appDataView = getViewDataBinding().advStatements;
        rvStatements = new RecyclerView(this);
        initAdapter();
        getViewDataBinding().toolbar.setNavigationOnClickListener(v -> finish());
        appDataView.initContentView(rvStatements, 0, 0, null, () -> getViewModel().pullToRefresh());
        getViewModel().init(contractId);
        getViewModel().getModelRefreshing().observe(this, mBoolean -> {
            if (mBoolean)
                appDataView.updateViewState(AppDataViewState.SHOW_RELOADING);
            else
                appDataView.updateViewState(AppDataViewState.HIDE_RELOADING);
        });
        getViewModel().getDataStatements().observe(this, statementModels -> {
            statementAdapter.swapData(statementModels);
        });
    }

    private String getData() {
        Intent intent = getIntent();
        if (!intent.hasExtra(CONTRACT_ID)) {
            return "";
        }
        return intent.getStringExtra(CONTRACT_ID);
    }

    private void initAdapter() {
        statementAdapter = new StatementAdapter(model -> {
            //TODO
        }, preferencesHelper);
        rvStatements.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvStatements.setAdapter(statementAdapter);
    }
}
