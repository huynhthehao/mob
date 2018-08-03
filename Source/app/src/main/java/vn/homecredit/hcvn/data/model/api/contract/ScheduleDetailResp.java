package vn.homecredit.hcvn.data.model.api.contract;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import vn.homecredit.hcvn.data.model.api.base.BaseApiResponse;

public class ScheduleDetailResp extends BaseApiResponse {
    @SerializedName("data")
    @Expose
    private List<Instalment> data = null;

    public List<Instalment> getData() {
        return data;
    }

    public void setData(List<Instalment> data) {
        this.data = data;
    }

    public class Instalment {

        @SerializedName("amount")
        @Expose
        private Integer amount;
        @SerializedName("instalment_date")
        @Expose
        private String instalmentDate;
        @SerializedName("instalment_order")
        @Expose
        private Integer instalmentOrder;
        @SerializedName("instalment_status")
        @Expose
        private Integer instalmentStatus;
        @SerializedName("regularity_text_en")
        @Expose
        private String regularityTextEn;
        @SerializedName("regularity_text_vn")
        @Expose
        private String regularityTextVn;
        @SerializedName("code_instalment_regularity")
        @Expose
        private String codeInstalmentRegularity;

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public String getInstalmentDate() {
            return instalmentDate;
        }

        public void setInstalmentDate(String instalmentDate) {
            this.instalmentDate = instalmentDate;
        }

        public Integer getInstalmentOrder() {
            return instalmentOrder;
        }

        public void setInstalmentOrder(Integer instalmentOrder) {
            this.instalmentOrder = instalmentOrder;
        }

        public Integer getInstalmentStatus() {
            return instalmentStatus;
        }

        public void setInstalmentStatus(Integer instalmentStatus) {
            this.instalmentStatus = instalmentStatus;
        }

        public String getRegularityTextEn() {
            return regularityTextEn;
        }

        public void setRegularityTextEn(String regularityTextEn) {
            this.regularityTextEn = regularityTextEn;
        }

        public String getRegularityTextVn() {
            return regularityTextVn;
        }

        public void setRegularityTextVn(String regularityTextVn) {
            this.regularityTextVn = regularityTextVn;
        }

        public String getCodeInstalmentRegularity() {
            return codeInstalmentRegularity;
        }

        public void setCodeInstalmentRegularity(String codeInstalmentRegularity) {
            this.codeInstalmentRegularity = codeInstalmentRegularity;
        }

    }
}
