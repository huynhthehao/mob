
package vn.homecredit.hcvn.data.model.api.contract;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContractDataResp {

    @SerializedName("Contracts")
    @Expose
    private List<HcContract> contracts = null;

    @SerializedName("MasterContracts")
    @Expose
    private List<MasterContract> masterContracts = null;

    public List<HcContract> getContracts() {
        return contracts;
    }

    public void setContracts(List<HcContract> contracts) {
        this.contracts = contracts;
    }

    public List<MasterContract> getMasterContracts() {
        return masterContracts;
    }

    public void setMasterContracts(List<MasterContract> masterContracts) {
        this.masterContracts = masterContracts;
    }
}
