/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/3/18 4:10 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpTimerResp {
    @SerializedName("data")
    @Expose
    private OtpTimerRespData data;
    @SerializedName("response_code")
    @Expose
    private Integer responseCode;
    @SerializedName("response_message")
    @Expose
    private String responseMessage;

    public OtpTimerRespData getData() {
        return data;
    }

    public void setData(OtpTimerRespData data) {
        this.data = data;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public class OtpTimerRespData
    {
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
    }
}
