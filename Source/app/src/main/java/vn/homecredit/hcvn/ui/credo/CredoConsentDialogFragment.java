/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/25/18 2:46 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.ui.credo;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import vn.homecredit.hcvn.R;


@SuppressLint("ValidFragment")
public class CredoConsentDialogFragment extends DialogFragment {

    private TextView disagreeButton;
    private Button agreeButton;
    private WebView consentWebview;

    private static final String PRIVATE_TAG = "Credo_Consent_Dialog";

    private Runnable onSubmitCalledRunner = () -> System.out.print("Submit Credo called");
    private Runnable onDismissRunner = () -> System.out.print("Dismissed");


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void setOnActionCalled(Runnable onActionCalledRunner){
        this.onSubmitCalledRunner = onActionCalledRunner;
    }

    public void setOnDismiss(Runnable onDismissRunner){
        this.onDismissRunner = onDismissRunner;
    }


    public static void showDialog(FragmentManager fragmentManager, Runnable onSubmitCalledRunner) {
        if (fragmentManager.findFragmentByTag(PRIVATE_TAG) != null)
            return;

        CredoConsentDialogFragment dialog = new CredoConsentDialogFragment();
        dialog.setOnActionCalled(onSubmitCalledRunner);
        dialog.show(fragmentManager, PRIVATE_TAG);
    }


    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View dialogView = inflater.inflate(R.layout.fragment_credo_consent_dialog, container, false);

        disagreeButton = dialogView.findViewById(R.id.btnCredoDisagree);
        agreeButton = dialogView.findViewById(R.id.btnCredoAgree);
        consentWebview = dialogView.findViewById(R.id.wvConsent);

        initWebviewContent();

        disagreeButton.setOnClickListener(view -> onClose(view));
        agreeButton.setOnClickListener(view -> onSubmit(view));

        return dialogView;
    }

    private void initWebviewContent(){
        WebSettings webSettings = consentWebview.getSettings();
        //webSettings.setTextSize(WebSettings.TextSize.SMALLER);
        webSettings.setDefaultFontSize(13);

        String consentString = getString(R.string.desc_credo_consent);
        consentWebview.loadData(consentString, "text/html", "UTF-8");
    }

    private void onClose(View view){
        onDismissRunner.run();
        dismiss();
    }

    private void onSubmit(View view){
        onSubmitCalledRunner.run();
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
}
