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
import android.view.View;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.ui.custom.MyStatefulLayout;

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
        getStatefulLayout().showOffline(R.string.unable_to_connect_message, clickListener);
    }
}
