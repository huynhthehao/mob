
package vn.homecredit.hcvn.data.model.api.contract;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vn.homecredit.hcvn.data.model.api.base.BaseApiResponse;

public class ContractResp extends BaseApiResponse {

    @SerializedName("data")
    @Expose
    private ContractDataResp data;


    public ContractDataResp getData() {
        return data;
    }

    public void setData(ContractDataResp data) {
        this.data = data;
    }

}
