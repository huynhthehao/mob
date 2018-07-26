package vn.homecredit.hcvn.utils;

import vn.homecredit.hcvn.data.model.api.contract.ContractStatus;
import vn.homecredit.hcvn.data.model.api.contract.HcContract;

public class TestData {
    public static HcContract approvedContract() {
        HcContract hcContract = new HcContract();
        hcContract.setContractNumber("3800589883");
        hcContract.setSignedDate("2018-07-20T00:00:00");
        hcContract.setAmtCreditTotal(35409000);
        hcContract.setStatus(ContractStatus.Approved);
        return hcContract;
    }

    public static HcContract pendingContract() {
        HcContract hcContract = new HcContract();
        hcContract.setContractNumber("3800589883");
        hcContract.setSignedDate("2018-07-20T00:00:00");
        hcContract.setAmtCreditTotal(35409000);
        hcContract.setStatus(ContractStatus.Active);
        return hcContract;
    }
}
