package vn.homecredit.hcvn.data.model.api.contract;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class MasterContract {

    @SerializedName("MaterialPrepared")
    @Expose
    private boolean materialPrepared;
    @SerializedName("CustomerBirthDate")
    @Expose
    private String customerBirthDate;
    @SerializedName("CustomerEmail")
    @Expose
    private String rustomerEmail;
    @SerializedName("DisbursementBankAccountHolderName")
    @Expose
    private String disbursementBankAccountHolderName;
    @SerializedName("DisbursementBankAccountNumber")
    @Expose
    private String disbursementBankAccountNumber;
    @SerializedName("DisbursementBankName")
    @Expose
    private String disbursementBankName;
    @SerializedName("DisbursementBankBranchname")
    @Expose
    private String disbursementBankBranchname;
    @SerializedName("HasDisbursementBankAccount")
    @Expose
    private boolean hasDisbursementBankAccount;
    @SerializedName("IsCreditCardContract")
    @Expose
    private boolean isCreditCardContract;
    @SerializedName("IsAllCardActivated")
    @Expose
    private boolean isAllCardActivated;
    @SerializedName("ProductInformation")
    @Expose
    private String productInformation;
    @SerializedName("CustomerPrimaryPhoneNumber")
    @Expose
    private String customerPrimaryPhoneNumber;
    @SerializedName("CustomerSecondaryPhoneNumber")
    @Expose
    private String customerSecondaryPhoneNumber;
    @SerializedName("CreditLimit")
    @Expose
    private int creditLimit;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Source")
    @Expose
    private String source;
    @SerializedName("ContractNumber")
    @Expose
    private String contractNumber;
    @SerializedName("ContractStatus")
    @Expose
    private String contractStatus;
    @SerializedName("ContractStatusText")
    @Expose
    private String contractStatusText;
    @SerializedName("ContractStatusDate")
    @Expose
    private String contractStatusDate;
    @SerializedName("CustomerFullname")
    @Expose
    private String customerFullname;
    @SerializedName("PresentedInterestRate")
    @Expose
    private String presentedInterestRate;
    @SerializedName("CustomerAddress")
    @Expose
    private String customerAddress;
    @SerializedName("TotalMonthlyPayment")
    @Expose
    private int totalMonthlyPayment;
    @SerializedName("FirstDueDate")
    @Expose
    private String firstDueDate;
    @SerializedName("LastDueDate")
    @Expose
    private String lastDueDate;
    @SerializedName("PaymentDuration")
    @Expose
    private String paymentDuration;
    @SerializedName("CreditAmount")
    @Expose
    private int creditAmount;
    @SerializedName("TotalPaymentPerCredit")
    @Expose
    private int totalPaymentPerCredit;
    @SerializedName("Annuity")
    @Expose
    private int annuity;
    @SerializedName("CustomerFirstDueDate")
    @Expose
    private String customerFirstDueDate;
    @SerializedName("Terms")
    @Expose
    private int terms;
    @SerializedName("SignedDate")
    @Expose
    private String signedDate;
    @SerializedName("HasInsurance")
    @Expose
    private boolean hasInsurance;
    @SerializedName("MinimumPayment")
    @Expose
    private int minimumPayment;

    public MasterContract() {
    }

    public boolean isMaterialPrepared() {
        return materialPrepared;
    }

    public void setMaterialPrepared(boolean materialPrepared) {
        this.materialPrepared = materialPrepared;
    }

    public String getCustomerBirthDate() {
        return customerBirthDate;
    }

    public void setCustomerBirthDate(String customerBirthDate) {
        this.customerBirthDate = customerBirthDate;
    }

    public String getRustomerEmail() {
        return rustomerEmail;
    }

    public void setRustomerEmail(String rustomerEmail) {
        this.rustomerEmail = rustomerEmail;
    }

    public String getDisbursementBankAccountHolderName() {
        return disbursementBankAccountHolderName;
    }

    public void setDisbursementBankAccountHolderName(String disbursementBankAccountHolderName) {
        this.disbursementBankAccountHolderName = disbursementBankAccountHolderName;
    }

    public String getDisbursementBankAccountNumber() {
        return disbursementBankAccountNumber;
    }

    public void setDisbursementBankAccountNumber(String disbursementBankAccountNumber) {
        this.disbursementBankAccountNumber = disbursementBankAccountNumber;
    }

    public String getDisbursementBankName() {
        return disbursementBankName;
    }

    public void setDisbursementBankName(String disbursementBankName) {
        this.disbursementBankName = disbursementBankName;
    }

    public String getDisbursementBankBranchname() {
        return disbursementBankBranchname;
    }

    public void setDisbursementBankBranchname(String disbursementBankBranchname) {
        this.disbursementBankBranchname = disbursementBankBranchname;
    }

    public boolean isHasDisbursementBankAccount() {
        return hasDisbursementBankAccount;
    }

    public void setHasDisbursementBankAccount(boolean hasDisbursementBankAccount) {
        this.hasDisbursementBankAccount = hasDisbursementBankAccount;
    }

    public boolean isCreditCardContract() {
        return isCreditCardContract;
    }

    public void setCreditCardContract(boolean creditCardContract) {
        isCreditCardContract = creditCardContract;
    }

    public boolean isAllCardActivated() {
        return isAllCardActivated;
    }

    public void setAllCardActivated(boolean allCardActivated) {
        isAllCardActivated = allCardActivated;
    }

    public String getProductInformation() {
        return productInformation;
    }

    public void setProductInformation(String productInformation) {
        this.productInformation = productInformation;
    }

    public String getCustomerPrimaryPhoneNumber() {
        return customerPrimaryPhoneNumber;
    }

    public void setCustomerPrimaryPhoneNumber(String customerPrimaryPhoneNumber) {
        this.customerPrimaryPhoneNumber = customerPrimaryPhoneNumber;
    }

    public String getCustomerSecondaryPhoneNumber() {
        return customerSecondaryPhoneNumber;
    }

    public void setCustomerSecondaryPhoneNumber(String customerSecondaryPhoneNumber) {
        this.customerSecondaryPhoneNumber = customerSecondaryPhoneNumber;
    }

    public int getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(int creditLimit) {
        this.creditLimit = creditLimit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public String getContractStatusText() {
        return contractStatusText;
    }

    public void setContractStatusText(String contractStatusText) {
        this.contractStatusText = contractStatusText;
    }

    public String getContractStatusDate() {
        return contractStatusDate;
    }

    public void setContractStatusDate(String contractStatusDate) {
        this.contractStatusDate = contractStatusDate;
    }

    public String getCustomerFullname() {
        return customerFullname;
    }

    public void setCustomerFullname(String customerFullname) {
        this.customerFullname = customerFullname;
    }

    public String getPresentedInterestRate() {
        return presentedInterestRate;
    }

    public void setPresentedInterestRate(String presentedInterestRate) {
        this.presentedInterestRate = presentedInterestRate;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public int getTotalMonthlyPayment() {
        return totalMonthlyPayment;
    }

    public void setTotalMonthlyPayment(int totalMonthlyPayment) {
        this.totalMonthlyPayment = totalMonthlyPayment;
    }

    public String getFirstDueDate() {
        return firstDueDate;
    }

    public void setFirstDueDate(String firstDueDate) {
        this.firstDueDate = firstDueDate;
    }

    public String getLastDueDate() {
        return lastDueDate;
    }

    public void setLastDueDate(String lastDueDate) {
        this.lastDueDate = lastDueDate;
    }

    public String getPaymentDuration() {
        return paymentDuration;
    }

    public void setPaymentDuration(String paymentDuration) {
        this.paymentDuration = paymentDuration;
    }

    public int getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(int creditAmount) {
        this.creditAmount = creditAmount;
    }

    public int getTotalPaymentPerCredit() {
        return totalPaymentPerCredit;
    }

    public void setTotalPaymentPerCredit(int totalPaymentPerCredit) {
        this.totalPaymentPerCredit = totalPaymentPerCredit;
    }

    public int getAnnuity() {
        return annuity;
    }

    public void setAnnuity(int annuity) {
        this.annuity = annuity;
    }

    public String getCustomerFirstDueDate() {
        return customerFirstDueDate;
    }

    public void setCustomerFirstDueDate(String customerFirstDueDate) {
        this.customerFirstDueDate = customerFirstDueDate;
    }

    public int getTerms() {
        return terms;
    }

    public void setTerms(int terms) {
        this.terms = terms;
    }

    public String getSignedDate() {
        return signedDate;
    }

    public void setSignedDate(String signedDate) {
        this.signedDate = signedDate;
    }

    public boolean isHasInsurance() {
        return hasInsurance;
    }

    public void setHasInsurance(boolean hasInsurance) {
        this.hasInsurance = hasInsurance;
    }

    public int getMinimumPayment() {
        return minimumPayment;
    }

    public void setMinimumPayment(int minimumPayment) {
        this.minimumPayment = minimumPayment;
    }

    public int getLoanAmount() {
        return isCreditCardContract ? creditLimit : creditAmount;
    }

    public boolean canApproved() {
        return contractStatus.equals("approved");
    }
    public boolean isSigned() {
        return contractStatus.equals("signed");
    }
}
