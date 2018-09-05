package vn.homecredit.hcvn.ui.payment.summary.model;

import org.parceler.Parcel;

@Parcel
public class PaymentSummaryModel {
    String paymentDate = "";
    String payerName = "";
    String beneficiary = "";
    int totalTransaction = 0;

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(String beneficiary) {
        this.beneficiary = beneficiary;
    }

    public int getTotalTransaction() {
        return totalTransaction;
    }

    public void setTotalTransaction(int totalTransaction) {
        this.totalTransaction = totalTransaction;
    }
}
