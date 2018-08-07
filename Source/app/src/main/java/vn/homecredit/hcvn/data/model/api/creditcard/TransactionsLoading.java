/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 8/7/18 3:38 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.data.model.api.creditcard;


import com.google.gson.annotations.Expose;
import org.parceler.Parcel;

import vn.homecredit.hcvn.data.model.api.contract.HcContract;
import vn.homecredit.hcvn.data.model.enums.TransactionListType;

@Parcel
public class TransactionsLoading {

    @Expose
    public HcContract contract;
    @Expose
    public TransactionListType listType;

    public TransactionsLoading(){}
    public TransactionsLoading(HcContract contract, TransactionListType listType){
        this.listType = listType;
        this.contract = contract;
    }

    public TransactionListType getListType() {
        return listType;
    }

    public HcContract getContract() {
        return contract;
    }
}