/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 8/6/18 11:10 AM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;

import vn.homecredit.hcvn.data.model.api.contract.HcContract;
import vn.homecredit.hcvn.data.model.enums.TransactionListType;
import vn.homecredit.hcvn.helpers.UiHelper;

@Parcel
public class Transaction {

    public static final String CreditDirection = "credit";

    @SerializedName("amount")
    @Expose
    public int amount;

    @SerializedName("date")
    @Expose
    public Date transactionDate;

    @SerializedName("PostingDate")
    @Expose
    public Date status;

    @SerializedName("description")
    @Expose
    public String description;

    @SerializedName("fullDescription")
    @Expose
    public String fullDescription;

    @SerializedName("direction")
    @Expose
    public String direction;

    @SerializedName("isCancelled")
    @Expose
    public boolean isCancelled;

    @SerializedName("isHold")
    @Expose
    public boolean isHold;


    public String getAmountDisplay() {
        String headSign = direction.equalsIgnoreCase(CreditDirection) ? "+" : "-";
        return headSign + UiHelper.getCurrencyFormat(amount);
    }

    public static class TransactionsLoading
    {
        private HcContract contract;
        private TransactionListType listType;

        public TransactionsLoading(HcContract contract, TransactionListType listType){
            this.listType = listType;
            this.contract = contract;
        }

        public HcContract getContract() {
            return contract;
        }

        public TransactionListType getListType() {
            return listType;
        }
    }

    public class TransactionDetailLoading
    {
        public Transaction transaction;
        public TransactionListType listType;
    }
}