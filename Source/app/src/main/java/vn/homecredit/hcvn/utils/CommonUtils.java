/*
 * CommonUtils.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/12/18 3:04 PM
 */

package vn.homecredit.hcvn.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import vn.homecredit.hcvn.R;

public final class CommonUtils {
    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    /**
     * This method will generate a default formatter for number so that we can reuse it for many purposes
     * such as add currency symbols, modify small things in formatter...
     * @return
     */
    public static NumberFormat getDefaultNumberFormmater() {
        NumberFormat formatter = new DecimalFormat("#,###");
        return formatter;
    }
}