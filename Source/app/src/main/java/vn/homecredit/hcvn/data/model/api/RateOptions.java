/*
 * RateOptions.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/12/18 1:33 PM
 */

package vn.homecredit.hcvn.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RateOptions {

    @SerializedName("isEnabled")
    @Expose
    private Boolean isEnabled;
    @SerializedName("rateLaunch")
    @Expose
    private Integer rateLaunch;
    @SerializedName("rateInstallment")
    @Expose
    private Integer rateInstallment;
    @SerializedName("ratePayment")
    @Expose
    private Integer ratePayment;
    @SerializedName("rateEachVersion")
    @Expose
    private Boolean rateEachVersion;

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Integer getRateLaunch() {
        return rateLaunch;
    }

    public void setRateLaunch(Integer rateLaunch) {
        this.rateLaunch = rateLaunch;
    }

    public Integer getRateInstallment() {
        return rateInstallment;
    }

    public void setRateInstallment(Integer rateInstallment) {
        this.rateInstallment = rateInstallment;
    }

    public Integer getRatePayment() {
        return ratePayment;
    }

    public void setRatePayment(Integer ratePayment) {
        this.ratePayment = ratePayment;
    }

    public Boolean getRateEachVersion() {
        return rateEachVersion;
    }

    public void setRateEachVersion(Boolean rateEachVersion) {
        this.rateEachVersion = rateEachVersion;
    }

}