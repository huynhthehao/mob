package vn.homecredit.hcvn.ui.notification.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import vn.homecredit.hcvn.utils.DateUtils;

@Parcel
public class OfferModel {
    @SerializedName("message_code")
    @Expose
    private String messageCode;

    @SerializedName("product_code")
    @Expose
    private String productCode;

    @SerializedName("has_gift")
    @Expose
    private boolean hasGift;

    @SerializedName("start_date")
    @Expose
    private String startDate;

    @SerializedName("end_date")
    @Expose
    private String endDate;

    @SerializedName("cam_id")
    @Expose
    private String camId;

    @SerializedName("active")
    @Expose
    private Boolean active;

    @SerializedName("max_loan_amount")
    @Expose
    private Integer maxLoanAmount;
    @SerializedName("min_loan_amount")
    @Expose
    private Integer minLoanAmount;

    @SerializedName("risk_group")
    @Expose
    private String riskGroup;

    private long dayLeft;
    private boolean expired;

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public boolean isHasGift() {
        return hasGift;
    }

    public void setHasGift(boolean hasGift) {
        this.hasGift = hasGift;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCamId() {
        return camId;
    }

    public void setCamId(String camId) {
        this.camId = camId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getMaxLoanAmount() {
        return maxLoanAmount;
    }

    public void setMaxLoanAmount(Integer maxLoanAmount) {
        this.maxLoanAmount = maxLoanAmount;
    }

    public Integer getMinLoanAmount() {
        return minLoanAmount;
    }

    public void setMinLoanAmount(Integer minLoanAmount) {
        this.minLoanAmount = minLoanAmount;
    }

    public String getRiskGroup() {
        return riskGroup;
    }

    public void setRiskGroup(String riskGroup) {
        this.riskGroup = riskGroup;
    }

    public int getDayLeft() {
        long days = DateUtils.calcDayLeft(endDate);
        if (days < 0) days = 0;
        return (int) days;
    }

    public boolean isExpired() {
        return getDayLeft() <= 0;
    }
}
