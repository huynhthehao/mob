package vn.homecredit.hcvn.ui.signup;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.ui.custom.HcDialogFragment;

public class DialogContractsHelp extends HcDialogFragment {

    public static final String TAG_CONTRACTS = "TAG_CONTRACTS";
    public static final String BUNDLE_TITLE = "BUNDLE_TITLE";
    public static final String BUNDLE_CONTENT = "BUNDLE_CONTENT";

    public static void showDialog(FragmentManager fragmentManager, String title, String content) {
        if (fragmentManager.findFragmentByTag(TAG_CONTRACTS) == null) {
            DialogContractsHelp dialogContractsHelp = newInstance(title, content);
            dialogContractsHelp.show(fragmentManager, TAG_CONTRACTS);
        }
    }

    public static DialogContractsHelp newInstance(String title, String content) {
        Bundle args = new Bundle();
        args.putString(BUNDLE_TITLE, title);
        args.putString(BUNDLE_CONTENT, content);
        DialogContractsHelp fragment = new DialogContractsHelp();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_homecredit_simple;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        String title = getArguments().getString(BUNDLE_TITLE);
        String content = getArguments().getString(BUNDLE_CONTENT);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        TextView tvContent = view.findViewById(R.id.tvContent);
        TextView tvOk = view.findViewById(R.id.btnOK);
        tvOk.setOnClickListener(v -> dismiss());
        tvTitle.setText(title);
        if (title == null) {
            tvTitle.setVisibility(View.GONE);
        }
        tvContent.setText(content);
        Linkify.addLinks(tvContent, Linkify.PHONE_NUMBERS);
        tvContent.setLinkTextColor(getResources().getColor(R.color.colorPrimary));
        tvContent.setClickable(true);
    }
}
