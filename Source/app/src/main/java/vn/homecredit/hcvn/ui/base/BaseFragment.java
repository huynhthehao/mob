/*
 * BaseFragment.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/12/18 3:00 PM
 */

package vn.homecredit.hcvn.ui.base;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import dagger.android.support.AndroidSupportInjection;
import io.reactivex.functions.Consumer;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.utils.CommonUtils;

public abstract class BaseFragment<T extends ViewDataBinding, V extends BaseViewModel> extends Fragment {

    private BaseActivity mActivity;
    private View mRootView;
    private T mViewDataBinding;
    private V mViewModel;
    protected ProgressDialog mProgressDialog;

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    public abstract int getBindingVariable();

    /**
     * @return layout resource id
     */
    public abstract
    @LayoutRes
    int getLayoutId();

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    public abstract V getViewModel();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            this.mActivity = activity;
            activity.onFragmentAttached();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection();
        super.onCreate(savedInstanceState);
        mViewModel = getViewModel();
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        mRootView = mViewDataBinding.getRoot();
        return mRootView;
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.executePendingBindings();
        getViewModel().getIsLoading().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (((ObservableBoolean) sender).get()) {
                    showLoading();
                } else {
                    hideLoading();
                }
            }
        });
        this.init();
    }

    protected void init() {
        getViewModel().setNavigator(this);
        getViewModel().init();
    }

    public BaseActivity getBaseActivity() {
        return mActivity;
    }

    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    public void hideKeyboard() {
        if (mActivity != null) {
            mActivity.hideKeyboard();
        }
    }

    public boolean isNetworkConnected() {
        return mActivity != null && mActivity.isNetworkConnected();
    }

    public void openActivityOnTokenExpire() {
        if (mActivity != null) {
            mActivity.openActivityOnTokenExpire();
        }
    }

    private void performDependencyInjection() {
        AndroidSupportInjection.inject(this);
    }

    public interface Callback {
        void onFragmentAttached();

        void onFragmentDetached(String tag);
    }

    public void showMessage(Integer messageId) {
        String message = getResources().getString(messageId);
        showMessage(message);
    }

    public void showMessage(String message) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this.getContext())
                .title(R.string.notice)
                .content(message)
                .positiveText(R.string.ok);

        MaterialDialog dialog = builder.build();
        dialog.show();
    }

    public void showConfirmMessage(Integer titleId, Integer messageId, final Consumer<Boolean> onCompleted) {
        String title = getResources().getString(titleId);
        String message = getResources().getString(messageId);

        showConfirmMessage(title, message, onCompleted);
    }


    public void showConfirmMessage(String title, String message, final Consumer<Boolean> onCompleted) {
        new MaterialDialog.Builder(this.getActivity())
                .title(title)
                .content(message)
                .positiveText(R.string.ok)
                .negativeColor(getResources().getColor(R.color.my_secondary_text))
                .negativeText(R.string.cancel)
                .canceledOnTouchOutside(false)
                .cancelListener(dialog -> {
                    try {
                        onCompleted.accept(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
                .onPositive((dialog, which) -> {
                    try {
                        onCompleted.accept(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
                .show();
    }

    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    public void showLoading() {
        mProgressDialog = CommonUtils.showLoadingDialog(getContext());
    }
}