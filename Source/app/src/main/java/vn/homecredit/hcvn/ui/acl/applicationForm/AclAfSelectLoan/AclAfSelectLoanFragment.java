/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/9/18 5:14 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.ui.acl.applicationForm.AclAfSelectLoan;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxSeekBar;

import javax.inject.Inject;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.BR;
import  vn.homecredit.hcvn.databinding.FragmentAclAfSelectLoanBinding;
import vn.homecredit.hcvn.ui.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AclAfSelectLoanFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AclAfSelectLoanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AclAfSelectLoanFragment extends BaseFragment<FragmentAclAfSelectLoanBinding, AclAfSelectLoanViewModel> implements AclAfSelectLoanNavigator {

    @Inject
    AclAfSelectLoanViewModel mAclAfSelectLoanViewModel;

    private OnFragmentInteractionListener mListener;

    public AclAfSelectLoanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AclAfSelectLoanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AclAfSelectLoanFragment newInstance() {
        AclAfSelectLoanFragment fragment = new AclAfSelectLoanFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void init() {
        getViewModel().setNavigator(this);
        getViewModel().AmountSliderChangedSubject = RxSeekBar.userChanges(getViewDataBinding().loanAmountSeekBar);
        getViewModel().init();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getViewDataBinding().noteTextView.setText(Html.fromHtml(getContext().getString(R.string.legal_note)));
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
        return R.layout.fragment_acl_af_select_loan;
    }

    @Override
    public AclAfSelectLoanViewModel getViewModel() {
        return mAclAfSelectLoanViewModel;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void popToRoot() {
        mListener.goToAclValidation();
    }

    @Override
    public void goToAclValidation() {
        mListener.goToAclValidation();
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

        void goToAclValidation();
    }
}
