/*
 * ServerSettings.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/12/18 1:46 PM
 */

package vn.homecredit.hcvn.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServerSettings {

    @SerializedName("OpenApiClientId")
    @Expose
    private String openApiClientId;
    @SerializedName("OpenApiMobileRedirectUrl")
    @Expose
    private String openApiMobileRedirectUrl;
    @SerializedName("OpenApiUrl")
    @Expose
    private String openApiUrl;
    @SerializedName("TermsAndConditionsForEServicesUrl")
    @Expose
    private String termsAndConditionsForEServicesUrl;
    @SerializedName("GuidanceForInternetBankingServiceUrl")
    @Expose
    private String guidanceForInternetBankingServiceUrl;
    @SerializedName("MobileAppManualUrl")
    @Expose
    private String mobileAppManualUrl;
    @SerializedName("CredoConsentUrlEn")
    @Expose
    private String credoConsentUrlEn;
    @SerializedName("CredoConsentUrlVi")
    @Expose
    private String credoConsentUrlVi;
    @SerializedName("System")
    @Expose
    private String system;
    @SerializedName("CredoRate")
    @Expose
    private String credoRate;
    @SerializedName("CredoConsentEn")
    @Expose
    private String credoConsentEn;
    @SerializedName("CredoConsentVi")
    @Expose
    private String credoConsentVi;

    public String getOpenApiClientId() {
        return openApiClientId;
    }

    public void setOpenApiClientId(String openApiClientId) {
        this.openApiClientId = openApiClientId;
    }

    public String getOpenApiMobileRedirectUrl() {
        return openApiMobileRedirectUrl;
    }

    public void setOpenApiMobileRedirectUrl(String openApiMobileRedirectUrl) {
        this.openApiMobileRedirectUrl = openApiMobileRedirectUrl;
    }

    public String getOpenApiUrl() {
        return openApiUrl;
    }

    public void setOpenApiUrl(String openApiUrl) {
        this.openApiUrl = openApiUrl;
    }

    public String getTermsAndConditionsForEServicesUrl() {
        return termsAndConditionsForEServicesUrl;
    }

    public void setTermsAndConditionsForEServicesUrl(String termsAndConditionsForEServicesUrl) {
        this.termsAndConditionsForEServicesUrl = termsAndConditionsForEServicesUrl;
    }

    public String getGuidanceForInternetBankingServiceUrl() {
        return guidanceForInternetBankingServiceUrl;
    }

    public void setGuidanceForInternetBankingServiceUrl(String guidanceForInternetBankingServiceUrl) {
        this.guidanceForInternetBankingServiceUrl = guidanceForInternetBankingServiceUrl;
    }

    public String getMobileAppManualUrl() {
        return mobileAppManualUrl;
    }

    public void setMobileAppManualUrl(String mobileAppManualUrl) {
        this.mobileAppManualUrl = mobileAppManualUrl;
    }

    public String getCredoConsentUrlEn() {
        return credoConsentUrlEn;
    }

    public void setCredoConsentUrlEn(String credoConsentUrlEn) {
        this.credoConsentUrlEn = credoConsentUrlEn;
    }

    public String getCredoConsentUrlVi() {
        return credoConsentUrlVi;
    }

    public void setCredoConsentUrlVi(String credoConsentUrlVi) {
        this.credoConsentUrlVi = credoConsentUrlVi;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getCredoRate() {
        return credoRate;
    }

    public void setCredoRate(String credoRate) {
        this.credoRate = credoRate;
    }

    public String getCredoConsentEn() {
        return credoConsentEn;
    }

    public void setCredoConsentEn(String credoConsentEn) {
        this.credoConsentEn = credoConsentEn;
    }

    public String getCredoConsentVi() {
        return credoConsentVi;
    }

    public void setCredoConsentVi(String credoConsentVi) {
        this.credoConsentVi = credoConsentVi;
    }

}
