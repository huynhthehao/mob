package vn.homecredit.hcvn.ui.contract.statement.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatementModel {
    @SerializedName("statementId")
    @Expose
    private String id;

    @SerializedName("startDate")
    @Expose
    private String startDate;

    @SerializedName("endDate")
    @Expose
    private String endDate;

    @SerializedName("textVn")
    @Expose
    private String textVn;

    @SerializedName("textEn")
    @Expose
    private String textEn;

    @SerializedName("key")
    @Expose
    private String key;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTextVn() {
        return textVn;
    }

    public void setTextVn(String textVn) {
        this.textVn = textVn;
    }

    public String getTextEn() {
        return textEn;
    }

    public void setTextEn(String textEn) {
        this.textEn = textEn;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
