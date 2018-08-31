package vn.homecredit.hcvn.ui.payment.model;

public class MakePaymentRequestValue {
    String rePaymentId = "";
    String contractNumber = "";
    String token = "";
    String provider = "momo";
    String amount = "";
    String customerNumber = "";

    public String getRePaymentId() {
        return rePaymentId;
    }

    public void setRePaymentId(String rePaymentId) {
        this.rePaymentId = rePaymentId;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }
}
