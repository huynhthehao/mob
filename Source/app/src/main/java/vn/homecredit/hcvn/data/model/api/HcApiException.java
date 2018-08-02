package vn.homecredit.hcvn.data.model.api;

import com.androidnetworking.error.ANError;

import vn.homecredit.hcvn.data.model.api.base.BaseApiResponse;

public class HcApiException extends Throwable {
    public static final int ERROR_CODE_UNAUTHORIZED = 401;
    public static final int ERROR_UNKNOWN = 1;
    private int errorResponseCode;
    private String errorResponseMessage;

    public HcApiException(Throwable throwable, Class clazz) {
        mapException(throwable, clazz);
    }

    public HcApiException(int errorResponseCode, String errorResponseMessage) {

        this.errorResponseCode = errorResponseCode;
        this.errorResponseMessage = errorResponseMessage;
    }

    public int getErrorResponseCode() {
        return errorResponseCode;
    }

    public String getErrorResponseMessage() {
        return errorResponseMessage;
    }

    private<T> void mapException(Throwable throwable, Class<T> clazz) {
        if (throwable instanceof ANError) {
            T errorObject = ((ANError) throwable).getErrorAsObject(clazz);
            if (errorObject instanceof TokenResp) {
                errorResponseCode = ((TokenResp) errorObject).getResponseCode();
                errorResponseMessage = ((TokenResp) errorObject).getErrorDescription();
            }else if (errorObject instanceof VersionResp){
                errorResponseCode = ((VersionResp) errorObject).getResponseCode();
                errorResponseMessage = ((VersionResp) errorObject).getResponseMessage();
            }else if (errorObject instanceof ProfileResp){
                errorResponseCode = ((ProfileResp) errorObject).getResponseCode();
                errorResponseMessage = "No error message";
            }else if (errorObject instanceof OtpTimerResp){
                errorResponseCode = ((OtpTimerResp) errorObject).getResponseCode();
                errorResponseMessage = ((OtpTimerResp) errorObject).getResponseMessage();
            }else if (errorObject instanceof BaseApiResponse){
                errorResponseCode = ((BaseApiResponse) errorObject).getResponseCode();
                errorResponseMessage = ((BaseApiResponse) errorObject).getResponseMessage();
            }
        }else {
            errorResponseCode = ERROR_UNKNOWN;
            errorResponseMessage = throwable.getMessage();
        }
    }
}
