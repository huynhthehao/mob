package vn.homecredit.hcvn.data.model.api;

import android.content.Context;

import com.androidnetworking.error.ANError;

import java.io.IOException;
import java.net.UnknownHostException;

import vn.homecredit.hcvn.HCVNApp;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.api.base.BaseApiResponse;

public class HcApiException extends Throwable {

    public static final int ERROR_CODE_UNAUTHORIZED = 401;
    public static final int ERROR_CODE_BAD_REQUEST = 400;
    public static final int ERROR_CODE_SERVICE_NOT_AVAILABLE = 503;
    public static final int ERROR_CODE_INTERNAL = 500;
    public static final int ERROR_UNKNOWN = 1;
    private int errorResponseCode;
    private String errorResponseMessage;
    /**
     * Just add this variable for remote logging because we'll transform original throwable to local throwable
     */
    private Throwable serverThrowable;

    public HcApiException(Throwable throwable, Class clazz) {
        serverThrowable = throwable;
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
            if (((ANError) throwable).getErrorCode() == ERROR_CODE_UNAUTHORIZED) {
                errorResponseCode = ERROR_CODE_UNAUTHORIZED;
                errorResponseMessage = ((ANError) throwable).getErrorBody();
                return;
            }

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
            }else {
                errorResponseCode = ((ANError) throwable).getErrorCode();
                errorResponseMessage = ((ANError) throwable).getErrorBody();
            }

            Throwable innerError = throwable.getCause();
            if(innerError instanceof UnknownHostException){
                Context currentContext = HCVNApp.getContext();
                errorResponseCode = ERROR_CODE_SERVICE_NOT_AVAILABLE;
                errorResponseMessage = currentContext.getString(R.string.unable_to_connect_message);
            }
        }else {
            errorResponseCode = ERROR_UNKNOWN;
            errorResponseMessage = throwable.getMessage();
        }
    }

    public boolean isErrorResponseEmpty() {
        return errorResponseMessage == null;
    }
}
