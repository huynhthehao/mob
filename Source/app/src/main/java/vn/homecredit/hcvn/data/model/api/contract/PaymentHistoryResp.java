package vn.homecredit.hcvn.data.model.api.contract;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import vn.homecredit.hcvn.data.model.api.base.BaseApiResponse;

public class PaymentHistoryResp extends BaseApiResponse {

    @SerializedName("data")
    @Expose
    private List<Payment> data = null;

    public List<Payment> getData() {
        return data;
    }

    public void setData(List<Payment> data) {
        this.data = data;
    }

    public class Payment {

        @SerializedName("paid_amount")
        @Expose
        private Integer paidAmount;
        @SerializedName("paid_date")
        @Expose
        private String paidDate;

        public Integer getPaidAmount() {
            return paidAmount;
        }

        public void setPaidAmount(Integer paidAmount) {
            this.paidAmount = paidAmount;
        }

        public String getPaidDate() {
            return paidDate;
        }

        public void setPaidDate(String paidDate) {
            this.paidDate = paidDate;
        }

    }
}
