/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/3/18 4:31 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.data.model;

import vn.homecredit.hcvn.data.model.api.OtpTimerResp;

public class OtpPassParam {

    public OtpPassParam(OtpTimerResp otpTimerResp, String phoneNumber, String idNumber, OtpFlow otpFlow) {

        mOtpTimerResp = otpTimerResp;
        mPhoneNumber = phoneNumber;
        mIdNumber = idNumber;
        mOtpFlow = otpFlow;
    }

    public enum OtpFlow
    {
        SignUp(1),
        ForgotPassword(2),
        ChangePassword(4),
        MasterContract(8),
        MasterContractCreditCard(16),
        CashLoanWalkin(32);


        OtpFlow(int value) {

        }
    }

    private String phone;
    private String contract;
    private OtpTimerResp mOtpTimerResp;
    private final String mPhoneNumber;
    private final String mIdNumber;
    private final OtpFlow mOtpFlow;

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
