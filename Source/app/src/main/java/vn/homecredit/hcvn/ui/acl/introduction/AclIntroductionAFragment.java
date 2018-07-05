/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/5/18 12:42 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.ui.acl.introduction;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.homecredit.hcvn.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AclIntroductionAFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AclIntroductionAFragment extends Fragment {

    public AclIntroductionAFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AclIntroductionAFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AclIntroductionAFragment newInstance() {
        AclIntroductionAFragment fragment = new AclIntroductionAFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_acl_introduction_a, container, false);
    }

}
