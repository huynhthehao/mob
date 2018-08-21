/*
 * BaseActivity.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/12/18 2:59 PM
 */

package vn.homecredit.hcvn.ui.base;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.functions.Consumer;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.message.base.BaseMessage;
import vn.homecredit.hcvn.helpers.UiHelper;
import vn.homecredit.hcvn.service.tracking.TrackingService;
import vn.homecredit.hcvn.ui.welcome.WelcomeActivity;
import vn.homecredit.hcvn.utils.CommonUtils;
import vn.homecredit.hcvn.utils.NetworkUtils;

import static java.lang.Boolean.TRUE;
import static vn.homecredit.hcvn.HCVNApp.getContext;

public abstract class BaseActivity<T extends ViewDataBinding, V extends BaseViewModel> extends AppCompatActivity
        implements BaseFragment.Callback {

    // TODO
    // this can probably depend on isLoading variable of BaseViewModel,
    // since its going to be common for all the activities
    protected ProgressDialog mProgressDialog;
    private T mViewDataBinding;
    private V mViewModel;

    @Inject
    TrackingService trackService;

    protected boolean getLoadingEnable(){
        return true;
    }

    /**
     * Override for set binding variable
     * @return variable id
     */
    public abstract int getBindingVariable();

    /**
     * @return layout resource id
     */
    public abstract
    @LayoutRes
    int getLayoutId();

    public abstract V getViewModel();

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection();
        super.onCreate(savedInstanceState);
        performDataBinding();
        this.init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (trackService != null){
            trackService.sendView(this);
        }
    }

    protected void init() {
        getViewModel().setNavigator(this);
        getViewModel().init();
    }

    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    public void openActivityOnTokenExpire() {
        startActivity(WelcomeActivity.newIntent(this));
        finish();
    }

    public void performDependencyInjection() {
        AndroidInjection.inject(this);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    public void showLoading() {
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(this);
    }

    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        this.mViewModel = mViewModel == null ? getViewModel() : mViewModel;
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.executePendingBindings();
        mViewModel.getIsLoading().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (((ObservableBoolean) sender).get()) {
                    if(getLoadingEnable())
                        showLoading();
                } else {
                    hideLoading();
                }
            }
        });

        bindModelMessageDialog();
        bindModelMessageByIdDialog();
        bindModelConfirmDialog();
        bindModelErrorAuthenticate();
    }

    private void bindModelErrorAuthenticate() {
        getViewModel().getModelReLogin().observe(this, o -> {
            if (o != null && o == TRUE) {
                UiHelper.showConfirmMessageNoCancel(this,null, getString(R.string.token_expired), aBoolean -> {
                    if (aBoolean != null && aBoolean == TRUE) {
                        startWelcomeAfterSessionExpired();
                    }
                });
            }
        });
    }


    public void showMessage(String message) {
        UiHelper.showMessage(this, message);
    }

    public void showMessage(Integer messageId) {
        String message = getResources().getString(messageId);
        showMessage(message);
    }


    public void showConfirmMessage(String title, String message, final Consumer<Boolean> onCompleted) {
        UiHelper.showConfirmMessage(this,title, message,onCompleted);
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

    private void startWelcomeAfterSessionExpired() {
        Intent intent = WelcomeActivity.newIntent(getContext());
        intent.putExtra(WelcomeActivity.IS_FORCE_LOGOUT, true);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}