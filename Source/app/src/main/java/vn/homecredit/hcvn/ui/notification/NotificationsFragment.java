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
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onesignal.shortcutbadger.ShortcutBadger;

import javax.inject.Inject;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.databinding.FragmentNotificationsBinding;
import vn.homecredit.hcvn.ui.base.BaseFragment;
import vn.homecredit.hcvn.ui.custom.AppDataView;
import vn.homecredit.hcvn.ui.custom.AppDataViewState;
import vn.homecredit.hcvn.ui.home.HomeActivity;
import vn.homecredit.hcvn.ui.offers.ExpiredOfferActivity;
import vn.homecredit.hcvn.ui.offers.OfferActivity;
import vn.homecredit.hcvn.utils.AppUtils;

public class NotificationsFragment extends BaseFragment<FragmentNotificationsBinding, NotificationViewModel> {
    public static final String ACTION_REFRESH_NOTIFICATIONS = "ACTION_REFRESH_NOTIFICATIONS";

    @Inject
    ViewModelProvider.Factory viewNotificationFactory;
    AppDataView appDataView;
    NotificationAdapter notificationAdapter;
    RecyclerView rvNotifications;

    public static NotificationsFragment newInstance() {
        NotificationsFragment fragment = new NotificationsFragment();
        return fragment;
    }

    public interface OnNotificationCountListener {
        void updateNotificationCount(int count);
    }

    private final BroadcastReceiver mRefreshListBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null && action.equals(ACTION_REFRESH_NOTIFICATIONS)) {
                if (getViewModel() != null) {
                    refreshList();
                }
            }
        }
    };

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mRefreshListBroadcastReceiver,
                new IntentFilter(ACTION_REFRESH_NOTIFICATIONS));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mRefreshListBroadcastReceiver);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        appDataView = getViewDataBinding().advNotifications;
        rvNotifications = new RecyclerView(getActivity());
        initAdapter();
        appDataView.initContentView(rvNotifications, 0, 0, null, () -> getViewModel().pullToRefreshNotifications());
        getViewModel().initData();
        getViewModel().getDataNotifications().observe(this, notificationModels -> notificationAdapter.swapData(notificationModels));
        getViewModel().getModelIsRefreshing().observe(this, isRefreshing -> {
                    if (!isRefreshing)
                        appDataView.updateViewState(AppDataViewState.HIDE_RELOADING);
                    else
                        appDataView.updateViewState(AppDataViewState.SHOW_RELOADING);
                }
        );
        getViewModel().getModelNotificationUnreadCount().observe(this, count -> {
            // Update notification unread count at tab
            ((HomeActivity) getActivity()).updateNotificationCount(count);
            // Update badge number
            ShortcutBadger.applyCount(getActivity().getApplicationContext(), count);
        });
        getViewModel().getModelOpenNotificationMarketingType().observe(this, marketingUrl -> {
            AppUtils.openExternalBrowser(getActivity(), marketingUrl);
        });
        getViewModel().getModelOpenNotificationOfferType().observe(this, offerModel -> {
            OfferActivity.start(getActivity(), offerModel.getCamId());
        });
    }

    private void initAdapter() {
        notificationAdapter = new NotificationAdapter(getActivity(), model -> {
            getViewModel().onNotificationItemClicked(model);
        });
        rvNotifications.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvNotifications.setAdapter(notificationAdapter);
    }

    public void refreshList() {
        appDataView.updateViewState(AppDataViewState.SHOW_RELOADING);
        getViewModel().pullToRefreshNotifications();
    }
}
