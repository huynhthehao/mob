/*
 * VersionResp.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/12/18 1:24 PM
 */

package vn.homecredit.hcvn.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vn.homecredit.hcvn.data.model.api.RateOptions;

public class VersionResp {

    @SerializedName("Platform")
    @Expose
    private String platform;
    @SerializedName("Version")
    @Expose
    private String version;
    @SerializedName("MaintenanceStart")
    @Expose
    private String maintenanceStart;
    @SerializedName("MaintenanceEnd")
    @Expose
    private String maintenanceEnd;
    @SerializedName("UnderMaintenance")
    @Expose
    private Boolean underMaintenance;
    @SerializedName("CustomerSupportPhone")
    @Expose
    private String customerSupportPhone;
    @SerializedName("MarketingPhone")
    @Expose
    private String marketingPhone;
    @SerializedName("ClwoQRScanEnabled")
    @Expose
    private Boolean clwoQRScanEnabled;
    @SerializedName("RateOptions")
    @Expose
    private RateOptions rateOptions;
    @SerializedName("TrackingEnabled")
    @Expose
    private Boolean trackingEnabled;
    @SerializedName("TrackingSize")
    @Expose
    private Integer trackingSize;
    @SerializedName("PasscodeEnabled")
    @Expose
    private Boolean passcodeEnabled;
    @SerializedName("PasscodeExpiredInMinutes")
    @Expose
    private Integer passcodeExpiredInMinutes;
    @SerializedName("Settings")
    @Expose
    private ServerSettings settings;
    @SerializedName("CredoEnabled")
    @Expose
    private Boolean credoEnabled;
    @SerializedName("AppExpiredInMinutes")
    @Expose
    private Integer appExpiredInMinutes;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMaintenanceStart() {
        return maintenanceStart;
    }

    public void setMaintenanceStart(String maintenanceStart) {
        this.maintenanceStart = maintenanceStart;
    }

    public String getMaintenanceEnd() {
        return maintenanceEnd;
    }

    public void setMaintenanceEnd(String maintenanceEnd) {
        this.maintenanceEnd = maintenanceEnd;
    }

    public Boolean getUnderMaintenance() {
        return underMaintenance;
    }

    public void setUnderMaintenance(Boolean underMaintenance) {
        this.underMaintenance = underMaintenance;
    }

    public String getCustomerSupportPhone() {
        return customerSupportPhone;
    }

    public void setCustomerSupportPhone(String customerSupportPhone) {
        this.customerSupportPhone = customerSupportPhone;
    }

    public String getMarketingPhone() {
        return marketingPhone;
    }

    public void setMarketingPhone(String marketingPhone) {
        this.marketingPhone = marketingPhone;
    }

    public Boolean getClwoQRScanEnabled() {
        return clwoQRScanEnabled;
    }

    public void setClwoQRScanEnabled(Boolean clwoQRScanEnabled) {
        this.clwoQRScanEnabled = clwoQRScanEnabled;
    }

    public RateOptions getRateOptions() {
        return rateOptions;
    }

    public void setRateOptions(RateOptions rateOptions) {
        this.rateOptions = rateOptions;
    }

    public Boolean getTrackingEnabled() {
        return trackingEnabled;
    }

    public void setTrackingEnabled(Boolean trackingEnabled) {
        this.trackingEnabled = trackingEnabled;
    }

    public Integer getTrackingSize() {
        return trackingSize;
    }

    public void setTrackingSize(Integer trackingSize) {
        this.trackingSize = trackingSize;
    }

    public Boolean getPasscodeEnabled() {
        return passcodeEnabled;
    }

    public void setPasscodeEnabled(Boolean passcodeEnabled) {
        this.passcodeEnabled = passcodeEnabled;
    }

    public Integer getPasscodeExpiredInMinutes() {
        return passcodeExpiredInMinutes;
    }

    public void setPasscodeExpiredInMinutes(Integer passcodeExpiredInMinutes) {
        this.passcodeExpiredInMinutes = passcodeExpiredInMinutes;
    }

    public ServerSettings getSettings() {
        return settings;
    }

    public void setSettings(ServerSettings settings) {
        this.settings = settings;
    }

    public Boolean getCredoEnabled() {
        return credoEnabled;
    }

    public void setCredoEnabled(Boolean credoEnabled) {
        this.credoEnabled = credoEnabled;
    }

    public Integer getAppExpiredInMinutes() {
        return appExpiredInMinutes;
    }

    public void setAppExpiredInMinutes(Integer appExpiredInMinutes) {
        this.appExpiredInMinutes = appExpiredInMinutes;
    }

}