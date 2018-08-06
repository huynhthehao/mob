/*
 * MoreFragment.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/13/18 4:07 PM
 */

package vn.homecredit.hcvn.ui.more;


import android.app.NotificationManager;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import javax.inject.Inject;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.databinding.FragmentMoreBinding;
import vn.homecredit.hcvn.ui.base.BaseFragment;
import vn.homecredit.hcvn.ui.map.PayMapActivity;
import vn.homecredit.hcvn.ui.profile.ProfileActivity;
import vn.homecredit.hcvn.ui.settings.SettingsActivity;
import vn.homecredit.hcvn.ui.settings.changepass.ChangePassActivity;
import vn.homecredit.hcvn.utils.AppUtils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoreFragment extends BaseFragment<FragmentMoreBinding, MoreViewModel> {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    public MoreFragment() {
        // Required empty public constructor
    }

    public static MoreFragment newInstance() {
        MoreFragment fragment = new MoreFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.moreviewmodel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_more;
    }

    @Override
    public MoreViewModel getViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(MoreViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getViewModel().init();
        getViewModel().getModelProfile().observe(this, clickedProfile -> showProfile(clickedProfile, "clicked Profile"));
        getViewModel().getModelPassword().observe(this, clickedPassword -> showPassword(clickedPassword, "clicked Password"));
        getViewModel().getModelSetting().observe(this, clickedSetting -> showSetting(clickedSetting));
        getViewModel().getModelLocation().observe(this, clickedLocation -> showLocation(clickedLocation));
        getViewModel().getModelMomo().observe(this, clickedMomo -> showMomo(clickedMomo));
        getViewModel().getModelUrlTerm().observe(this, urlTerm -> showTerm(urlTerm));
        getViewModel().getModelUrlUserGuide().observe(this, urlUserGuide -> showUserGuide(urlUserGuide));
        getViewModel().getModelLogout().observe(this, clickedLogout -> showLogout(clickedLogout));
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    private void showLogout(Boolean clickedLogout) {
        if (clickedLogout) {
            showConfirmMessage(R.string.log_out, R.string.logout_question, (yes) -> {
                if (yes) {
                    getViewModel().logout();
                    // clear all current push notifications on status bar
                    NotificationManager nm = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                    nm.cancelAll();
                    // restart app
                    AppUtils.restartApp(getActivity());
                }
            });
        }
    }

    private void showMomo(Boolean clickedMomo) {
        if (clickedMomo) {
            showToast("clicked Momo");
        }
    }

    private void showLocation(Boolean clickedLocation) {
        if (clickedLocation) {
//            showToast("clicked Location");
            PayMapActivity.start(getContext(),PayMapActivity.PAYMENT_MODE);
        }
    }

    private void showSetting(Boolean clickedSetting) {
        if (clickedSetting) {
            Intent intent = SettingsActivity.newIntent(getContext());
            startActivity(intent);
        }
    }

    private void showPassword(Boolean clickedPassword, String s) {
        if (clickedPassword) {
            ChangePassActivity.start(getActivity());
        }
    }

    private void showProfile(Boolean clickedProfile, String s) {
        if (clickedProfile) {
            Intent intent = new Intent(getActivity(), ProfileActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void showTerm(String urlTerm) {
        if (urlTerm != null) {
            AppUtils.openExternalBrowser(getActivity(), urlTerm);
        }
    }

    private void showUserGuide(String urlUserGuide) {
        if (urlUserGuide != null) {
            AppUtils.openExternalBrowser(getActivity(), urlUserGuide);
        }
    }

    private void showToast(String ss) {
        Toast.makeText(getActivity(), ss, Toast.LENGTH_SHORT).show();
    }
}
