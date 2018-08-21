package vn.homecredit.hcvn.ui.home;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.ui.contract.main.ContractFragment;
import vn.homecredit.hcvn.ui.more.MoreFragment;
import vn.homecredit.hcvn.ui.notification.NotificationsFragment;
import vn.homecredit.hcvn.ui.support.SupportFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    Context context;
    public static final int TAB_CONTRACTS = 0;
    public static final int TAB_NOTIFICATION = 1;
    public static final int TAB_SUPPORT = 2;
    public static final int TAB_MORE = 3;
    private int[] imageResId = {R.drawable.ic_tab_contract, R.drawable.ic_tab_notification, R.drawable.ic_tab_support, R.drawable.ic_more};

    public SectionsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case TAB_CONTRACTS:
                return new ContractFragment();
            case TAB_NOTIFICATION:
                return NotificationsFragment.newInstance();
            case TAB_SUPPORT:
                return SupportFragment.newInstance();
            case TAB_MORE:
                return MoreFragment.newInstance();
            default:
                break;
        }
        return new ContractFragment();
    }


    @Override
    public int getCount() {
        return 4;
    }

    public View getTabView(int position) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_tab_view, null);
        ImageView img = v.findViewById(R.id.ivImage);
        img.setImageResource(imageResId[position]);
        return v;
    }

    public void updateNotificationCount(View view, int count) {
        TextView tvNotification = view.findViewById(R.id.tvNotificationCount);
        if (count == 0) {
            tvNotification.setVisibility(View.GONE);
        } else {
            tvNotification.setVisibility(View.VISIBLE);
            tvNotification.setText(String.valueOf(count));
        }
    }
}