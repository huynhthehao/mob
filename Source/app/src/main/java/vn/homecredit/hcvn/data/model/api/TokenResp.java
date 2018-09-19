/*
 * TokenResp.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/14/18 3:35 PM
 */

package vn.homecredit.hcvn.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenResp {
    @SerializedName("access_token")
    @Expose
    private String accessToken;

    @SerializedName("token_type")
    @Expose
    private String tokenType;

    @SerializedName("expires_in")
    @Expose
    private Integer expiresIn;

    @SerializedName("error")
    @Expose
    private String errorType;

    @SerializedName("error_description")
    @Expose
    private String errorDescription;

    @SerializedName("response_code")
    @Expose
    public int responseCode;

    @SerializedName("response_string_code")
    @Expose
    public String responseStringCode;

    @SerializedName("response_message")
    @Expose
    public String responseMessage;

    @SerializedName("response_detail_message")
    @Expose
    public String responseDetailMessage;

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseStringCode() {
        return responseStringCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public String getResponseDetailMessage() {
        return responseDetailMessage;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public String getErrorType() {
        return errorType;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public void setResponseStringCode(String responseStringCode) {
        this.responseStringCode = responseStringCode;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public void setResponseDetailMessage(String responseDetailMessage) {
        this.responseDetailMessage = responseDetailMessage;
    }
}
