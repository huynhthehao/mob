package vn.homecredit.hcvn.helpers;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.ui.custom.HcDialogFragment;
import vn.homecredit.hcvn.utils.AppUtils;

public class DialogContractsHelp extends HcDialogFragment {

    public static final String TAG_CONTRACTS = "TAG_CONTRACTS";
    public static final String BUNDLE_TITLE = "BUNDLE_TITLE";
    public static final String BUNDLE_CONTENT = "BUNDLE_CONTENT";
    public static final String BUNDLE_CUSTOMER_SUPPORT_PHONE = "BUNDLE_CUSTOMER_SUPPORT_PHONE";
    String customerPhoneNumber = "";

    public static void showDialog(FragmentManager fragmentManager, String title, String content, String customerSupportPhone) {
        if (fragmentManager.findFragmentByTag(TAG_CONTRACTS) == null) {
            DialogContractsHelp dialogContractsHelp = newInstance(title, content, customerSupportPhone);
            dialogContractsHelp.show(fragmentManager, TAG_CONTRACTS);
        }
    }

    public static DialogContractsHelp newInstance(String title, String content, String customerSupportPhone) {
        Bundle args = new Bundle();
        args.putString(BUNDLE_TITLE, title);
        args.putString(BUNDLE_CONTENT, content);
        args.putString(BUNDLE_CUSTOMER_SUPPORT_PHONE, customerSupportPhone);
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
        customerPhoneNumber = getArguments().getString(BUNDLE_CUSTOMER_SUPPORT_PHONE);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        TextView tvContent = view.findViewById(R.id.tvContent);
        TextView tvOk = view.findViewById(R.id.btnOK);
        tvOk.setOnClickListener(v -> dismiss());
        tvTitle.setText(title);
        if (title == null) {
            tvTitle.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(customerPhoneNumber))
            return;
        final SpannableString spannableString = new SpannableString(content);
        spannableString.setSpan(clickableSpan, content.indexOf(customerPhoneNumber),
                content.indexOf(customerPhoneNumber) + customerPhoneNumber.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        tvContent.setText(spannableString);
        tvContent.setMovementMethod(LinkMovementMethod.getInstance());
    }

    final ClickableSpan clickableSpan = new ClickableSpan() {
        @Override
        public void onClick(final View view) {
            if (!TextUtils.isEmpty(customerPhoneNumber)) {
                AppUtils.openDeviceCallDialog(getContext(), customerPhoneNumber);
            }
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(getContext().getResources().getColor(R.color.primary_red));
            ds.setUnderlineText(false);
            ds.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }
    };
}
