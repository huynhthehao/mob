/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/5/18 2:35 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.ui.acl.introduction.AclIntroduction;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.ui.base.BaseSimpleFragment;

public class AclIntroductionBFragment extends BaseSimpleFragment {

    public AclIntroductionBFragment() {
        // Required empty public constructor
    }

    public static AclIntroductionBFragment newInstance() {
        AclIntroductionBFragment fragment = new AclIntroductionBFragment();
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_acl_introduction_b;
    }

}
