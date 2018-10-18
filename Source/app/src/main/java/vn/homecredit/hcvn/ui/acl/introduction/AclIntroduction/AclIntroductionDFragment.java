/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 8/22/18 5:20 PM, by hien.nguyenm
 */

package vn.homecredit.hcvn.ui.acl.introduction.AclIntroduction;


import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.ui.base.BaseSimpleFragment;


public class AclIntroductionDFragment extends BaseSimpleFragment {

    public AclIntroductionDFragment() {
        // Required empty public constructor
    }

    public static AclIntroductionDFragment newInstance() {
        AclIntroductionDFragment fragment = new AclIntroductionDFragment();
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_acl_introduction_d;
    }
}
