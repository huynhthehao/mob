/*
 * SupportFragment.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/13/18 2:46 PM
 */

package vn.homecredit.hcvn.ui.support;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.databinding.FragmentSupportBinding;
import vn.homecredit.hcvn.ui.base.BaseFragment;

public class SupportFragment extends BaseFragment<FragmentSupportBinding, SupportViewModel> {
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    public static SupportFragment newInstance() {
        SupportFragment fragment = new SupportFragment();
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_support;
    }

    @Override
    public SupportViewModel getViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(SupportViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getViewModel().init();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
