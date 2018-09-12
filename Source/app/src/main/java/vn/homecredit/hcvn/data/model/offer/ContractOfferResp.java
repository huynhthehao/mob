package vn.homecredit.hcvn.data.model.offer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vn.homecredit.hcvn.data.model.api.base.BaseApiResponse;

public class ContractOfferResp extends BaseApiResponse {

    @SerializedName("data")
    @Expose
    private ContractOfferData data;

    public ContractOfferData getData() {
        return data;
    }

    public void setData(ContractOfferData data) {
        this.data = data;
    }
}
