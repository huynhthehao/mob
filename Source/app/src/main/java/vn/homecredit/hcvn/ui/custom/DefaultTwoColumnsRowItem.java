/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 8/3/18 1:06 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.helpers.UiHelper;

public class DefaultTwoColumnsRowItem extends LinearLayout {
    private String leftDisplayText = "";
    private TextView textViewRight;
    private int textColor = 0;

    public DefaultTwoColumnsRowItem(Context context) {
        super(context);
        init(context, null);
    }

    public DefaultTwoColumnsRowItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DefaultTwoColumnsRowItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DefaultTwoColumnsRowItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    @BindingAdapter("textValue")
    public static void setTextValue(DefaultTwoColumnsRowItem rowItem, String value) {
        if (value != null) {
            rowItem.textViewRight.setText(value);
        }
    }

    @BindingAdapter("textColor")
    public static void setTextColorValue(DefaultTwoColumnsRowItem rowItem, int value) {
        rowItem.textViewRight.setTextColor(value);
    }

    @BindingAdapter("textValue")
    public static void setTextValue(DefaultTwoColumnsRowItem rowItem, int value) {
        rowItem.textViewRight.setText(String.format("%d", value));
    }

    private void init(Context context, AttributeSet attrs) {
        final int totalWeightSum = 100;

        View.inflate(context, R.layout.item_view_horizontal_master_detail, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DefaultTwoColumnsRowItem);

        leftDisplayText = typedArray.getString(R.styleable.DefaultTwoColumnsRowItem_leftText);
        String rightDisplayText = typedArray.getString(R.styleable.DefaultTwoColumnsRowItem_rightText);

        int verticalPadding = typedArray.getDimensionPixelOffset(R.styleable.DefaultTwoColumnsRowItem_verticalPadding, 10);
        int leftLayoutPercent = typedArray.getInteger(R.styleable.DefaultTwoColumnsRowItem_leftColumnWeightPercent, 40);
        int rightLayoutPercent = totalWeightSum - leftLayoutPercent;

        TextView textViewLeft = findViewById(R.id.tvTitle);
        textViewRight = findViewById(R.id.tvValue);

        textViewLeft.setText(leftDisplayText);
        textViewRight.setText(rightDisplayText);

        LayoutParams leftLayout = new LayoutParams(0, LayoutParams.WRAP_CONTENT, leftLayoutPercent);
        LayoutParams rightLayout = new LayoutParams(0, LayoutParams.WRAP_CONTENT, rightLayoutPercent);

        this.setWeightSum(100);
        this.setPadding(0,verticalPadding,0,verticalPadding);

        textViewLeft.setLayoutParams(leftLayout);
        textViewRight.setLayoutParams(rightLayout);
        textViewRight.setGravity(Gravity.END);

        if (textColor != 0) {
            textViewRight.setTextColor(textColor);
        }

        typedArray.recycle();
    }
}
