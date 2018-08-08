/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 8/7/18 3:38 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.data.model.api.creditcard;


import com.google.gson.annotations.Expose;
import org.parceler.Parcel;
import vn.homecredit.hcvn.data.model.enums.TransactionListType;

@Parcel
public class TransactionDetailLoading
{
    @Expose
    public Transaction transaction;

    @Expose
    public TransactionListType listType;

    public TransactionDetailLoading(){}

    public TransactionDetailLoading(Transaction transaction, TransactionListType listType) {
        this.listType = listType;
        this.transaction = transaction;
    }
}