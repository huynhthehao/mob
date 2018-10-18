/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/5/18 12:42 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.ui.acl.introduction.AclIntroduction;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.ui.base.BaseSimpleFragment;

public class AclIntroductionAFragment extends BaseSimpleFragment {

    public AclIntroductionAFragment() {
        // Required empty public constructor
    }
    public static AclIntroductionAFragment newInstance() {
        AclIntroductionAFragment fragment = new AclIntroductionAFragment();
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_acl_introduction_a;
    }


}
