/*
 * ContractFragment.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/13/18 4:11 PM
 */

package vn.homecredit.hcvn.ui.contract;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.api.contract.HcContract;
import vn.homecredit.hcvn.databinding.FragmentContractListBinding;
import vn.homecredit.hcvn.ui.base.BaseFragment;
import vn.homecredit.hcvn.ui.contract.dummy.DummyContent;
import vn.homecredit.hcvn.ui.contract.dummy.DummyContent.DummyItem;

import java.util.List;

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

    }
}
