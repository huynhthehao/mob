/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/5/18 5:13 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.ui.acl.introduction.AclSelectLoanType;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import javax.inject.Inject;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.databinding.FragmentAclSelectLoanTypeBinding;
import vn.homecredit.hcvn.ui.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AclSelectLoanTypeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AclSelectLoanTypeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AclSelectLoanTypeFragment extends BaseFragment<FragmentAclSelectLoanTypeBinding, AclSelectLoanTypeViewModel> implements AclSelectLoanTypeNavigator {

    public static final String TAG = AclSelectLoanTypeFragment.class.getSimpleName();
    @Inject
    AclSelectLoanTypeViewModel mAclSelectLoanTypeViewModel;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AclSelectLoanTypeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AclSelectLoanTypeFragment newInstance() {
        AclSelectLoanTypeFragment fragment = new AclSelectLoanTypeFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: Need to modified
        //mAclSelectLoanTypeViewModel.setNavigator(this);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_acl_select_loan_type;
    }

    @Override
    public AclSelectLoanTypeViewModel getViewModel() {
        return mAclSelectLoanTypeViewModel;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void goACLO() {
        mListener.onSelectACLO();
    }

    @Override
    public void goPos() {
        mListener.onSelectPOS();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

        void onSelectACLO();
        void onSelectPOS();
    }
}
