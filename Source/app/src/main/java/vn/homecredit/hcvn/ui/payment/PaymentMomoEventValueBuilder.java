package vn.homecredit.hcvn.ui.payment;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import vn.homecredit.hcvn.R;
import vn.momo.momo_partner.MoMoParameterNamePayment;

public class PaymentMomoEventValueBuilder {
    Context context;
    private String merchantName;
    private String merchantCode;
    private String merchantNameLabel;
    private String contractNumber;
    private String amount;
    private String languageCode;

    public PaymentMomoEventValueBuilder(Context context) {
        this.context = context;
        this.merchantNameLabel = context.getString(R.string.momo_merchant_name_label);
    }

    public PaymentMomoEventValueBuilder setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
        return this;
    }

    public PaymentMomoEventValueBuilder setAmount(String amount) {
        this.amount = amount;
        return this;
    }

    public PaymentMomoEventValueBuilder setMerchantName(String merchantName) {
        this.merchantName = merchantName;
        return this;
    }

    public PaymentMomoEventValueBuilder setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
        return this;
    }

    public PaymentMomoEventValueBuilder setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
        return this;
    }

    public Map<String, Object> create() {
        if (contractNumber.isEmpty()) {
            throw new IllegalStateException("Contract number can not be empty!");
        }
        if (amount.isEmpty()) {
            throw new IllegalStateException("Amount can not be empty!");
        }
        if (merchantCode.isEmpty()) {
            throw new IllegalStateException("Merchant code can not be empty!");
        }
        if (merchantName.isEmpty()) {
            throw new IllegalStateException("Merchant name can not be empty!");
        }
        Map<String, Object> eventValue = new HashMap<>();
        //client Required
        eventValue.put(MoMoParameterNamePayment.MERCHANT_NAME, merchantName);
        eventValue.put(MoMoParameterNamePayment.MERCHANT_CODE, merchantCode);
        eventValue.put(MoMoParameterNamePayment.MERCHANT_NAME_LABEL, merchantNameLabel);
        eventValue.put(MoMoParameterNamePayment.AMOUNT, amount);
        eventValue.put(MoMoParameterNamePayment.DESCRIPTION, context.getString(R.string.momo_payment_description));
        eventValue.put(MoMoParameterNamePayment.REQUEST_TYPE, "payment");
        eventValue.put(MoMoParameterNamePayment.LANGUAGE, languageCode);
        eventValue.put("orderLabel", context.getString(R.string.contract_number));
        eventValue.put("orderId", contractNumber);
        return eventValue;
    }
}