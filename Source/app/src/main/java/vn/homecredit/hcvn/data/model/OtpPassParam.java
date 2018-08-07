/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/3/18 4:31 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.data.model;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import vn.homecredit.hcvn.data.model.api.OtpTimerResp;
import vn.homecredit.hcvn.data.model.api.contract.MasterContract;
import vn.homecredit.hcvn.data.model.api.contract.MasterContractVerifyDataResp;
import vn.homecredit.hcvn.data.model.enums.OtpFlow;

@Parcel
public class OtpPassParam {

    private MasterContract masterContract;
    private OtpTimerResp otpTimerResp;
    private String phoneNumber;
    private String contractId;
    private String otp;
    private OtpFlow otpFlow;
    private MasterContractVerifyDataResp masterContractVerifyDataResp;

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

    public OtpPassParam(OtpTimerResp otpTimerResp, MasterContract masterContract) {
        this.masterContract = masterContract;
        this.otpTimerResp = otpTimerResp;
        this.phoneNumber = masterContract.getCustomerPrimaryPhoneNumber();
        this.contractId = masterContract.getContractNumber();
        this.otpFlow = masterContract.isCreditCardContract() ? OtpFlow.MASTER_CONTRACT_CREDIT_CARD : OtpFlow.MASTER_CONTRACT;
        this.otpTimerResp = otpTimerResp;
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

    public MasterContract getMasterContract() {
        return masterContract;
    }

    public MasterContractVerifyDataResp getMasterContractVerifyDataResp() {
        return masterContractVerifyDataResp;
    }

    public void setMasterContractVerifyDataResp(MasterContractVerifyDataResp masterContractVerifyDataResp) {
        this.masterContractVerifyDataResp = masterContractVerifyDataResp;
    }
}

