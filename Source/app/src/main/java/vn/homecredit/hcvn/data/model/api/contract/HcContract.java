
package vn.homecredit.hcvn.data.model.api.contract;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HcContract {

    public static final int TYPE_ACTIVED = 0;
    public static final int TYPE_APPROVIED = 1;
    public static final int TYPE_CLOSED = 2;

    @SerializedName("client_name")
    @Expose
    private String clientName;
    @SerializedName("contract_number")
    @Expose
    private String contractNumber;
    @SerializedName("dpd")
    @Expose
    private String dpd;
    @SerializedName("id_number")
    @Expose
    private String idNumber;
    @SerializedName("primary_phone")
    @Expose
    private String primaryPhone;
    @SerializedName("product_type")
    @Expose
    private String productType;
    @SerializedName("signed_date")
    @Expose
    private String signedDate;
    @SerializedName("amt_credit_total")
    @Expose
    private Integer amtCreditTotal;
    @SerializedName("status_text_en")
    @Expose
    private String statusTextEn;
    @SerializedName("status_text_vn")
    @Expose
    private String statusTextVn;
    @SerializedName("tenor")
    @Expose
    private Integer tenor;
    @SerializedName("product_code")
    @Expose
    private String productCode;
    @SerializedName("is_credit_card")
    @Expose
    private Boolean isCreditCard;
    @SerializedName("status")
    @Expose
    private String status;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getDpd() {
        return dpd;
    }

    public void setDpd(String dpd) {
        this.dpd = dpd;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPrimaryPhone() {
        return primaryPhone;
    }

    public void setPrimaryPhone(String primaryPhone) {
        this.primaryPhone = primaryPhone;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getSignedDate() {
        return signedDate;
    }

    public void setSignedDate(String signedDate) {
        this.signedDate = signedDate;
    }

    public Integer getAmtCreditTotal() {
        return amtCreditTotal;
    }

    public void setAmtCreditTotal(Integer amtCreditTotal) {
        this.amtCreditTotal = amtCreditTotal;
    }

    public String getStatusTextEn() {
        return statusTextEn;
    }

    public void setStatusTextEn(String statusTextEn) {
        this.statusTextEn = statusTextEn;
    }

    public String getStatusTextVn() {
        return statusTextVn;
    }

    public void setStatusTextVn(String statusTextVn) {
        this.statusTextVn = statusTextVn;
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

    public Boolean getIsCreditCard() {
        return isCreditCard;
    }

    public void setIsCreditCard(Boolean isCreditCard) {
        this.isCreditCard = isCreditCard;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTypeContract() {
        if (status == null) {
            return TYPE_CLOSED;
        }
        if (status.equals(ContractStatus.Active)) {
            return TYPE_ACTIVED;
        }else if (status.equals(ContractStatus.Approved)) {
            return TYPE_APPROVIED;
        }else {
            return TYPE_CLOSED;
        }
    }
}
