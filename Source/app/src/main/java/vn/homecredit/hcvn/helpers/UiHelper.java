/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/30/18 11:24 AM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.helpers;

import android.content.Context;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.internal.MDButton;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.reactivex.functions.Consumer;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.utils.StringUtils;

public class UiHelper {

    public static String getDateFormat(Date input){
        if(input == null)
            return "01/01/1990";

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(input);
    }

    public static String getCurrencyFormat(long number) {
        return getDecimalFormatCore(number);
    }

    public static String getCurrencyFormat(String number) {
        double numberValue = parseDouble(number);
        return getDecimalFormatCore(numberValue);
    }

    public static String getCurrencyFormat(double number) {
        return getDecimalFormatCore(number);
    }

    private static String getDecimalFormatCore(Object number) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH);
        DecimalFormat decimalFormat = (DecimalFormat)numberFormat;
        decimalFormat.applyPattern("#,###.00");
        String formatValue = decimalFormat.format(number);

        formatValue = formatValue.replace(".00", "")
                .replace(".0", "");

        if(StringUtils.isNullOrEmpty(formatValue))
            formatValue = "0";

        formatValue += "₫";
        return formatValue;
    }


    private static double parseDouble(String number) {
        try {
            return Double.parseDouble(number);
        } catch (Exception ex) {
            return 0;
        }
    }

    public static int dpToPx(Context context, int dp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }

    public static float getDpValue(Context context, int dp) {
        return context.getResources().getDimension(dp);
    }

    public static void showMessage(Context context, String message) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .title(R.string.notice)
                .content(message)
                .positiveText(R.string.ok);

        MaterialDialog dialog = builder.build();
        MDButton positiveButton = dialog.getActionButton(DialogAction.POSITIVE);
        positiveButton.setAllCaps(false);

        float textSize = getDpValue(context, R.dimen.medium_text);
        positiveButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

        dialog.show();
    }

    public static void showMessage(Context context, int messageId) {
        String message = context.getString(messageId);
        showMessage(context, message);
    }

    public static void showConfirmMessage(Context context, String title, String message, final Consumer<Boolean> onCompleted) {
        showConfirmMessage(context, title, message, context.getString(R.string.cancel), onCompleted);
    }

    public static void showConfirmMessageNoCancel(Context context, String title, String message, final Consumer<Boolean> onCompleted) {
        showConfirmMessage(context, title, message, null , onCompleted);
    }

    public static void showConfirmMessage(Context context, String title, String message, String negativeButtonText,  final Consumer<Boolean> onCompleted) {

        MaterialDialog.Builder dialogBuilder = new MaterialDialog.Builder(context)
                .title(title)
                .content(message)
                .positiveText(R.string.ok)
                .negativeColor(context.getResources().getColor(R.color.my_secondary_text))
                .canceledOnTouchOutside(false)
                .cancelListener(d -> {
                    try {
                        onCompleted.accept(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
                .onPositive((d, which) -> {
                    try {
                        onCompleted.accept(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        if (negativeButtonText != null) {
           dialogBuilder.negativeText(negativeButtonText);
        }

        MaterialDialog dialog = dialogBuilder.build();

        MDButton negativeButton = dialog.getActionButton(DialogAction.NEGATIVE);
        MDButton positiveButton = dialog.getActionButton(DialogAction.POSITIVE);
        negativeButton.setAllCaps(false);
        positiveButton.setAllCaps(false);

        float textSize = getDpValue(context, R.dimen.medium_text);
        positiveButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        negativeButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        negativeButton.setTypeface(null, Typeface.NORMAL);

        dialog.show();
    }
}
