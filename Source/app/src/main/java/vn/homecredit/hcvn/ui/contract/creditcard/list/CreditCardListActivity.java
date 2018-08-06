/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 8/6/18 11:40 AM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.ui.contract.creditcard.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.api.HcCreditCard;
import vn.homecredit.hcvn.data.model.api.contract.HcContract;
import vn.homecredit.hcvn.databinding.ActivityCreditcardListBinding;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.ui.contract.creditcard.detail.CreditCardDetailActivity;

public class CreditCardListActivity extends BaseActivity<ActivityCreditcardListBinding, CreditCardListViewModel> implements CreditCardContractListener {

    public static final String BUNDLE_INPUT_CONTRACT = "INPUT_CONTRACT ";

    CreditCardListViewAdapter cardListViewAdapter;

    @Inject
    CreditCardListViewModel viewModel;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_transaction_list;
    }

    @Override
    public CreditCardListViewModel getViewModel() {
        return viewModel;
    }

    public static Intent getNewIntent(Context context, HcContract contract) {
        Intent newInstance = new Intent(context, CreditCardListActivity.class);
        if (contract != null) {
            Bundle currentBundle = new Bundle();
            currentBundle.putParcelable(BUNDLE_INPUT_CONTRACT, Parcels.wrap(contract));
            newInstance.putExtras(currentBundle);
        }
        return newInstance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewDataBinding().toolbar.setNavigationOnClickListener(v -> finish());

        if (getIntent().hasExtra(BUNDLE_INPUT_CONTRACT)) {
            HcContract contract = Parcels.unwrap(getIntent().getParcelableExtra(BUNDLE_INPUT_CONTRACT));
            viewModel.setData(this, contract);
        }

        initDataAdapter(viewModel.getData());
    }

    private void initDataAdapter(List<HcCreditCard> cards) {
        cardListViewAdapter = new CreditCardListViewAdapter(this);

        RecyclerView recyclerView = getViewDataBinding().getRoot().findViewById(R.id.lvCreditCards);
        recyclerView.setAdapter(cardListViewAdapter);
        cardListViewAdapter.setListener(this);
        cardListViewAdapter.setNewData(cards);
        SwipeRefreshLayout fresher = findViewById(R.id.fsCreditCardList);
        fresher.setEnabled(false);
    }


    @Override
    public void onCardTapped(HcCreditCard card) {
        if (card == null) {
            showMessage(R.string.data_not_found);
            return;
        }

        Intent intent = CreditCardDetailActivity.getNewIntent(this, card);
        startActivity(intent);
    }
}
