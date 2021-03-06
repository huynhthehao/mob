/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/6/18 4:57 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import vn.homecredit.hcvn.R;


public class DashBoadItemView extends LinearLayout {
    private String title;
    private int srcId;
    TextView tvBadgeNumber;

    public DashBoadItemView(Context context) {
        super(context);
        if (!isInEditMode()) {
            init(context, null);
        }
    }

    public DashBoadItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    public DashBoadItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DashBoadItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    private void init(Context context, AttributeSet attrs) {
        View.inflate(context, R.layout.item_view_dashboard, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DashboardView);
        title = typedArray.getString(R.styleable.DashboardView_text);
        int color = typedArray.getColor(R.styleable.DashboardView_color, -1);
        float size = typedArray.getDimensionPixelSize(R.styleable.DashboardView_size, -1);
        srcId = typedArray.getResourceId(R.styleable.DashboardView_src, 0);

        ((ImageView) findViewById(R.id.ivIcon)).setImageResource(srcId);
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText(title);
        if (color != -1) {
            tvTitle.setTextColor(color);
        }
        if (size != -1) {
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }

        tvBadgeNumber = findViewById(R.id.tvBadgeNumber);

        typedArray.recycle();
    }

    public void updateBadgeNumber(int count) {
        if (tvBadgeNumber == null) {
            return;
        }
        if (count == 0) {
            tvBadgeNumber.setVisibility(GONE);
            return;
        }
        tvBadgeNumber.setVisibility(VISIBLE);
        tvBadgeNumber.setText(String.valueOf(count));
    }


}
