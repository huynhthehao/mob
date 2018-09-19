/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 8/22/18 5:20 PM, by hien.nguyenm
 */

package vn.homecredit.hcvn.ui.acl.introduction.AclIntroduction;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.homecredit.hcvn.R;


public class AclIntroductionDFragment extends Fragment {

    public AclIntroductionDFragment() {
        // Required empty public constructor
    }


    public static AclIntroductionDFragment newInstance() {
        AclIntroductionDFragment fragment = new AclIntroductionDFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_acl_introduction_d, container, false);
    }
}
