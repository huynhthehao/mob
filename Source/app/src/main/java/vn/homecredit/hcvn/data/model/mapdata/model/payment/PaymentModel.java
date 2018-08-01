package vn.homecredit.hcvn.data.model.mapdata.model.payment;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PaymentModel implements Serializable {
    @SerializedName("data")
    @Expose
    private List<PaymentData> data = null;
    @SerializedName("response_code")
    @Expose
    private Integer responseCode;
    @SerializedName("response_message")
    @Expose
    private String responseMessage;

    public List<PaymentData> getData() {
        return data;
    }

    public void setData(List<PaymentData> data) {
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

}