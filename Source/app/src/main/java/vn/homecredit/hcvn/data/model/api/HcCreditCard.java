/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/6/18 4:15 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vn.homecredit.hcvn.data.model.LanguageCode;
import vn.homecredit.hcvn.helpers.prefs.PreferencesHelper;

public class HcCreditCard {

    @SerializedName("card_number")
    @Expose
    public String cardNumber;

    @SerializedName("card_type")
    @Expose
    public String cardType;

    @SerializedName("card_status")
    @Expose
    public Integer status;

    @SerializedName("status_text_en")
    @Expose
    public String statusDescriptionEn;

    @SerializedName("status_text_vn")
    @Expose
    public String statusDescriptionVn;

    @SerializedName("credit_limit")
    @Expose
    public long limit;

    @SerializedName("available_balance")
    @Expose
    public long availableBalance;

    @SerializedName("hold_balance")
    @Expose
    public long holdBalance;

    @SerializedName("total_outstanding_debt")
    @Expose
    public long totalOutStandingDebt;

    public String getStatusText(PreferencesHelper preferencesHelper){
        String currentLangeCode = preferencesHelper.getLanguageCode();
        if(currentLangeCode.equals(LanguageCode.ENGLISH))
            return statusDescriptionEn;

        return statusDescriptionVn;
    }
}
