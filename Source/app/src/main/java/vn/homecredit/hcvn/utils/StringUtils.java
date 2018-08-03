/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/13/18 11:43 AM, by hien.nguyenm
 */

package vn.homecredit.hcvn.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class StringUtils {

    public static boolean isNullOrWhiteSpace(CharSequence input){
        if(input == null)
            return true;
        String value = input.toString().trim();

        return value.length() < 1;
    }

    public static boolean isNullOrEmpty(CharSequence input){
        return input == null || input.length() == 0;
    }

    public static String getCurrencyMaskValue(Number inputNumber) {
        NumberFormat formatter = new DecimalFormat("#,###");
        String formattedNumber = formatter.format(inputNumber);
        return formattedNumber;
    }
}
