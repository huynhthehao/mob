package vn.homecredit.hcvn.mock;

import vn.homecredit.hcvn.data.model.api.ProfileResp;
import vn.homecredit.hcvn.data.model.api.contract.MasterContract;
import vn.homecredit.hcvn.data.model.api.contract.MasterContractResp;
import vn.homecredit.hcvn.utils.TestData;

public class MockData {

    public static ProfileResp profileResp(int statusCode, String message) {
        ProfileResp profileResp = new ProfileResp();
        profileResp.setResponseCode(statusCode);
        profileResp.setResponseMessage( message);
        return profileResp;
    }

    public static MasterContractResp masterContractResp(boolean isPrepared){
        MasterContractResp masterContractResp = new MasterContractResp();
        MasterContract masterContract = new MasterContract();
        masterContract.setContractStatus("approved");
        masterContract.setMaterialPrepared(isPrepared);
        masterContractResp.setMasterContract(masterContract);
        return  masterContractResp;
    }
    public static MasterContractResp masterContractResp(){
        return masterContractResp(false);
    }
}
