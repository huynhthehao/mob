/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/3/18 4:31 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.data.model;

import vn.homecredit.hcvn.data.model.api.OtpTimerResp;

public class OtpPassParam {

    private OtpTimerResp otpTimerResp;
    private final String phoneNumber;
    private final String contractId;
    private OtpFlow otpFlow;

    public OtpPassParam(OtpTimerResp otpTimerResp, String phoneNumber, String contractId, OtpFlow otpFlow) {
        this.otpTimerResp = otpTimerResp;
        this.phoneNumber = phoneNumber;
        this.contractId = contractId;
        this.otpFlow = otpFlow;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }


    public OtpTimerResp getOtpTimerResp() {
        return otpTimerResp;
    }


    public OtpFlow getOtpFlow() {
        return otpFlow;
    }

    public String getContractId() {
        return contractId;
    }
}

