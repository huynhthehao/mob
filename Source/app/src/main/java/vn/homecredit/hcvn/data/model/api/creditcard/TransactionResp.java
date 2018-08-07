
/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/30/18 3:57 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.data.model.api.creditcard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import vn.homecredit.hcvn.data.model.api.contract.ContractDataResp;
import vn.homecredit.hcvn.data.model.api.contract.HcContract;
import vn.homecredit.hcvn.data.model.api.contract.MasterContract;

public class TransactionResp {

    @SerializedName("data")
    @Expose
    private List<Transaction> transactions = new ArrayList<>();

    @SerializedName("response_code")
    @Expose
    private Integer responseCode;

    public List<Transaction> getData() {
        return transactions;
    }

    public Integer getResponseCode() {
        return responseCode;
    }
}
