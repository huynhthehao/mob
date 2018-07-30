package vn.homecredit.hcvn.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import vn.homecredit.hcvn.R;

public class ContractSummaryItem extends RelativeLayout {
    private static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";
    private String title;
    private String content;
    private int color;

    private TextView tvTitle;
    private TextView tvContent;


    public ContractSummaryItem(Context context) {
        super(context);
        init(context, null);
    }

    public ContractSummaryItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ContractSummaryItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ContractSummaryItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View.inflate(context, R.layout.item_summary_contract, this);
        tvTitle = findViewById(R.id.tvTitle);
        tvContent = findViewById(R.id.tvContent);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ContractSummaryItemView);
        setTitle(typedArray.getString(R.styleable.ContractSummaryItemView_title));
        setContent(typedArray.getString(R.styleable.ContractSummaryItemView_content));
        int textColor = attrs.getAttributeIntValue(ANDROID_SCHEMA, "textColor", -1);
        if (textColor != -1) {
            tvContent.setTextColor(textColor);
        }
        int textStyle = attrs.getAttributeIntValue(ANDROID_SCHEMA, "textStyle", Typeface.NORMAL);
        switch (textStyle) {
            case Typeface.BOLD:
                tvContent.setTypeface(null, Typeface.BOLD);
                invalidate();
                break;


        }
        typedArray.recycle();
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        tvTitle.setText(title);
        invalidate();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        tvContent.setText(content);
        invalidate();
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        invalidate();
    }
}
