/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 8/7/18 3:46 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.data.model.api.creditcard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import java.util.Date;

import vn.homecredit.hcvn.helpers.UiHelper;
import vn.homecredit.hcvn.utils.DateUtils;

@Parcel
public class Transaction {

    public static final String CreditDirection = "credit";

    @SerializedName("amount")
    @Expose
    public int amount;

    @SerializedName("date")
    @Expose
    public String transactionDate;

    @SerializedName("PostingDate")
    @Expose
    public String postingDate;

    @SerializedName("description")
    @Expose
    public String description;

    @SerializedName("fullDescription")
    @Expose
    public String fullDescription;

    @SerializedName("direction")
    @Expose
    public String direction;

    @SerializedName("isCancelled")
    @Expose
    public boolean isCancelled;

    @SerializedName("isHold")
    @Expose
    public boolean isHold;

    public String getAmountDisplay() {
        String headSign = direction.equalsIgnoreCase(CreditDirection) ? "+" : "-";
        return headSign + UiHelper.getCurrencyFormat(amount);
    }

    public String getTransactionDate(){
        return DateUtils.simplifyDateString(transactionDate);
    }

    public String getPostingDate(){
        return DateUtils.simplifyDateString(postingDate);
    }
}