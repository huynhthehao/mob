package vn.homecredit.hcvn.data.model.api.acl.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProposeOfferRequest {
    @SerializedName("RequiredAmount")
    @Expose
    private Double requiredAmount;
    @SerializedName("Tenor")
    @Expose
    private Integer tenor;
    @SerializedName("ProductCode")
    @Expose
    private String productCode;
    @SerializedName("BoundScore")
    @Expose
    private float boundScore;
    @SerializedName("HasInsurance")
    @Expose
    private Boolean hasInsurance;

    public ProposeOfferRequest(Double requiredAmount, Integer tenor, String productCode, float boundScore) {
        this.requiredAmount = requiredAmount;
        this.tenor = tenor;
        this.productCode = productCode;
        this.boundScore = boundScore;
        this.hasInsurance = true;
    }

    public Double getRequiredAmount() {
        return requiredAmount;
    }

    public void setRequiredAmount(Double requiredAmount) {
        this.requiredAmount = requiredAmount;
    }

    public Integer getTenor() {
        return tenor;
    }

    public void setTenor(Integer tenor) {
        this.tenor = tenor;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public float getBoundScore() {
        return boundScore;
    }

    public void setBoundScore(Integer boundScore) {
        this.boundScore = boundScore;
    }

    public Boolean getHasInsurance() {
        return hasInsurance;
    }

    public void setHasInsurance(Boolean hasInsurance) {
        this.hasInsurance = hasInsurance;
    }

}
