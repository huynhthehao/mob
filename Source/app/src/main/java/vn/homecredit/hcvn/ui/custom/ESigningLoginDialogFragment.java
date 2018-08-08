package vn.homecredit.hcvn.ui.custom;

import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.helpers.fingerprint.FingerprintUiHelper;

import static vn.homecredit.hcvn.ui.signup.DialogContractsHelp.TAG_CONTRACTS;

public class ESigningLoginDialogFragment extends HcDialogFragment implements FingerprintUiHelper.Callback {

    private static final String TAG_ESIGNING_DIALOG = "TAG_ESIGNING_DIALOG";
    private static final String BUNDLE_USE_FINGERPRINT = "BUNDLE_USE_FINGERPRINT";
    private ImageView ivFingerIcon;
    private TextView tvTitle;
    private EditText edtPassword;
    private TextView btnCancel;
    private TextView btnOK;
    private boolean useFingerprint;

    private OnESingingListener onESingingListener;
    private FingerprintUiHelper fingerPrintUiHelper;

    public static void showDialog(FragmentManager fragmentManager, boolean useFingerprint, OnESingingListener onESingingListener) {
        if (fragmentManager.findFragmentByTag(TAG_CONTRACTS) == null) {
            ESigningLoginDialogFragment eSigningLoginDialogFragment = newInstance(useFingerprint);
            eSigningLoginDialogFragment.setOnESingingListener(onESingingListener);
            eSigningLoginDialogFragment.show(fragmentManager, TAG_ESIGNING_DIALOG);
        }
    }

    public static ESigningLoginDialogFragment newInstance(boolean useFingerprint) {
        Bundle args = new Bundle();
        args.putBoolean(BUNDLE_USE_FINGERPRINT, useFingerprint);
        ESigningLoginDialogFragment fragment = new ESigningLoginDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setOnESingingListener(OnESingingListener onESingingListener) {
        this.onESingingListener = onESingingListener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_esigning_fingerprint;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        ivFingerIcon = view.findViewById(R.id.fingerprint_icon);
        tvTitle = view.findViewById(R.id.tvTitle);
        edtPassword = view.findViewById(R.id.passwordTextView);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnOK = view.findViewById(R.id.btnOK);
        btnOK.setOnClickListener(v -> {
            if (useFingerprint ) {
                useFingerprint = false;
                updateUI(useFingerprint);
            }else {
                onESingingListener.onPassword(edtPassword.getText().toString());
                dismiss();
            }
        });

        btnCancel.setOnClickListener(v -> dismiss());
        useFingerprint = getArguments().getBoolean(BUNDLE_USE_FINGERPRINT, false);
        updateUI(useFingerprint);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            FingerprintManager fingerprintManager = getContext().getSystemService(FingerprintManager.class);
            fingerPrintUiHelper = new FingerprintUiHelper(fingerprintManager,this.getContext(),
                    ivFingerIcon,
                    view.findViewById(R.id.fingerprint_description), this);
            fingerPrintUiHelper.startListening(null);
        }
    }


    private void updateUI(boolean useFingerprint) {
        if (useFingerprint) {
            btnCancel.setVisibility(View.GONE);
            edtPassword.setVisibility(View.GONE);
            ivFingerIcon.setVisibility(View.VISIBLE);
            btnOK.setText(R.string.use_password);
            tvTitle.setText(R.string.dialog_esigning_title);
            btnOK.setText(R.string.action_next);
        }else {
            btnCancel.setVisibility(View.VISIBLE);
            edtPassword.setVisibility(View.VISIBLE);
            ivFingerIcon.setVisibility(View.GONE);
            tvTitle.setText(R.string.dialog_esigning_title);
            btnOK.setText(R.string.done);
        }
    }

    @Override
    public void onAuthenticated() {
        dismiss();
    }

    @Override
    public void onError() {

    }

    public interface OnESingingListener {
        void onFingerprint();
        void onPassword(String password);
    }
}
