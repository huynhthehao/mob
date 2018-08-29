package vn.homecredit.hcvn.mock;

import vn.homecredit.hcvn.data.model.api.contract.MasterContract;
import vn.homecredit.hcvn.data.model.api.contract.MasterContractResp;

public class MasterContractRespBuilder {

    private String contractId;
    private int statusCode;
    private boolean isMaterialPrepare = false;
    private String contractStatus = "approved";

    MasterContractRespBuilder(String contractId) {
        this.contractId = contractId;
    }

    MasterContractRespBuilder(int statusCode) {
        this.statusCode = statusCode;
    }

    public static MasterContractRespBuilder create(int statusCode) {
        return new MasterContractRespBuilder(statusCode);
    }

    public static MasterContractRespBuilder create(String contractId) {
        return new MasterContractRespBuilder(contractId);
    }

    public MasterContractRespBuilder prepared(boolean isMaterialPrepare){
        this.isMaterialPrepare = isMaterialPrepare;
        return this;
    }

    public MasterContractRespBuilder contractId(String contractId){
        this.contractId = contractId;
        return this;
    }

    public MasterContractRespBuilder status(String status){
        this.contractStatus = status;
        return this;
    }

    public MasterContractResp build() {
        MasterContractResp masterContractResp = new MasterContractResp();
        masterContractResp.setResponseCode(statusCode);
        MasterContract masterContract = new MasterContract();
        masterContract.setMaterialPrepared(isMaterialPrepare);
        masterContract.setContractStatus(contractStatus);
        masterContract.setContractNumber(contractId);
        masterContractResp.setMasterContract(masterContract);
        return masterContractResp;
    }

}
