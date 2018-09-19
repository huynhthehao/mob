package vn.homecredit.hcvn.ui.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.StringRes;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import vn.homecredit.hcvn.R;

public class HcLoanSeekBar extends LinearLayout implements SeekBar.OnSeekBarChangeListener {
    public static final int ABOVE = 0;
    public static final int BELOW = 1;

    private OnValueChangeListener onValueChangeListener;

    private View belowTitleContainer;
    private TextView tvAboveMinTitle;
    private TextView tvAboveMaxTitle;
    private TextView tvBelowMinTitle;
    private TextView tvBelowMaxTitle;
    private TextView tvValueText;
    private SeekBar seekBar;

    private String unit = "";
    private int min;
    private int max;
    private int step = 1;

    public HcLoanSeekBar(Context context) {
        this(context, null);
    }

    public HcLoanSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.seekBarStyle);
    }

    public HcLoanSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
        loadAttributes(context, attrs);
    }

    private void initialize(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_loan_seekbar, this);
        setOrientation(VERTICAL);

        tvAboveMinTitle = findViewById(R.id.above_min_title);
        tvAboveMaxTitle = findViewById(R.id.above_max_title);
        tvBelowMinTitle = findViewById(R.id.below_min_title);
        tvBelowMaxTitle = findViewById(R.id.below_max_title);
        belowTitleContainer = findViewById(R.id.below_title_container);
        seekBar = findViewById(R.id.seek_bar);
        tvValueText = findViewById(R.id.value_text);

        seekBar.setOnSeekBarChangeListener(this);
    }

    private void loadAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HcLoanSeekBar);
        for (int i = 0; i < a.getIndexCount(); i++) {
            int index = a.getIndex(i);
            if (!a.hasValue(index))
                continue;
            switch (index) {
                case R.styleable.HcLoanSeekBar_unit:
                    setUnit(a.getString(index));
                    break;
                case R.styleable.HcLoanSeekBar_loan_color:
                    // TODO: Define value title color
                    break;
                case R.styleable.HcLoanSeekBar_min:
                    setMin(a.getInt(index, min));
                    break;
                case R.styleable.HcLoanSeekBar_max:
                    setMax(a.getInt(index, max));
                    break;
                case R.styleable.HcLoanSeekBar_step:
                    setStep(a.getInt(index, step));
                    break;
                case R.styleable.HcLoanSeekBar_title_position:
                    setTitlePosition(a.getInt(index, BELOW));
                    break;
                case R.styleable.HcLoanSeekBar_title_color:
                    setTitleColor(a.getColorStateList(index));
                    break;
                case R.styleable.HcLoanSeekBar_thumbTint:
                    setThumbTint(a.getColorStateList(index));
                    break;
                case R.styleable.HcLoanSeekBar_progressTint:
                    setProgressTint(a.getColorStateList(index));
                    break;
            }
        }
        a.recycle();
    }

    public void setTitlePosition(int position) {
        if (position == ABOVE) {
            belowTitleContainer.setVisibility(GONE);
            tvAboveMaxTitle.setVisibility(VISIBLE);
            tvAboveMinTitle.setVisibility(VISIBLE);
        } else {
            belowTitleContainer.setVisibility(VISIBLE);
            tvAboveMaxTitle.setVisibility(GONE);
            tvAboveMinTitle.setVisibility(GONE);
        }
    }

    public void setTitleColor(ColorStateList colorStateList) {
        tvBelowMinTitle.setTextColor(colorStateList);
        tvBelowMaxTitle.setTextColor(colorStateList);
        tvAboveMaxTitle.setTextColor(colorStateList);
        tvAboveMinTitle.setTextColor(colorStateList);
    }

    public void setUnit(String unit) {
        this.unit = unit;
        updateUnit();
    }

    public void setUnit(@StringRes int unitRes) {
        unit = getResources().getString(unitRes);
        updateUnit();
    }

    public void setMin(int min) {
        if (this.min == min)
            return;
        this.min = min;
        updateMinMax();
    }

    public void setMax(int max) {
        if (this.max == max)
            return;
        this.max = max;
        updateMinMax();
    }

    public void setStep(int step) {
        if (this.step == step)
            return;
        this.step = step;
        updateMinMax();
    }

    public void setThumbTint(ColorStateList colorStateList) {
        Drawable thumb = seekBar.getThumb().getConstantState().newDrawable();
        DrawableCompat.setTintList(thumb, colorStateList);
        seekBar.setThumb(thumb);
//        seekBar.getThumb().setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
    }

    public void setProgressTint(ColorStateList colorStateList) {
        Drawable progressDrawable = seekBar.getProgressDrawable().getConstantState().newDrawable();
        DrawableCompat.setTintList(progressDrawable, colorStateList);
        seekBar.setProgressDrawable(progressDrawable);
    }

    public void setValueText(String valueTitle) {
        tvValueText.setText(valueTitle);
    }

    public void setOnValueChangeListener(OnValueChangeListener listener) {
        this.onValueChangeListener = listener;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int value = step * progress + min;
        if (value > max)
            value = max;
        setValueText(String.format("%d %s", value, unit));
        if (onValueChangeListener != null)
            onValueChangeListener.onValueChange(this, value);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    private void updateMinMax() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
        } else {
            // TODO: May need to check this again. Temporary use 0 for min seekbar value
//            seekBar.setMin(min);
        }
        seekBar.setMax((max - min) / step);
        updateUnit();
    }

    private void updateUnit() {
        String minTitle = String.format("%d %s", min, unit);
        String maxTitle = String.format("%d %s", max, unit);
        tvAboveMinTitle.setText(minTitle);
        tvBelowMinTitle.setText(minTitle);
        tvAboveMaxTitle.setText(maxTitle);
        tvBelowMaxTitle.setText(maxTitle);
    }

    public interface OnValueChangeListener {
        void onValueChange(HcLoanSeekBar seekBar, int value);
    }
}
