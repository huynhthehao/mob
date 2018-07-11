package vn.homecredit.hcvn.ui.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import vn.homecredit.hcvn.ui.contract.ContractFragment;
import vn.homecredit.hcvn.ui.more.MoreFragment;
import vn.homecredit.hcvn.ui.notification.NotificationFragment;
import vn.homecredit.hcvn.ui.support.SupportFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public static final int TAB_CONTRACTS = 0;
    public static final int TAB_NOTIFICATION = 1;
    public static final int TAB_SUPPORT = 2;
    public static final int TAB_MORE = 3;

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case TAB_CONTRACTS:
                return ContractFragment.newInstance(1);
            case TAB_NOTIFICATION:
                return NotificationFragment.newInstance(1);
            case TAB_SUPPORT:
                return SupportFragment.newInstance("A", "B");
            case TAB_MORE:
                return MoreFragment.newInstance();
            default:
                break;
        }
        return PlaceholderFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return 4;
    }
}