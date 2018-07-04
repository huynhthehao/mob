package vn.homecredit.hcvn.ui.home;

import android.view.View;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.custom.FullscreenDialogFragment;


public class DashBoardDialogFragment extends FullscreenDialogFragment implements View.OnClickListener {
    public static final String TAG_DASHBOARD = "TAG_DASHBOARD";
    OnDashboardClicked onDashboardClicked;

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
