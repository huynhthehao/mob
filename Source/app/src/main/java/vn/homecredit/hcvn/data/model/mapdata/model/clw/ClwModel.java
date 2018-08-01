package vn.homecredit.hcvn.data.model.mapdata.model.clw;


import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClwModel implements Serializable {
    @SerializedName("data")
    @Expose
    private List<ClwData> data = null;
    @SerializedName("response_code")
    @Expose
    private Integer responseCode;
    @SerializedName("response_message")
    @Expose
    private String responseMessage;
    private final static long serialVersionUID = 3790567901880386103L;

    public List<ClwData> getData() {
        return data;
    }

    public void setData(List<ClwData> data) {
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