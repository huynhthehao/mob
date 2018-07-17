package vn.homecredit.hcvn.data.model.api;

import com.google.gson.Gson;

public class SignupResp {


    public static final int RESPONSE_CODE_SUCCESS = 0;
    public static final int RESPONSE_CODE_IN_EFFECT = 64;

    /**
     * response_code : -99
     * response_string_code : UnknownError
     * response_message : Lỗi  hệ thống, vui lòng thử lại sau
     */

    private int response_code;
    private String response_string_code;
    private String response_message;

    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }

    public String getResponse_string_code() {
        return response_string_code;
    }

    public void setResponse_string_code(String response_string_code) {
        this.response_string_code = response_string_code;
    }

    public String getResponse_message() {
        return response_message;
    }

    public void setResponse_message(String response_message) {
        this.response_message = response_message;
    }

    public boolean isVerified() {
        return response_code == RESPONSE_CODE_SUCCESS
                || response_code == RESPONSE_CODE_IN_EFFECT;
    }

    @Override
    public String toString() {
        return "SignupResp{" +
                "response_code=" + response_code +
                ", response_string_code='" + response_string_code + '\'' +
                ", response_message='" + response_message + '\'' +
                '}';
    }
}
