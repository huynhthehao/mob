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
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import vn.homecredit.hcvn.R;

public class MenuRowItem extends LinearLayout {

    private static final int DEFAULT_IMAGE_SRC = R.drawable.ic_launcher_foreground;

    private String mTitle;
    private int mSrc;

    private TextView mTitleView;
    private ImageView mImageView;

    public MenuRowItem(Context context) {
        super(context);
        setupView(null);
    }

    public MenuRowItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setupView(attrs);
    }

    public MenuRowItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupView(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MenuRowItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setupView(attrs);
    }

    protected void setupView(@Nullable AttributeSet attrs) {

        getAttributeValue(attrs);

        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // inflate whatever layout xml has your common xml
        inflater.inflate(R.layout.widget_menu_item, this);
    }

    protected void getAttributeValue(@Nullable AttributeSet attrs) {
        int[] set = {
                android.R.attr.src, // idx 0
                android.R.attr.text        // idx 1
        };
        TypedArray array = getContext().getTheme().obtainStyledAttributes(attrs, set, 0, 0);

        mSrc = array.getResourceId(0, DEFAULT_IMAGE_SRC);
        mTitle = array.getString(1);

        array.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mTitleView = (TextView) findViewById(R.id.menuTitleView);
        mImageView = (ImageView) findViewById(R.id.imageView);

        mTitleView.setText(mTitle);
        mImageView.setImageResource(mSrc);
    }
}
