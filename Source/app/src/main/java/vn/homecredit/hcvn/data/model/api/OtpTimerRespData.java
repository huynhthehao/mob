/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/5/18 2:25 PM, by Admin
 */

package vn.homecredit.hcvn.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class OtpTimerRespData {
    @SerializedName("PrimaryPhone")
    @Expose
    private String primaryPhone;

    @SerializedName("Status")
    @Expose
    private Integer status;

    @SerializedName("RemainingTime")
    @Expose
    private Integer remainingTime;
    @SerializedName("OtpLiveTime")
    @Expose
    private Integer otpLiveTime;
    @SerializedName("OtpTimeResend")
    @Expose
    private Integer otpTimeResend;
    @SerializedName("Source")
    @Expose
    private String source;

    public OtpTimerRespData() {
    }

    private OtpTimerRespData(int remainingTime, int otpLiveTime, int otpTimeResend) {
        setRemainingTime(remainingTime);
        setOtpLiveTime(otpLiveTime);
        setOtpTimeResend(otpTimeResend);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(Integer remainingTime) {
        this.remainingTime = remainingTime;
    }

    public Integer getOtpLiveTime() {
        return otpLiveTime;
    }

    public void setOtpLiveTime(Integer otpLiveTime) {
        this.otpLiveTime = otpLiveTime;
    }

    public Integer getOtpTimeResend() {
        return otpTimeResend;
    }

    public void setOtpTimeResend(Integer otpTimeResend) {
        this.otpTimeResend = otpTimeResend;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPrimaryPhone() {
        return primaryPhone;
    }

    public void setPrimaryPhone(String primaryPhone) {
        this.primaryPhone = primaryPhone;
    }

    public boolean isMissingRequiredData() {
        return remainingTime == null || otpTimeResend == null || otpLiveTime == null;
    }

    @Override
    public String toString() {
        return "OtpTimerRespData{" +
                "status=" + status +
                ", remainingTime=" + remainingTime +
                ", otpLiveTime=" + otpLiveTime +
                ", otpTimeResend=" + otpTimeResend +
                ", source='" + source + '\'' +
                '}';
    }

    public static OtpTimerRespData getDefault() {
        return Holder.defaultInstance;
    }

    private static class Holder {
        private static OtpTimerRespData defaultInstance = new OtpTimerRespData(297327, 300000, 60000);
    }
}
