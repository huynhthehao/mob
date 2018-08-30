package vn.homecredit.hcvn.ui.payment;

import android.content.Context;

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

    public PaymentMomoEventValueBuilder(Context context) {
        this.context = context;
        this.merchantCode = context.getString(R.string.momo_merchant_code);
        this.merchantName = context.getString(R.string.momo_merchant_name);
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

    public Map<String, Object> create() {
        if (contractNumber.isEmpty()) {
            throw new IllegalStateException("Contract number can not be empty!");
        }
        if (amount.isEmpty()) {
            throw new IllegalStateException("Amount can not be empty!");
        }
        Map<String, Object> eventValue = new HashMap<>();
        //client Required
        eventValue.put(MoMoParameterNamePayment.MERCHANT_NAME, merchantName);
        eventValue.put(MoMoParameterNamePayment.MERCHANT_CODE, merchantCode);
        eventValue.put(MoMoParameterNamePayment.MERCHANT_NAME_LABEL, merchantNameLabel);
        eventValue.put(MoMoParameterNamePayment.AMOUNT, amount);
        eventValue.put(MoMoParameterNamePayment.DESCRIPTION, context.getString(R.string.momo_payment_description, contractNumber));
        eventValue.put(MoMoParameterNamePayment.REQUEST_TYPE, "payment");
        eventValue.put(MoMoParameterNamePayment.LANGUAGE, "vi");
        return eventValue;
    }
}