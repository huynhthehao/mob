package vn.homecredit.hcvn.ui.settings.changepass;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.ui.custom.FullscreenDialogFragment;

public class DialogChangePassSuccess extends FullscreenDialogFragment {

    public static final String TAG_CHANGE_PASS_SUCCESS = "TAG_CHANGE_PASS_SUCCESS";
    private OnChangePassSuccessListener onChangePassSuccessListener;

    public static void showDialog(FragmentManager fragmentManager, OnChangePassSuccessListener onChangePassSuccessListener) {
        if (fragmentManager.findFragmentByTag(TAG_CHANGE_PASS_SUCCESS) == null) {
            DialogChangePassSuccess dialogChangePassSuccess = new DialogChangePassSuccess();
            dialogChangePassSuccess.setOnChangePassSuccessListener(onChangePassSuccessListener);
            dialogChangePassSuccess.show(fragmentManager, TAG_CHANGE_PASS_SUCCESS );

        }
    }
    @Override
    public int getLayoutId() {
        return R.layout.dialog_congratulation;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        view.findViewById(R.id.btnNext).setOnClickListener(v -> {
            dismiss();
            if (onChangePassSuccessListener != null) {
                onChangePassSuccessListener.onClickedNext();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().setCancelable(false);
    }

    public void setOnChangePassSuccessListener(OnChangePassSuccessListener onChangePassSuccessListener) {
        this.onChangePassSuccessListener = onChangePassSuccessListener;
    }

    public interface OnChangePassSuccessListener {
        void onClickedNext();
    }
}
