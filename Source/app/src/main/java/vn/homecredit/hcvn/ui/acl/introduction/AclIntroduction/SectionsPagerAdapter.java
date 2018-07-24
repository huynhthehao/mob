/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/5/18 5:09 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.ui.acl.introduction.AclIntroduction;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import vn.homecredit.hcvn.ui.acl.introduction.AclSelectLoanType.AclSelectLoanTypeFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        switch (position) {
            case 0:
                return AclIntroductionAFragment.newInstance();
            case 1:
                return AclIntroductionBFragment.newInstance();
            case 2:
                return AclIntroductionCFragment.newInstance();
            case 3:
                return AclSelectLoanTypeFragment.newInstance();
        };
        return null;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 4;
    }
}
