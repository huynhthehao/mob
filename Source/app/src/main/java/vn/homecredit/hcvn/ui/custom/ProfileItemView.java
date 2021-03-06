package vn.homecredit.hcvn.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import vn.homecredit.hcvn.R;

public class ProfileItemView extends LinearLayout {
    private String title = "";
    private int srcId = 0;
    private TextView tvValue;

    public ProfileItemView(Context context) {
        super(context);
        init(context, null);
    }

    public ProfileItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ProfileItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ProfileItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    @BindingAdapter("textValue")
    public static void setTextValue(ProfileItemView profileItemView, String value) {
        if (value != null) {
            profileItemView.tvValue.setText(value);
        }
    }

    private void init(Context context, AttributeSet attrs) {
        View.inflate(context, R.layout.item_view_profile, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProfileItemView);
        title = typedArray.getString(R.styleable.ProfileItemView_textTitle);
        srcId = typedArray.getResourceId(R.styleable.ProfileItemView_src, 0);

        ((ImageView) findViewById(R.id.ivIcon)).setImageResource(srcId);
        ((TextView) findViewById(R.id.tvTitle)).setText(title);
        tvValue = findViewById(R.id.tvValue);

        typedArray.recycle();
    }
}
