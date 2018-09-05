package vn.homecredit.hcvn.ui.momo;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

import vn.homecredit.hcvn.utils.CommonUtils;

public final class NumberFormatTextWatcher implements TextWatcher {
    private final EditText et;
    private final DecimalFormat df;
    private final NumberFormatTextWatcher.StringAsNumberChangeListener listener;
    private String value;
    private boolean isFractionEnd;

    public NumberFormatTextWatcher(@NotNull EditText et, @NotNull DecimalFormat df, @Nullable NumberFormatTextWatcher.StringAsNumberChangeListener listener) {
        this.et = et;
        this.df = df;
        this.listener = listener;
    }

    public NumberFormatTextWatcher(@NotNull EditText et, @NotNull String numberFormatPattern, @Nullable NumberFormatTextWatcher.StringAsNumberChangeListener listener) {
        this(et, new DecimalFormat(numberFormatPattern), listener);
    }

    public NumberFormatTextWatcher(@NotNull EditText et, @Nullable NumberFormatTextWatcher.StringAsNumberChangeListener listener) {
        this(et, (DecimalFormat) CommonUtils.getDefaultNumberFormmater(), listener);
    }

    public void beforeTextChanged(@Nullable CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged(@Nullable CharSequence s, int start, int before, int count) {
        if (s != null) {
            DecimalFormatSymbols symbols = df.getDecimalFormatSymbols();
            int index = s.toString().indexOf(symbols.getDecimalSeparator());
            this.isFractionEnd = index > 0 && index == s.length() - 1;
        }
    }

    public void afterTextChanged(@Nullable Editable s) {
        et.removeTextChangedListener(this);

        try {
            int initlen = et.getText().length();
            DecimalFormatSymbols symbols = df.getDecimalFormatSymbols();
            String v = s.toString()
                    .replace(symbols.getCurrencySymbol(), "")
                    .replace(symbols.getGroupingSeparator() + "", "");
            // Assign candidate string value before parsing number to keep original value
            value = TextUtils.isEmpty(v) ? "0" : v;
            Number n = this.df.parse(v);
            // Assign string value after parsing number successful
            value = n.toString();
            int cp = et.getSelectionStart();
            // Add decimal separator at the end of text because it will be disappear after formatting
            String formattedDecimalText = df.format(n) + (isFractionEnd ? df.getDecimalFormatSymbols().getDecimalSeparator() : "");
            et.setText(formattedDecimalText);
            int endlen = et.getText().length();
            int sel = cp + (endlen - initlen);
            if (sel > 0 && sel <= et.getText().length()) {
                et.setSelection(sel);
            } else {
                et.setSelection(et.getText().length() - 1);
            }
        } catch (NumberFormatException ignored) {
        } catch (ParseException ignored) {
        }

        et.addTextChangedListener(this);
        if (listener != null) {
            listener.onChange(value);
        }
    }

    public interface StringAsNumberChangeListener {
        void onChange(@NotNull String value);
    }
}