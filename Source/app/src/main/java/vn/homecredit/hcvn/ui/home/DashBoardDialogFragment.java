package vn.homecredit.hcvn.ui.home;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.custom.FullscreenDialogFragment;


public class DashBoardDialogFragment extends FullscreenDialogFragment implements View.OnClickListener {
    public static final String TAG_DASHBOARD = "TAG_DASHBOARD";
    public static final String BUNDLE_GREETING = "GREETING";
    public static final String BUNDLE_USERNAME = "USERNAME";

    OnDashboardClicked onDashboardClicked;

    public static DashBoardDialogFragment newInstance(String greeting, String username) {
        Bundle args = new Bundle();
        args.putString(BUNDLE_GREETING, greeting);
        args.putString(BUNDLE_USERNAME, username);
        DashBoardDialogFragment fragment = new DashBoardDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_dashboard;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        view.findViewById(R.id.dashboardContact).setOnClickListener(this);
        view.findViewById(R.id.dashboardSchedule).setOnClickListener(this);
        view.findViewById(R.id.dashboardOffer).setOnClickListener(this);
        view.findViewById(R.id.dashboardNotification).setOnClickListener(this);
        view.findViewById(R.id.dashboardMomo).setOnClickListener(this);
        view.findViewById(R.id.dashboardMore).setOnClickListener(this);
        if (getArguments() != null && getArguments().containsKey(BUNDLE_USERNAME) && getArguments().containsKey(BUNDLE_GREETING)) {
            TextView tvGreeting = view.findViewById(R.id.tvGreeting);
            TextView tvUsername = view.findViewById(R.id.tvUserName);
            tvGreeting.setText(getArguments().getString(BUNDLE_GREETING));
            tvUsername.setText(getArguments().getString(BUNDLE_USERNAME));
        }
    }

    @Override
    public void onClick(View v) {
        if (onDashboardClicked == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.dashboardContact:
                onDashboardClicked.onClickedContact();
                dismiss();
                break;
            case R.id.dashboardSchedule:
                onDashboardClicked.onClickedSchedule();
                dismiss();
                break;
            case R.id.dashboardOffer:
                onDashboardClicked.onClickedOffer();
                dismiss();
                break;
            case R.id.dashboardNotification:
                onDashboardClicked.onClickedNotification();
                dismiss();
                break;
            case R.id.dashboardMomo:
                onDashboardClicked.onClickedMomo();
                dismiss();
                break;
            case R.id.dashboardMore:
                onDashboardClicked.onClickedMore();
                dismiss();
                break;
        }
    }

    public void setOnDashboardClicked(OnDashboardClicked onDashboardClicked) {
        this.onDashboardClicked = onDashboardClicked;
    }

    public interface OnDashboardClicked {
        void onClickedContact();
        void onClickedSchedule();
        void onClickedOffer();
        void onClickedNotification();
        void onClickedMomo();
        void onClickedMore();
    }
}