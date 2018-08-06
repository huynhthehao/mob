package vn.homecredit.hcvn.ui.contract.statement.statementdetails;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import org.parceler.Parcel;
import org.parceler.Parcels;

import javax.inject.Inject;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.databinding.ActivityStatementDetailsBinding;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.ui.contract.statement.model.StatementModel;
import vn.homecredit.hcvn.ui.custom.SimpleRecyclerViewFragment;

public class StatementDetailsActivity extends BaseActivity<ActivityStatementDetailsBinding, StatementDetailsViewModel> {
    public static final String CONTRACT_ID = "contract_id";
    public static final String STATEMENT_OBJECT = "STATEMENT_OBJECT";
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    StatementModel statementModel;
    String contractId = "";
    private SimpleRecyclerViewFragment fragmentImages;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_statement_details;
    }

    @Override
    public StatementDetailsViewModel getViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(StatementDetailsViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        if (statementModel == null || TextUtils.isEmpty(contractId)) {
            return;
        }
        fragmentImages = (SimpleRecyclerViewFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentImages);
        getViewDataBinding().toolbar.setNavigationOnClickListener(v -> onBackPressed());
        getViewModel().init(contractId, statementModel);
        getViewModel().getDataStatementDetails().observe(this, statementImages -> {
            fragmentImages.updateImages(statementImages);
        });
    }

    public void getData() {
        Intent intent = getIntent();
        if (intent.hasExtra(STATEMENT_OBJECT)) {
            statementModel = Parcels.unwrap(intent.getParcelableExtra(STATEMENT_OBJECT));
        }
        if (intent.hasExtra(CONTRACT_ID)) {
            contractId = intent.getStringExtra(CONTRACT_ID);
        }
    }
}
