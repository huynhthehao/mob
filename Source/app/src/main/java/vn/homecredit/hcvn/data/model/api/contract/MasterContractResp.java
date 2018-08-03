package vn.homecredit.hcvn.data.model.api.contract;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vn.homecredit.hcvn.data.model.api.base.BaseApiResponse;

public class MasterContractResp extends BaseApiResponse {
    @SerializedName("data")
    @Expose
    private MasterContract masterContract;

    public MasterContract getMasterContract() {
        return masterContract;
    }

    public void setMasterContract(MasterContract masterContract) {
        this.masterContract = masterContract;
    }
}
