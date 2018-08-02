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

import io.reactivex.functions.Consumer;
import vn.homecredit.hcvn.R;

public class UiHelper {

    public static int dpToPx(Context context, int dp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }

    public static float getDpValue(Context context, int dp){
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

    public static void showConfirmMessage(Context context, String title, String message, final Consumer<Boolean> onCompleted) {
        MaterialDialog dialog = new MaterialDialog.Builder(context)
                .title(title)
                .content(message)
                .positiveText(R.string.ok)
                .negativeColor(context.getResources().getColor(R.color.my_secondary_text))
                .negativeText(R.string.cancel)
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
                }).build();

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
