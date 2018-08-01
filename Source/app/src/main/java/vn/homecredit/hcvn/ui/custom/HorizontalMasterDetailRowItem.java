package vn.homecredit.hcvn.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import vn.homecredit.hcvn.R;

public class HorizontalMasterDetailRowItem extends LinearLayout {
    private String title = "";
    private int srcId = 0;
    private TextView tvValue;

    public HorizontalMasterDetailRowItem(Context context) {
        super(context);
        init(context, null);
    }

    public HorizontalMasterDetailRowItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public HorizontalMasterDetailRowItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public HorizontalMasterDetailRowItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    @BindingAdapter("textValue")
    public static void setTextValue(HorizontalMasterDetailRowItem horizontalMasterDetailRowItem, String value) {
        if (value != null) {
            horizontalMasterDetailRowItem.tvValue.setText(value);
        }
    }

    @BindingAdapter("textValue")
    public static void setTextValue(HorizontalMasterDetailRowItem horizontalMasterDetailRowItem, int value) {
        horizontalMasterDetailRowItem.tvValue.setText(String.format("%d", value));
    }

    private void init(Context context, AttributeSet attrs) {
        View.inflate(context, R.layout.item_view_horizontal_master_detail, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HorizontalMasterDetailRowItem);
        title = typedArray.getString(R.styleable.HorizontalMasterDetailRowItem_textTitle);
        srcId = typedArray.getResourceId(R.styleable.HorizontalMasterDetailRowItem_src, 0);

        ((TextView) findViewById(R.id.tvTitle)).setText(title);
        tvValue = findViewById(R.id.tvValue);

        typedArray.recycle();
    }
}
