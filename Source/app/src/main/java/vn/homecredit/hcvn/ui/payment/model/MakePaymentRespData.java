package vn.homecredit.hcvn.ui.payment.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MakePaymentRespData {
    @SerializedName("contractNumber")
    @Expose
    private String contractNumber ;

    @SerializedName("amount")
    @Expose
    private int amount ;

    @SerializedName("transactionId")
    @Expose
    private String transactionId;

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
