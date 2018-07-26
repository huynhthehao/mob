package vn.homecredit.hcvn.ui.notification.model;

import java.util.Date;

public class ClwResult {
    int status;
    int applicationLoanId;
    String contractNumber = "";
    double amount = 0;
    int tenor;
    Date expiredDate ;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getApplicationLoanId() {
        return applicationLoanId;
    }

    public void setApplicationLoanId(int applicationLoanId) {
        this.applicationLoanId = applicationLoanId;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getTenor() {
        return tenor;
    }

    public void setTenor(int tenor) {
        this.tenor = tenor;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }
}
