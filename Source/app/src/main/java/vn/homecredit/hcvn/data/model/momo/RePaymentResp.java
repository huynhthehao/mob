package vn.homecredit.hcvn.data.model.momo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vn.homecredit.hcvn.data.model.api.base.BaseApiResponse;

public class RePaymentResp extends BaseApiResponse {

    @SerializedName("data")
    @Expose
    private RePaymentData rePaymentData ;

    public RePaymentData getRePaymentData() {
        return rePaymentData;
    }

    public void setRePaymentData(RePaymentData rePaymentData) {
        this.rePaymentData = rePaymentData;
    }
}
