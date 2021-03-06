/*
 * ContractFragment.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/13/18 4:11 PM
 */

package vn.homecredit.hcvn.ui.contract.main;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.api.contract.HcContract;
import vn.homecredit.hcvn.data.model.api.creditcard.HcCreditCard;
import vn.homecredit.hcvn.databinding.FragmentContractListBinding;
import vn.homecredit.hcvn.ui.base.BaseFragment;
import vn.homecredit.hcvn.ui.contract.creditcard.detail.CreditCardDetailActivity;
import vn.homecredit.hcvn.ui.contract.creditcard.list.CreditCardListActivity;
import vn.homecredit.hcvn.ui.contract.detail.ContractDetailActivity;
import vn.homecredit.hcvn.ui.contract.summaryContract.SummaryContractActivity;
import vn.homecredit.hcvn.ui.map.PayMapActivity;

import javax.inject.Inject;

public class ContractFragment extends BaseFragment<FragmentContractListBinding, ContractViewModel> implements ContractRecyclerViewAdapter.OnContractListener {
    private static final String BUNDLE_ACTIVE_CONTRACT = "BUNDLE_ACTIVE_CONTRACT";

    ContractRecyclerViewAdapter contractRecyclerViewAdapter;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    public static ContractFragment newInstance() {
        return newInstance(false);
    }

    public static ContractFragment newInstance(boolean isActiveContract) {
        Bundle args = new Bundle();
        args.putBoolean(BUNDLE_ACTIVE_CONTRACT, isActiveContract);
        ContractFragment fragment = new ContractFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_contract_list;
    }

    @Override
    public ContractViewModel getViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(ContractViewModel.class);
    }

    @Override
    protected void init() {
        super.init();
        contractRecyclerViewAdapter = new ContractRecyclerViewAdapter();
        contractRecyclerViewAdapter.setOnContractListener(this);
        RecyclerView rvContract = getViewDataBinding().getRoot().findViewById(R.id.rvContract);
        rvContract.setAdapter(contractRecyclerViewAdapter);
        getViewModel().getListMutableLiveData().observe(this, hcContracts -> {
            if (hcContracts != null) {
                contractRecyclerViewAdapter.setNewData(hcContracts);
            }
        });
        getViewModel().getRefreshing().observe(this, aBoolean -> getViewDataBinding().swiperefresh.setRefreshing(aBoolean));
        if (getArguments() != null && getArguments().getBoolean(BUNDLE_ACTIVE_CONTRACT, false)) {
            getViewModel().getActiviteContracṭ̣();
        }else {
            getViewModel().getContracṭ̣();
        }

    }


    @Override
    public void onClicked(int position) {
        HcContract selectedContract = getViewModel().getDataAtIndex(position);
        if (selectedContract == null) {
            showMessage(R.string.data_not_found);
            return;
        }

        if (selectedContract.isCreditCard()) {
            if (selectedContract.getCards() != null && selectedContract.getCards().size() > 1) {
                Intent intent = CreditCardListActivity.getNewIntent(getContext(), selectedContract);
                startActivity(intent);
            } else {
                HcCreditCard currentCard = selectedContract.getCards().get(0);
                currentCard.refContract = selectedContract;
                Intent intent = CreditCardDetailActivity.getNewIntent(getContext(), currentCard);
                startActivity(intent);
            }
            return;
        }

        ContractDetailActivity.start(getContext(), contractRecyclerViewAdapter.getItem(position));
    }

    @Override
    public void onLocationClicked(int position) {
        PayMapActivity.start(getContext(), PayMapActivity.PAYMENT_MODE);

    }

    @Override
    public void onSignClicked(int position) {
        SummaryContractActivity.start(getActivity(), contractRecyclerViewAdapter.getItem(position));
    }
}
