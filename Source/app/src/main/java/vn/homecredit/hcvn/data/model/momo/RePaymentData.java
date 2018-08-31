package vn.homecredit.hcvn.data.model.momo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RePaymentData {

    /**
     * rePaymentId : 0
     * contractNumber : string
     * fullName : string
     * idNumber : string
     * totalAmount : 0
     * amount : 0
     * dueDate : 2018-08-30T03:47:23.351Z
     */

    @SerializedName("rePaymentId")
    @Expose
    private int rePaymentId;

    @SerializedName("contractNumber")
    @Expose
    private String contractNumber;

    @SerializedName("fullName")
    @Expose
    private String fullName;

    @SerializedName("idNumber")
    @Expose
    private String idNumber;

    @SerializedName("totalAmount")
    @Expose
    private int totalAmount;

    @SerializedName("amount")
    @Expose
    private int amount;

    @SerializedName("dueDate")
    @Expose
    private String dueDate;

    public int getRePaymentId() {
        return rePaymentId;
    }

    public void setRePaymentId(int rePaymentId) {
        this.rePaymentId = rePaymentId;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}
