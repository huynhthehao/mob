package vn.homecredit.hcvn.ui.setpassword;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.custom.HcDialogFragment;

public class DialogAnnounceFingerprint extends HcDialogFragment {

    private static final String TAG_FINGERSPRINT = "TAG_FINGERSPRINT";

    public static void showDialog(FragmentManager fragmentManager) {
        if (fragmentManager.findFragmentByTag(TAG_FINGERSPRINT) == null) {
            DialogAnnounceFingerprint dialogFingersprint = newInstance();
            dialogFingersprint.show(fragmentManager, TAG_FINGERSPRINT);
        }
    }

    public static DialogAnnounceFingerprint newInstance() {
        Bundle args = new Bundle();
        DialogAnnounceFingerprint fragment = new DialogAnnounceFingerprint();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int getLayoutId() {
        return R.layout.dialog_announce_fingersprint;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        view.findViewById(R.id.btnCancel).setOnClickListener(v -> dismiss());
        view.findViewById(R.id.btnOK).setOnClickListener(v -> dismiss());
    }
}
