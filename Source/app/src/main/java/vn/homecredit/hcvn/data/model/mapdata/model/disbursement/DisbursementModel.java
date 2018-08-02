package vn.homecredit.hcvn.data.model.mapdata.model.disbursement;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DisbursementModel implements Serializable {

    @SerializedName("data")
    @Expose
    private List<DisbursementData> data = null;
    @SerializedName("response_code")
    @Expose
    private Integer responseCode;
    private final static long serialVersionUID = -4559715285299651559L;

    public List<DisbursementData> getData() {
        return data;
    }

    public void setData(List<DisbursementData> data) {
        this.data = data;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

}