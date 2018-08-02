
package vn.homecredit.hcvn.data.model.api.contract;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class HcContract {

    public static final int STATUS_ACTIVE = 0;
    public static final int STATUS_PENDING = 1;
    public static final int STATUS_CLOSED = 2;

    public static final int TYPE_TWO_WHEEL = 0;
    public static final int TYPE_CASH_LOAN = 1;
    public static final int TYPE_DURABLE = 2;
    public static final int TYPE_CREDIT_CARD = 3;

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

    @SerializedName("nextPayment")
    @Expose
    private NextPayment nextPayment;

    private MasterContract masterContract;

    private boolean isShowSection = false;

    public boolean isShowSection() {
        return isShowSection;
    }

    public void setShowSection(boolean showSection) {
        isShowSection = showSection;
    }

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
        return masterContract != null ? masterContract.getLoanAmount() : amtCreditTotal;
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


    public NextPayment getNextPayment() {
        return nextPayment;
    }

    public void setNextPayment(NextPayment nextPayment) {
        this.nextPayment = nextPayment;
    }

    public MasterContract getMasterContract() {
        return masterContract;
    }

    public void setMasterContract(MasterContract masterContract) {
        this.masterContract = masterContract;
    }

    public int getNextPaymentTotal() {
        if (nextPayment == null) {
            return 0;
        }
        return nextPayment.getTotal();
    }

    public String getNextPaymentDueDate() {
        if (nextPayment == null) {
            return "";
        }
        return nextPayment.getDateNextDue();
    }

    public int getTypeStatus() {
        if (masterContract != null) {
            return STATUS_PENDING;
        }
        if (status == null) {
            return STATUS_CLOSED;
        }
        if (status.equals(ContractStatus.Active)) {
            return STATUS_ACTIVE;
        }else if (status.equals(ContractStatus.Approved)) {
            return STATUS_PENDING;
        }else {
            return STATUS_CLOSED;
        }
    }

    public int getTypeContract() {
        if (productCode == null) {
            return TYPE_CASH_LOAN;
        }
        if (ContractType.CashLoan.equals(productCode)) {
            return TYPE_CASH_LOAN;
        }else if (ContractType.ConsumerDurables.equals(productCode)) {
            return TYPE_DURABLE;
        }else if (ContractType.CreditCard.equals(productCode)) {
            return TYPE_CREDIT_CARD;
        }else if (ContractType.TwoWheels.equals(productCode)) {
            return TYPE_TWO_WHEEL;
        }else {
            return TYPE_CASH_LOAN;
        }
    }


}
