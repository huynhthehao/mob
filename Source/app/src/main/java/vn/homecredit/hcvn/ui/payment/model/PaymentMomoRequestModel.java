package vn.homecredit.hcvn.ui.payment.model;

import vn.homecredit.hcvn.data.model.momo.RePaymentData;

public class PaymentMomoRequestModel {
    RePaymentData rePaymentData = new RePaymentData();
    String merchantName = "";
    String merchantCode = "";
    String languageCode = "";

    public RePaymentData getRePaymentData() {
        return rePaymentData;
    }

    public void setRePaymentData(RePaymentData rePaymentData) {
        this.rePaymentData = rePaymentData;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }
}
