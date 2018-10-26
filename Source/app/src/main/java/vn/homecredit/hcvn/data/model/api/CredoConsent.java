package vn.homecredit.hcvn.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CredoConsent
{
    @SerializedName("UserId")
    @Expose
    public String userId;

    @SerializedName("PhoneNumber")
    @Expose
    public String phoneNumber;

    @SerializedName("CustomerAgreed")
    @Expose
    public String customerAgreed;

    @SerializedName("CustomerAgreedDateTime")
    @Expose
    public String customerAgreedDateTime;

    @SerializedName("DeviceModel")
    @Expose
    public String deviceModel;

    @SerializedName("DeviceBrand")
    @Expose
    public String deviceBrand;

    @SerializedName("DeviceId")
    @Expose
    public String deviceId;

    @SerializedName("DevicePlatform")
    @Expose
    public String devicePlatform;

    @SerializedName("AppVersion")
    @Expose
    public String appVersion;

    public CredoConsent(){}

    public CredoConsent(String userId, String phoneNumber, String customerAgreed, String customerAgreedDateTime, String deviceModel, String deviceBrand, String deviceId, String devicePlatform, String appVersion) {
        this.userId = userId;
        this.phoneNumber = phoneNumber;
        this.customerAgreed = customerAgreed;
        this.customerAgreedDateTime = customerAgreedDateTime;
        this.deviceModel = deviceModel;
        this.deviceBrand = deviceBrand;
        this.deviceId = deviceId;
        this.devicePlatform = devicePlatform;
        this.appVersion = appVersion;
    }
}
