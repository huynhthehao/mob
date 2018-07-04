/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/3/18 11:24 AM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.utils;

import android.databinding.BindingAdapter;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import vn.homecredit.hcvn.rules.base.StringRule;

public class TextInputEditTextBindingUtil {
    @BindingAdapter({"app:validation"})
    public static void setErrorEnable(TextInputLayout textInputLayout, StringRule stringRule) {

        textInputLayout.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    UpdateErrorStatus(textInputLayout, stringRule, false);
                }
            }
        });

        UpdateErrorStatus(textInputLayout, stringRule, true);
    }

    private static void UpdateErrorStatus(TextInputLayout textInputLayout, StringRule stringRule, boolean skipError) {
        String text = textInputLayout.getEditText().getText().toString();

        if (skipError && text.isEmpty()) {
            skipError = true;
        }

        boolean isValidString = stringRule.stringIsValid(text) || skipError;

        textInputLayout.setErrorEnabled(!isValidString);
        if (isValidString) {
            textInputLayout.setError(null);
        } else {
            textInputLayout.setError(stringRule.getErrorMessage());
        }
    }
}
