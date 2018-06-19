/*
 * BaseStatefulActivity.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/19/18 3:11 PM
 */

package vn.homecredit.hcvn.ui.base;

import android.databinding.ViewDataBinding;
import android.support.annotation.StringRes;
import android.view.View;

import com.gturedi.views.StatefulLayout;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.control.MyStatefulLayout;

public abstract class BaseStatefulActivity<T extends ViewDataBinding, V extends BaseViewModel> extends BaseActivity<T,V> {
    public abstract MyStatefulLayout getStatefulLayout();

    @Override
    public void showLoading() {
        getStatefulLayout().showLoading();
    }

    @Override
    public void hideLoading() {
        getStatefulLayout().showContent();
    }

    public void showError(View.OnClickListener clickListener)
    {
        getStatefulLayout().showError(clickListener);
    }

    public void showOffline(View.OnClickListener clickListener)
    {
        getStatefulLayout().showOffline("No internet connection", clickListener);
    }
}
