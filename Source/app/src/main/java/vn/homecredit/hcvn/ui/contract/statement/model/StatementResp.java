package vn.homecredit.hcvn.ui.contract.statement.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import vn.homecredit.hcvn.data.model.api.base.BaseApiResponse;

public class StatementResp extends BaseApiResponse {
    @SerializedName("data")
    @Expose
    private List<StatementModel> data;

    public List<StatementModel> getData() {
        return data;
    }

    public void setData(List<StatementModel> data) {
        this.data = data;
    }
}
