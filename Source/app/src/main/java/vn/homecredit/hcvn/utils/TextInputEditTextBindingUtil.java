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

import vn.homecredit.hcvn.rules.base.StringRule;

public class TextInputEditTextBindingUtil {
    @BindingAdapter({"app:validation"})
    public static void setErrorEnable(TextInputLayout textInputLayout, StringRule stringRule) {
        textInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                boolean isValidString = stringRule.stringIsValid(textInputLayout.getEditText().getText());

                textInputLayout.setErrorEnabled(!isValidString);
                if (isValidString) {
                    textInputLayout.setError(null);
                } else {
                    textInputLayout.setError(stringRule.getErrorMessage());
                }
            }
        });

        boolean isValidString = stringRule.stringIsValid(textInputLayout.getEditText().getText());
        textInputLayout
                .setErrorEnabled(!isValidString);
        if (isValidString) {
            textInputLayout.setError(null);
        } else {
            textInputLayout.setError(stringRule.getErrorMessage());
        }
    }
}
