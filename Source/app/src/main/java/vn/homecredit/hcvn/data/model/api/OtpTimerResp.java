/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/3/18 4:10 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpTimerResp {
    public static final int RESPONSE_CODE_SUCCESS = 0;
    public static final int RESPONSE_CODE_IN_EFFECT = 64;

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

    public boolean isVerified() {
        return responseCode == RESPONSE_CODE_SUCCESS
                || responseCode == RESPONSE_CODE_IN_EFFECT;
    }

    @Override
    public String toString() {
        return "OtpTimerResp{" +
                "data=" + data +
                ", responseCode=" + responseCode +
                ", responseMessage='" + responseMessage + '\'' +
                '}';
    }
}
