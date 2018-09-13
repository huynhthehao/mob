package vn.homecredit.hcvn.data.model.offer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vn.homecredit.hcvn.data.model.api.base.BaseApiResponse;

public class OfferDetailResp extends BaseApiResponse {

    @SerializedName("data")
    @Expose
    private OfferDetailData data;

    public OfferDetailData getData() {
        return data;
    }

    public void setData(OfferDetailData data) {
        this.data = data;
    }
}
