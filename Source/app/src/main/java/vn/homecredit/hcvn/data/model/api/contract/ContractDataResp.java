package vn.homecredit.hcvn.data.model.api.contract;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContractDataResp {
    @SerializedName("Contracts")
    @Expose
    private List<HcContract> contracts;

    @SerializedName("MasterContracts")
    @Expose
    private List<HcContract> masterContracts;


    public List<HcContract> getContracts() {
        return contracts;
    }

    public void setContracts(List<HcContract> contracts) {
        this.contracts = contracts;
    }

    public List<HcContract> getMasterContracts() {
        return masterContracts;
    }

    public void setMasterContracts(List<HcContract> masterContracts) {
        this.masterContracts = masterContracts;
    }
}


