/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/3/18 4:31 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.data.model;

import vn.homecredit.hcvn.data.model.api.OtpTimerResp;

public class OtpPassParam {
    private String phone;
    private String contract;
    private OtpTimerResp mOtpTimerResp;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public OtpTimerResp getOtpTimerResp() {
        return mOtpTimerResp;
    }

    public void setOtpTimerResp(OtpTimerResp otpTimerResp) {
        mOtpTimerResp = otpTimerResp;
    }
}
