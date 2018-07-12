package vn.homecredit.hcvn.data.model.api.acl;

public enum CashLoanResponseCode {
    //Common
    UNKNOWN_ERROR(-99),
    CONNECT_FAILED(-98),
    SUCCESSFUL(0),
    VALIDATION_FAILED(30),
    OTPCODE_EXPIRED(61),
    OTPCODE_EXPIRED_OR_NOT_FOUND(62),
    OTPCODE_INCORRECT(63),
    OTPCODE_IN_EFFECT(64),
    OTPCODE_MISSING(65),
    OTPCODE_OVER_VALIDATED(66),
    OTPCODE_OVER_REQUEST(67),
    OTPCODE_OVER_WRONG_INPUT(68),

    //CashLoan
    PHONENUMBER_INVALID(1004),
    IDNUMBER_INVALID(1005),
    MISSING_PHONE_NUMBER(1006),
    MISSING_ID_NUMBER(1007),
    MISSNG_DEVICE_ID(1008),

    OFFER_VALIDATETION_ERROR(1031),
    OFFER_NON_MATCHING(1032),
    OFFER_IS_BLACK_LIST(1033),
    OFFER_IS_CIC_FAILED(1034),
    OFFER_IS_TSA_FAILED(1035),
    OFFER_CODE_NOT_FOUND(1036),

    NOTIFICATION_NOT_FOUND(1041),

    APPLICATION_NOT_FOUND(1011),
    APPLICATION_BELONG_TO_OTHER_DEVICE(1012),
    APPLICATION_STILL_ACTIVE(1013),
    APPLICATION_DELETE_FAILED(1014),
    APPLICATION_WRONG_STATUS(1015),
    APPLICATION_NOT_APPROVED(1016),
    POS_WRONG_INFO(1017),
    EXISTED_CUSTOMER(1018),
    APPLICATION_REJECTED(1019),
    APPLICATION_CANCELLED(1020),
    APPLICATION_EXPIRED(1021),
    APPLICATION_APPROVED(1022),
    UPLOAD_FILE_REJECTED(1023),
    CLW_DISABLED(1024),

    BLACKLIST_NOT_PASSED(2001),
    TSA_NOT_PASSED(2002),
    NON_SUPPPORT_PHONE_NUMBER(2003),
    MISSING_DATA_OR_EXPIRED(2007),
    BOUNDCODE_NOT_FOUND(2008);

    private final int mValue;

    CashLoanResponseCode(int value) {

        mValue = value;
    }

    public int getValue() {
        return mValue;
    }

    public static CashLoanResponseCode parseFromIntValue(int intValue) {
        for(CashLoanResponseCode cashLoanResponseCode : CashLoanResponseCode.values()) {
            if (cashLoanResponseCode.getValue() == intValue)
                return cashLoanResponseCode;
        }
        return UNKNOWN_ERROR;
    }
}
