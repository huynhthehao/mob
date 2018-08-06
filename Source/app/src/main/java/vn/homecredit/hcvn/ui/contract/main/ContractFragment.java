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
import android.support.v7.widget.RecyclerView;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.api.contract.HcContract;
import vn.homecredit.hcvn.databinding.FragmentContractListBinding;
import vn.homecredit.hcvn.ui.base.BaseFragment;
import vn.homecredit.hcvn.ui.contract.creditcard.CreditCardListActivity;
import vn.homecredit.hcvn.ui.contract.detail.ContractDetailActivity;
import vn.homecredit.hcvn.ui.contract.summaryContract.SummaryContractActivity;
import vn.homecredit.hcvn.ui.map.PayMapActivity;

import javax.inject.Inject;

public class ContractFragment extends BaseFragment<FragmentContractListBinding, ContractViewModel> implements ContractRecyclerViewAdapter.OnContractListener {

    ContractRecyclerViewAdapter contractRecyclerViewAdapter;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

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

    }


    @Override
    public void onClicked(int position) {
        HcContract selectedItem = getViewModel().getDataAtIndex(position);
        if (selectedItem == null) {
            showMessage(R.string.data_not_found);
            return;
        }

        if (selectedItem.isCreditCard()) {
            Intent intent = CreditCardListActivity.getNewIntent(getContext(), selectedItem);
            startActivity(intent);
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
