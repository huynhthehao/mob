package vn.homecredit.hcvn.data.model.tracking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InternalTrackingModel {
    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("Cuid")
    @Expose
    private String cuid;
    @SerializedName("Source")
    @Expose
    private String source;
    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("Action")
    @Expose
    private String action;
    @SerializedName("Label")
    @Expose
    private String label;
    @SerializedName("Value")
    @Expose
    private String value;
    @SerializedName("Page")
    @Expose
    private String page;
    @SerializedName("Device")
    @Expose
    private String device;
    @SerializedName("DeviceName")
    @Expose
    private String deviceName;
    @SerializedName("DeviceId")
    @Expose
    private String deviceId;
    @SerializedName("DeviceModel")
    @Expose
    private String deviceModel;
    @SerializedName("DeviceBrand")
    @Expose
    private String deviceBrand;
    @SerializedName("Platform")
    @Expose
    private String platform;
    @SerializedName("Language")
    @Expose
    private String language;
    @SerializedName("Duration")
    @Expose
    private Integer duration;
    @SerializedName("AppVersion")
    @Expose
    private String appVersion;
    @SerializedName("UtmSource")
    @Expose
    private String utmSource;
    @SerializedName("UtmContent")
    @Expose
    private String utmContent;
    @SerializedName("Longitude")
    @Expose
    private Integer longitude;
    @SerializedName("Latitude")
    @Expose
    private Integer latitude;
    @SerializedName("UtmlMedium")
    @Expose
    private String utmlMedium;
    @SerializedName("UtmCampaign")
    @Expose
    private String utmCampaign;
    @SerializedName("ActionTime")
    @Expose
    private String actionTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCuid() {
        return cuid;
    }

    public void setCuid(String cuid) {
        this.cuid = cuid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getDeviceBrand() {
        return deviceBrand;
    }

    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getUtmSource() {
        return utmSource;
    }

    public void setUtmSource(String utmSource) {
        this.utmSource = utmSource;
    }

    public String getUtmContent() {
        return utmContent;
    }

    public void setUtmContent(String utmContent) {
        this.utmContent = utmContent;
    }

    public Integer getLongitude() {
        return longitude;
    }

    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
    }

    public Integer getLatitude() {
        return latitude;
    }

    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }

    public String getUtmlMedium() {
        return utmlMedium;
    }

    public void setUtmlMedium(String utmlMedium) {
        this.utmlMedium = utmlMedium;
    }

    public String getUtmCampaign() {
        return utmCampaign;
    }

    public void setUtmCampaign(String utmCampaign) {
        this.utmCampaign = utmCampaign;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

}
