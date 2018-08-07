package vn.homecredit.hcvn.data.model.api.contract;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vn.homecredit.hcvn.data.model.api.base.BaseApiResponse;

public class MasterContractVerifyResp extends BaseApiResponse {

    @SerializedName("data")
    @Expose
    MasterContractVerifyDataResp masterContractVerifyDataResp;

    public MasterContractVerifyDataResp getMasterContractVerifyDataResp() {
        return masterContractVerifyDataResp;
    }

    public void setMasterContractVerifyDataResp(MasterContractVerifyDataResp masterContractVerifyDataResp) {
        this.masterContractVerifyDataResp = masterContractVerifyDataResp;
    }
}
