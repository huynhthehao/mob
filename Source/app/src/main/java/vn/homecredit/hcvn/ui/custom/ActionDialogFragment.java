/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/25/18 2:46 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.ui.custom;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.helpers.UiHelper;


@SuppressLint("ValidFragment")
public class ActionDialogFragment extends DialogFragment {

    private TextView cancelButton;
    private TextView actionButton;
    private TextView contentTextView;
    private ImageView titleIcon;

    private boolean isSetPadding = false;
    private boolean showTitleIcon = false;
    private int actionButtonLabelId;
    private int titleIconId;
    private CharSequence messageContent;
    private static final String PRIVATE_TAG = "Action_Dialog";

    private Runnable onActionCalledRunner = () -> System.out.print("Action called");
    private Runnable onDismissRunner = () -> System.out.print("Dismissed");

    @SuppressLint("ValidFragment")
    public ActionDialogFragment(CharSequence messageContent, int actionButtonLabelId, int titleIconId) {
        this.actionButtonLabelId = actionButtonLabelId;
        this.titleIconId = titleIconId;
        this.messageContent = messageContent;
        showTitleIcon = true;
    }

    @SuppressLint("ValidFragment")
    public ActionDialogFragment(CharSequence messageContent, int actionButtonLabelId) {
        this.actionButtonLabelId = actionButtonLabelId;
        this.messageContent = messageContent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog);
    }

    public void setOnActionCalled(Runnable onActionCalledRunner){
        this.onActionCalledRunner = onActionCalledRunner;
    }

    public void setOnDismiss(Runnable onDismissRunner){
        this.onDismissRunner = onDismissRunner;
    }


    public static void showDialog(FragmentManager fragmentManager, CharSequence messageContent, int actionButtonTextId, int titleIconId, Runnable onActionCalledRunner) {
        if (fragmentManager.findFragmentByTag(PRIVATE_TAG) != null)
            return;

        ActionDialogFragment dialog = new ActionDialogFragment(messageContent, actionButtonTextId, titleIconId);
        dialog.setOnActionCalled(onActionCalledRunner);
        dialog.show(fragmentManager, PRIVATE_TAG);
    }


    @Override
    public void onStart() {
        super.onStart();
        if(isSetPadding)
            return;
        isSetPadding = true;

        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setPadding();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View dialogView = inflater.inflate(R.layout.fragment_action_dialog, container, false);

        cancelButton = dialogView.findViewById(R.id.btnCancel);
        actionButton = dialogView.findViewById(R.id.btnAction);
        titleIcon = dialogView.findViewById(R.id.titleIcon);
        contentTextView = dialogView.findViewById(R.id.contentMessage);
        cancelButton.setOnClickListener(view -> onClose(view));
        actionButton.setOnClickListener(view -> onAction(view));

        updateStage();
        return dialogView;
    }

    private void onClose(View view){
        onDismissRunner.run();
        dismiss();
    }

    private void onAction(View view){
        onActionCalledRunner.run();
        dismiss();
        //view.postDelayed(() -> dismiss(), 500);
    }

    @Override
    public void onDismiss(DialogInterface dialog){
        super.onDismiss(dialog);
        onDismissRunner.run();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onResume() {
        super.onResume();
    }


    private void updateStage() {
        cancelButton.setText(R.string.cancel);
        actionButton.setText(actionButtonLabelId);
        contentTextView.setText(messageContent);
        if(showTitleIcon){
            titleIcon.setImageResource(titleIconId);
        } else {
            titleIcon.setVisibility(View.GONE);
        }
    }


    private void setPadding() {
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        int padding = UiHelper.dpToPx(getActivity(),10);
        params.y = UiHelper.dpToPx(getActivity(), params.y - padding);
        window.setAttributes(params);
    }
}
