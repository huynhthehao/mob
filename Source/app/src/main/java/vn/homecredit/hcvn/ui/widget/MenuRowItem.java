/*
 * MenuRowItem.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/20/18 11:00 AM
 */

package vn.homecredit.hcvn.ui.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import vn.homecredit.hcvn.R;

public class MenuRowItem extends LinearLayout {
    public MenuRowItem(Context context) {
        super(context);
        setupView();
    }

    public MenuRowItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setupView();
    }

    public MenuRowItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MenuRowItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setupView();
    }

    protected void setupView() {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // inflate whatever layout xml has your common xml
        inflater.inflate(R.layout.widget_menu_item, this);
    }
}
