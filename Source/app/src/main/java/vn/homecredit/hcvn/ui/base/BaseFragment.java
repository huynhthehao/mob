/*
 * BaseFragment.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/12/18 3:00 PM
 */

package vn.homecredit.hcvn.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import io.reactivex.functions.Consumer;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.message.base.BaseMessage;
import vn.homecredit.hcvn.helpers.UiHelper;
import vn.homecredit.hcvn.utils.CommonUtils;
import vn.homecredit.hcvn.ui.welcome.WelcomeActivity;
import vn.homecredit.hcvn.service.tracking.TrackingService;

import static java.lang.Boolean.TRUE;

public abstract class BaseFragment<T extends ViewDataBinding, V extends BaseViewModel> extends Fragment {

    private BaseActivity mActivity;
    private View mRootView;
    private T mViewDataBinding;
    private V mViewModel;
    protected ProgressDialog mProgressDialog;
    @Inject
    TrackingService trackService;

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
        bindModelErrorAuthenticate();
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
        bindModelConfirmDialog();
        bindModelMessageDialog();
        bindModelMessageByIdDialog();
    }

    private void bindModelMessageDialog() {
        getViewModel().getMessageData().observe(this, o -> {
            if (o != null && o instanceof String) {
                showMessage((String) o);
            }
        });
    }

    private void bindModelMessageByIdDialog() {
        getViewModel().getMessageIdData().observe(this, o -> {
            if (o != null && o instanceof Integer) {
                showMessage((Integer) o);
            }
        });
    }

    private void bindModelConfirmDialog() {
        getViewModel().getConfirmMessageData().observe(this, o -> {
            if (o != null && o instanceof BaseMessage) {
                BaseMessage messageData =  (BaseMessage) o;
                showConfirmMessage(messageData.getTitle(),messageData.getContent(),messageData.getOnCompleted());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (trackService != null) {
            trackService.sendView(this);
        }
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
        UiHelper.showMessage(this.getContext(), message);
    }

    public void showConfirmMessage(Integer titleId, Integer messageId, final Consumer<Boolean> onCompleted) {
        String title = getResources().getString(titleId);
        String message = getResources().getString(messageId);

        showConfirmMessage(title, message, onCompleted);
    }

    private void bindModelErrorAuthenticate() {
        getViewModel().getModelReLogin().observe(this, o -> {
            if (o != null && o == TRUE) {
                showConfirmMessage(null, getString(R.string.token_expired), aBoolean -> {
                    if (aBoolean != null && aBoolean == TRUE) {
                        startWelcomeAfterSessionExpired();
                    }
                });
            }
        });
    }

    public void showConfirmMessage(String title, String message, final Consumer<Boolean> onCompleted) {
        UiHelper.showConfirmMessage(this.getContext(), title, message, onCompleted);
    }

    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    public void showLoading() {
        mProgressDialog = CommonUtils.showLoadingDialog(getContext());
    }

    private void startWelcomeAfterSessionExpired() {
        Intent intent = WelcomeActivity.newIntent(getContext());
        intent.putExtra(WelcomeActivity.IS_FORCE_LOGOUT, true);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}