/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/3/18 4:31 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.data.model;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import vn.homecredit.hcvn.data.model.api.OtpTimerResp;

@Parcel
public class OtpPassParam {

    private OtpTimerResp otpTimerResp;
    private final String phoneNumber;
    private final String contractId;
    private String otp;
    private OtpFlow otpFlow;

    @ParcelConstructor
    public OtpPassParam(OtpTimerResp otpTimerResp, String phoneNumber, String contractId, OtpFlow otpFlow, String otp) {
        this.otpTimerResp = otpTimerResp;
        this.phoneNumber = phoneNumber;
        this.contractId = contractId;
        this.otpFlow = otpFlow;
        this.otp = otp;
    }
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

    public String getOtp() {
        return otp;
    }

    public void setOtpTimerResp(OtpTimerResp otpTimerResp) {
        this.otpTimerResp = otpTimerResp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public void setOtpFlow(OtpFlow otpFlow) {
        this.otpFlow = otpFlow;
    }

}

