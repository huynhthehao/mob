package vn.homecredit.hcvn.ui.contract.statement.statementdetails.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import vn.homecredit.hcvn.data.model.api.base.BaseApiResponse;

public class StatementDetailsResp extends BaseApiResponse {
    @SerializedName("data")
    @Expose
    private List<StatementDetailModel> data;

    public List<StatementDetailModel> getData() {
        return data;
    }

    public void setData(List<StatementDetailModel> data) {
        this.data = data;
    }
}
