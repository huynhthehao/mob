package vn.homecredit.hcvn.data.model.api.contract;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import vn.homecredit.hcvn.data.model.api.base.BaseApiResponse;

public class MasterContractDocResp extends BaseApiResponse {
    @SerializedName("data")
    @Expose
    private List<String> images;
}
