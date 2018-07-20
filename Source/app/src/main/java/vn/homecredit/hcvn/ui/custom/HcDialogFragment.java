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
import android.view.WindowManager;

import vn.homecredit.hcvn.R;


public abstract class HcDialogFragment extends DialogFragment {

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
        return builder.create();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().getAttributes().windowAnimations = styleAnimation;
        }
    }

    protected void setStyleAnimation(int styleAnimation) {
        this.styleAnimation = styleAnimation;
    }

    protected void initView(View view) {
    }
}
