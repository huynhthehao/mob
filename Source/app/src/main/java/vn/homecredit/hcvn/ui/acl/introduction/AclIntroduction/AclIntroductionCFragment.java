/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/5/18 2:48 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.ui.acl.introduction.AclIntroduction;


import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.ui.base.BaseSimpleFragment;

public class AclIntroductionCFragment extends BaseSimpleFragment {


    public AclIntroductionCFragment() {
        // Required empty public constructor
    }

    public static AclIntroductionCFragment newInstance() {
        AclIntroductionCFragment fragment = new AclIntroductionCFragment();
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_acl_introduction_c;
    }

}
