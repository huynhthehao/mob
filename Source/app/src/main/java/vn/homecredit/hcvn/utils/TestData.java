package vn.homecredit.hcvn.utils;

import java.util.UUID;

import vn.homecredit.hcvn.data.model.api.contract.ContractStatus;
import vn.homecredit.hcvn.data.model.api.contract.HcContract;
import vn.homecredit.hcvn.data.model.api.contract.NextPayment;

public class TestData {

    public static HcContract activeContract() {
        return hcContract(ContractStatus.Active, null);
    }
    public static HcContract closeContract() {
        return hcContract(ContractStatus.Finished, null);
    }
    public static HcContract pendingContract() {
        return hcContract(ContractStatus.Approved, null);
    }
    public static HcContract pendingContract(String type) {
        return hcContract(ContractStatus.Approved, type);
    }
    public static HcContract hcContract(String status, String type) {
        HcContract hcContract = new HcContract();
        hcContract.setClientName(UUID.randomUUID().toString());
        hcContract.setContractNumber("3800589883");
        hcContract.setSignedDate("2018-07-20T00:00:00");
        hcContract.setAmtCreditTotal(35409000);
        hcContract.setStatus(status);
        hcContract.setProductCode(type);
        hcContract.setNextPayment(nextPayment());
        return hcContract;
    }

    public static NextPayment nextPayment() {
        NextPayment nextPayment = new NextPayment();
        nextPayment.setDateNextDue("2018-07-20T00:00:00");
        nextPayment.setTotal(35409000);
        return nextPayment;

    }
}
