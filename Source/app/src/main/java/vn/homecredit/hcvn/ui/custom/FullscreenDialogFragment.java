/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/6/18 4:57 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.ui.custom;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import vn.homecredit.hcvn.HCVNApp;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.service.tracking.TrackingService;


public abstract class FullscreenDialogFragment extends DialogFragment {

    private TrackingService trackingService;

    abstract public int getLayoutId();

    private int styleAnimation = R.style.DialogAnimation;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(getLayoutId(), null);
        initView(view);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        trackingService = ((HCVNApp) getActivity().getApplication()).getTrackingService();
        return alertDialog;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().getAttributes().windowAnimations = styleAnimation;
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams layoutParams = getDialog().getWindow().getAttributes();
            layoutParams.dimAmount = 0;
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        }
        if (trackingService != null) {
            trackingService.sendView(this);
        }
    }

    protected void setStyleAnimation(int styleAnimation) {
        this.styleAnimation = styleAnimation;
    }

    protected void initView(View view) {
    }
}
