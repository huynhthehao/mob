
package vn.homecredit.hcvn.data.model.api.contract;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContractResp {

    @SerializedName("data")
    @Expose
    private ContractDataResp data;
    @SerializedName("response_code")
    @Expose
    private Integer responseCode;

    public ContractDataResp getData() {
        return data;
    }

    public void setData(ContractDataResp data) {
        this.data = data;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

}
