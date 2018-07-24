/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/5/18 12:42 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.ui.acl.introduction.AclIntroduction;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.homecredit.hcvn.R;

public class AclIntroductionAFragment extends Fragment {

    public AclIntroductionAFragment() {
        // Required empty public constructor
    }
    public static AclIntroductionAFragment newInstance() {
        AclIntroductionAFragment fragment = new AclIntroductionAFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_acl_introduction_a, container, false);
    }

}
