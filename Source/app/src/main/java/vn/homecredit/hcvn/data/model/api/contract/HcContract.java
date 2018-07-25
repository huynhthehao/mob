package vn.homecredit.hcvn.data.model.api.contract;

import com.google.gson.Gson;

public class HcContract {

    public static final int TYPE_ACTIVED = 0;
    public static final int TYPE_APPROVIED = 1;
    public static final int TYPE_CLOSED = 2;
    /**
     * client_name : Eidiá»Šj Ajá»Ž Dcb
     * contract_number : 3800589883
     * dpd : 0
     * id_number : 383200284
     * primary_phone : 056456445
     * product_type : Cash Loan
     * signed_date : 2018-07-20T00:00:00
     * amt_credit_total : 35409000
     * status_text_en : Singed
     * status_text_vn : Ä�Ã£ kÃ½
     * tenor : 0
     * product_code : CL
     * is_credit_card : false
     * status : N
     */
    private String client_name;
    private String contract_number;
    private String dpd;
    private String id_number;
    private String primary_phone;
    private String product_type;
    private String signed_date;
    private int amt_credit_total;
    private String status_text_en;
    private String status_text_vn;
    private int tenor;
    private String product_code;
    private boolean is_credit_card;
    private String status;

    public static HcContract objectFromData(String str) {

        return new Gson().fromJson(str, HcContract.class);
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getContract_number() {
        return contract_number;
    }

    public void setContract_number(String contract_number) {
        this.contract_number = contract_number;
    }

    public String getDpd() {
        return dpd;
    }

    public void setDpd(String dpd) {
        this.dpd = dpd;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getPrimary_phone() {
        return primary_phone;
    }

    public void setPrimary_phone(String primary_phone) {
        this.primary_phone = primary_phone;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getSigned_date() {
        return signed_date;
    }

    public void setSigned_date(String signed_date) {
        this.signed_date = signed_date;
    }

    public int getAmt_credit_total() {
        return amt_credit_total;
    }

    public void setAmt_credit_total(int amt_credit_total) {
        this.amt_credit_total = amt_credit_total;
    }

    public String getStatus_text_en() {
        return status_text_en;
    }

    public void setStatus_text_en(String status_text_en) {
        this.status_text_en = status_text_en;
    }

    public String getStatus_text_vn() {
        return status_text_vn;
    }

    public void setStatus_text_vn(String status_text_vn) {
        this.status_text_vn = status_text_vn;
    }

    public int getTenor() {
        return tenor;
    }

    public void setTenor(int tenor) {
        this.tenor = tenor;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public boolean isIs_credit_card() {
        return is_credit_card;
    }

    public void setIs_credit_card(boolean is_credit_card) {
        this.is_credit_card = is_credit_card;
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
