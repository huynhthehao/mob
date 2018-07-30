/*
 * NotificationFragment.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/13/18 4:13 PM
 */

package vn.homecredit.hcvn.ui.notification;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.databinding.FragmentNotificationsBinding;
import vn.homecredit.hcvn.ui.base.BaseFragment;
import vn.homecredit.hcvn.ui.custom.AppDataView;
import vn.homecredit.hcvn.utils.Log;

public class NotificationsFragment extends BaseFragment<FragmentNotificationsBinding, NotificationViewModel> {
    @Inject
    ViewModelProvider.Factory viewNotificationFactory;
    AppDataView appDataView;
    NotificationAdapter notificationAdapter;
    RecyclerView rvNotifications;

    public static NotificationsFragment newInstance() {
        NotificationsFragment fragment = new NotificationsFragment();
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_notifications;
    }

    @Override
    public NotificationViewModel getViewModel() {
        return ViewModelProviders.of(this, viewNotificationFactory).get(NotificationViewModel.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        appDataView = getViewDataBinding().advNotifications;
        rvNotifications = new RecyclerView(getActivity());
        initAdapter();
        appDataView.initContentView(rvNotifications, 0, 0, null, () -> {
            //TODO
        });
        getViewModel().init();
        getViewModel().getDataNotitifications().observe(this, notificationModels -> {
            notificationAdapter.swapData(notificationModels);
        });
    }

    private void initAdapter() {
        notificationAdapter = new NotificationAdapter(model -> {
            //TODO
        });
        rvNotifications.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvNotifications.setAdapter(notificationAdapter);
    }
}
