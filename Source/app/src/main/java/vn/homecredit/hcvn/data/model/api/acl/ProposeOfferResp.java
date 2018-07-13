package vn.homecredit.hcvn.data.model.api.acl;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vn.homecredit.hcvn.data.model.api.base.BaseApiResponse;

public class ProposeOfferResp extends BaseApiResponse {
    @SerializedName("data")
    @Expose
    private ProposeOfferRespData data;

    public ProposeOfferRespData getData() {
        return data;
    }

    public void setData(ProposeOfferRespData data) {
        this.data = data;
    }

    public class ProposeOfferRespData {
        @SerializedName("MonthlyPayment")
        @Expose
        private Integer monthlyPayment;
        @SerializedName("OfferCode")
        @Expose
        private String offerCode;
        @SerializedName("RequiredAmount")
        @Expose
        private Integer requiredAmount;
        @SerializedName("LoanAmount")
        @Expose
        private Integer loanAmount;
        @SerializedName("Tenor")
        @Expose
        private Integer tenor;
        @SerializedName("TenorWarning")
        @Expose
        private Boolean tenorWarning;
        @SerializedName("ProductCode")
        @Expose
        private String productCode;
        @SerializedName("BoundScore")
        @Expose
        private Integer boundScore;
        @SerializedName("InsuranceAmount")
        @Expose
        private Integer insuranceAmount;
        @SerializedName("InterestRate")
        @Expose
        private Double interestRate;
        @SerializedName("FirstDueDate")
        @Expose
        private String firstDueDate;

        public Integer getMonthlyPayment() {
            return monthlyPayment;
        }

        public void setMonthlyPayment(Integer monthlyPayment) {
            this.monthlyPayment = monthlyPayment;
        }

        public String getOfferCode() {
            return offerCode;
        }

        public void setOfferCode(String offerCode) {
            this.offerCode = offerCode;
        }

        public Integer getRequiredAmount() {
            return requiredAmount;
        }

        public void setRequiredAmount(Integer requiredAmount) {
            this.requiredAmount = requiredAmount;
        }

        public Integer getLoanAmount() {
            return loanAmount;
        }

        public void setLoanAmount(Integer loanAmount) {
            this.loanAmount = loanAmount;
        }

        public Integer getTenor() {
            return tenor;
        }

        public void setTenor(Integer tenor) {
            this.tenor = tenor;
        }

        public Boolean getTenorWarning() {
            return tenorWarning;
        }

        public void setTenorWarning(Boolean tenorWarning) {
            this.tenorWarning = tenorWarning;
        }

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public Integer getBoundScore() {
            return boundScore;
        }

        public void setBoundScore(Integer boundScore) {
            this.boundScore = boundScore;
        }

        public Integer getInsuranceAmount() {
            return insuranceAmount;
        }

        public void setInsuranceAmount(Integer insuranceAmount) {
            this.insuranceAmount = insuranceAmount;
        }

        public Double getInterestRate() {
            return interestRate;
        }

        public void setInterestRate(Double interestRate) {
            this.interestRate = interestRate;
        }

        public String getFirstDueDate() {
            return firstDueDate;
        }

        public void setFirstDueDate(String firstDueDate) {
            this.firstDueDate = firstDueDate;
        }
    }
}
