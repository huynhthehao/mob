/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/13/18 11:43 AM, by hien.nguyenm
 */

package vn.homecredit.hcvn.utils;

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
}
