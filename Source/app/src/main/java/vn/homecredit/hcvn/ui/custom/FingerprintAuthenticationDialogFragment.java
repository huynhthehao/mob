/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/23/18 1:09 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.ui.custom;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.helpers.fingerprint.FingerprintUiHelper;

@SuppressLint("ValidFragment")
public class FingerprintAuthenticationDialogFragment extends DialogFragment
        implements TextView.OnEditorActionListener, FingerprintUiHelper.Callback {

    private Button mCancelButton;
    private View mFingerprintContent;
    private View mBackupContent;

    private FingerprintManager.CryptoObject mCryptoObject;
    private FingerprintUiHelper fingerprintUiHelper;
    private FingerprintManager fingerprintManager;

    private Runnable onValidateSuccessRunner = () -> System.out.print("Validated successfully");

    @SuppressLint("ValidFragment")
    public FingerprintAuthenticationDialogFragment(FingerprintManager fingerprintManager) {
        this.fingerprintManager = fingerprintManager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog);
    }

    public void setOnValidateSuccess(Runnable onValidateSuccess){
        this.onValidateSuccessRunner = onValidateSuccess;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("");
        //getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        View dialog = inflater.inflate(R.layout.fragment_fingerprint_dialog_container, container, false);
        mCancelButton = dialog.findViewById(R.id.cancel_button);
        mCancelButton.setOnClickListener(view -> dismiss());
        mFingerprintContent = dialog.findViewById(R.id.fingerprint_container);
        mBackupContent = dialog.findViewById(R.id.backup_container);

        fingerprintUiHelper = new FingerprintUiHelper(fingerprintManager,
                dialog.findViewById(R.id.fingerprint_icon),
                dialog.findViewById(R.id.fingerprint_status), this);

        updateStage();

        return dialog;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onResume() {
        super.onResume();
        fingerprintUiHelper.startListening(mCryptoObject);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onPause() {
        super.onPause();
        fingerprintUiHelper.stopListening();
    }


    public void setCryptoObject(FingerprintManager.CryptoObject cryptoObject) {
        mCryptoObject = cryptoObject;
    }


    private void updateStage() {
        mCancelButton.setText(R.string.cancel);
        mFingerprintContent.setVisibility(View.VISIBLE);
        mBackupContent.setVisibility(View.GONE);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_GO) {
            return true;
        }
        return false;
    }

    @Override
    public void onAuthenticated() {
        // TODO: Use mCryptoObject
        onValidateSuccessRunner.run();
        dismiss();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onError() {
        //goToBackup();
    }

}
