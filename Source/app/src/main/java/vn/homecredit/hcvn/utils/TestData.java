package vn.homecredit.hcvn.utils;

import java.util.ArrayList;
import java.util.List;

import vn.homecredit.hcvn.data.model.api.creditcard.HcCreditCard;
import vn.homecredit.hcvn.data.model.api.contract.ContractStatus;
import vn.homecredit.hcvn.data.model.api.contract.ContractType;
import vn.homecredit.hcvn.data.model.api.contract.HcContract;
import vn.homecredit.hcvn.data.model.api.contract.MasterContractVerifyDataResp;
import vn.homecredit.hcvn.data.model.api.contract.MasterContractVerifyResp;
import vn.homecredit.hcvn.data.model.api.contract.NextPayment;
import vn.homecredit.hcvn.data.model.momo.RePaymentData;
import vn.homecredit.hcvn.data.model.momo.RePaymentResp;

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

    public static HcContract activeCreditCardContract() {
        HcContract contract = hcContract(ContractStatus.Active, ContractType.CreditCard);
        contract.isCreditCard(true);

        List<HcCreditCard> cards = new ArrayList<>();

        HcCreditCard card1 = new HcCreditCard();
        card1.status = "ACTIVE";
        card1.statusDescriptionVn = "Còn ngon";
        card1.availableBalance = 123456;
        card1.holdBalance = 3333;
        card1.limit = 4444;
        card1.cardType = "Cá nhân";
        card1.cardNumber = "xxxx xxxx xxxx 1357";

        HcCreditCard card2 = new HcCreditCard();
        card2.status = "CLOSE";
        card2.statusDescriptionVn = "Quăng sọt";
        card2.availableBalance = 123456;
        card2.holdBalance = 3333;
        card2.limit = 4444;
        card2.cardType = "Tổ chức";
        card2.cardNumber = "xxxx xxxx xxxx 9999";

        HcCreditCard card3 = new HcCreditCard();
        card3.status = "CLOSE";
        card3.statusDescriptionVn = "Quăng sọt";
        card3.availableBalance = 566778888;
        card3.holdBalance = 55555;
        card3.limit = 4444;
        card3.cardType = "Tổ chức";
        card3.cardNumber = "xxxx xxxx xxxx 7777";

        cards.add(card2);
        cards.add(card1);
        cards.add(card3);
        contract.setCards(cards);
        return contract;
    }
    public static HcContract hcContract(String status, String type) {
        HcContract hcContract = new HcContract();
        hcContract.setClientName("Lê Văn Tèo");
        hcContract.setContractNumber("3800589883");
        hcContract.setSignedDate("2018-07-20T00:00:00");
        hcContract.setAmtCreditTotal(35409000);
        hcContract.setStatusTextVn("Ngon lành");
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

    public static MasterContractVerifyResp masterContractVerifyResp() {
        MasterContractVerifyDataResp masterContractVerifyDataResp = new MasterContractVerifyDataResp();
        masterContractVerifyDataResp.setTimeOut(6000);
        masterContractVerifyDataResp.setLoadInterval(3000);
        MasterContractVerifyResp masterContractVerifyResp = new MasterContractVerifyResp();
        masterContractVerifyResp.setResponseCode(0);
        masterContractVerifyResp.setMasterContractVerifyDataResp(masterContractVerifyDataResp);
        return masterContractVerifyResp;
    }

    public static RePaymentResp rePaymentResp(){
        RePaymentResp rePaymentResp = new RePaymentResp();
        rePaymentResp.setResponseCode(0);
        RePaymentData rePaymentData = new RePaymentData();
        rePaymentData.setContractNumber("12345678");
        rePaymentData.setFullName("nguyen van a");
        rePaymentData.setIdNumber("***7218");
        rePaymentData.setTotalAmount(1000000);
        rePaymentData.setAmount(500000);
        rePaymentData.setDueDate("2018-07-20T00:00:00");
        rePaymentResp.setRePaymentData(rePaymentData);
        return rePaymentResp;
    }
}
