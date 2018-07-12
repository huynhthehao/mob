package vn.homecredit.hcvn.data.model.api.acl;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import vn.homecredit.hcvn.data.model.api.base.BaseApiResponse;

public class SuggestOfferResp extends BaseApiResponse {
    @SerializedName("data")
    @Expose
    private SuggestOfferRespData data;

    public SuggestOfferRespData getData() {
        return data;
    }

    public void setData(SuggestOfferRespData data) {
        this.data = data;
    }

    public class SuggestOfferRespData {

        @SerializedName("BoundScore")
        @Expose
        private Integer boundScore;
        @SerializedName("LoanTenors")
        @Expose
        private List<LoanTenor> loanTenors = null;

        public Integer getBoundScore() {
            return boundScore;
        }

        public void setBoundScore(Integer boundScore) {
            this.boundScore = boundScore;
        }

        public List<LoanTenor> getLoanTenors() {
            return loanTenors;
        }

        public void setLoanTenors(List<LoanTenor> loanTenors) {
            this.loanTenors = loanTenors;
        }

    }

    public class LoanTenor {

        @SerializedName("Amount")
        @Expose
        private Integer amount;
        @SerializedName("TenorProducts")
        @Expose
        private List<TenorProduct> tenorProducts = null;

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public List<TenorProduct> getTenorProducts() {
            return tenorProducts;
        }

        public void setTenorProducts(List<TenorProduct> tenorProducts) {
            this.tenorProducts = tenorProducts;
        }

    }

    public class TenorProduct {

        @SerializedName("Tenor")
        @Expose
        private Integer tenor;
        @SerializedName("ProductCode")
        @Expose
        private String productCode;

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

    }
}
