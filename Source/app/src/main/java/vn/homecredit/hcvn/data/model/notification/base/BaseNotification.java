/*
 * BaseNotification.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/15/18 4:28 PM
 */

package vn.homecredit.hcvn.data.model.notification.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseNotification {

    @SerializedName("Title")
    @Expose
    private String mTitle;

    @SerializedName("MessageText")
    @Expose
    private String mMessageText;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getMessageText() {
        return mMessageText;
    }

    public void setMessageText(String messageText) {
        mMessageText = messageText;
    }
}
