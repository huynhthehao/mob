package vn.homecredit.hcvn.ui.payment.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vn.homecredit.hcvn.data.model.api.base.BaseApiResponse;

public class MakePaymentResp extends BaseApiResponse {
    @SerializedName("data")
    @Expose
    private MakePaymentRespData makePaymentRespData;

    public MakePaymentRespData getMakePaymentRespData() {
        return makePaymentRespData;
    }

    public void setMakePaymentRespData(MakePaymentRespData makePaymentRespData) {
        this.makePaymentRespData = makePaymentRespData;
    }
}
