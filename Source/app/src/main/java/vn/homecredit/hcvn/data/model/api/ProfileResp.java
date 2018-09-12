/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/6/18 1:05 PM, by quan.p@homecredit.vn
 */

package vn.homecredit.hcvn.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

import vn.homecredit.hcvn.ui.notification.model.OfferModel;

public class ProfileResp {
    public static final int RESPONSE_CODE_SUCCESS = 0;

    @SerializedName("data")
    @Expose
    private ProfileRespData data;
    @SerializedName("response_code")
    @Expose
    private Integer responseCode;
    @SerializedName("response_message")
    @Expose
    private String responseMessage;

    public ProfileRespData getData() {
        return data;
    }

    public void setData(ProfileRespData data) {
        this.data = data;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public static class ProfileRespData {
        @SerializedName("UserTrackingId")
        @Expose
        private String userTrackingId;
        @SerializedName("UserId")
        @Expose
        private String userId;
        @SerializedName("CUID")
        @Expose
        private String cUID;
        @SerializedName("UserName")
        @Expose
        private String userName;
        @SerializedName("FullName")
        @Expose
        private String fullName;
        @SerializedName("PhoneNumber")
        @Expose
        private String phoneNumber;
        @SerializedName("IdNumber")
        @Expose
        private String idNumber;
        @SerializedName("ContractNumber")
        @Expose
        private String contractNumber;
        @SerializedName("Address")
        @Expose
        private String address;
        @SerializedName("Gender")
        @Expose
        private String gender;
        @SerializedName("IsNewUser")
        @Expose
        private Boolean isNewUser;
        @SerializedName("GenderText")
        @Expose
        private String genderText;
        @SerializedName("TicketCount")
        @Expose
        private Integer ticketCount;
        @SerializedName("NotificationCount")
        @Expose
        private Integer notificationCount;
        @SerializedName("DaysFromSignUp")
        @Expose
        private Integer daysFromSignUp;
        @SerializedName("MinimumDaysForRating")
        @Expose
        private Integer minimumDaysForRating;
        @SerializedName("TimeNow")
        @Expose
        private String timeNow;
        @SerializedName("Settings")
        @Expose
        private List<Setting> settings = null;
        @SerializedName("Offer")
        @Expose
        private Offer offer;
        @SerializedName("PasswordExpiration")
        @Expose
        private PasswordExpiration passwordExpiration;

        public String getUserTrackingId() {
            return userTrackingId;
        }

        public void setUserTrackingId(String userTrackingId) {
            this.userTrackingId = userTrackingId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getcUID() {
            return cUID;
        }

        public void setcUID(String cUID) {
            this.cUID = cUID;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getIdNumber() {
            return idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber;
        }

        public String getContractNumber() {
            return contractNumber;
        }

        public void setContractNumber(String contractNumber) {
            this.contractNumber = contractNumber;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public Boolean getNewUser() {
            return isNewUser;
        }

        public void setNewUser(Boolean newUser) {
            isNewUser = newUser;
        }

        public String getGenderText() {
            return genderText;
        }

        public void setGenderText(String genderText) {
            this.genderText = genderText;
        }

        public Integer getTicketCount() {
            return ticketCount;
        }

        public void setTicketCount(Integer ticketCount) {
            this.ticketCount = ticketCount;
        }

        public Integer getNotificationCount() {
            return notificationCount;
        }

        public void setNotificationCount(Integer notificationCount) {
            this.notificationCount = notificationCount;
        }

        public Integer getDaysFromSignUp() {
            return daysFromSignUp;
        }

        public void setDaysFromSignUp(Integer daysFromSignUp) {
            this.daysFromSignUp = daysFromSignUp;
        }

        public Integer getMinimumDaysForRating() {
            return minimumDaysForRating;
        }

        public void setMinimumDaysForRating(Integer minimumDaysForRating) {
            this.minimumDaysForRating = minimumDaysForRating;
        }

        public String getTimeNow() {
            return timeNow;
        }

        public void setTimeNow(String timeNow) {
            this.timeNow = timeNow;
        }

        public List<Setting> getSettings() {
            return settings;
        }

        public void setSettings(List<Setting> settings) {
            this.settings = settings;
        }

        public OfferModel getOffer() {
            return offer;
        }

        public void setOffer(OfferModel offer) {
            this.offer = offer;
        }

        public PasswordExpiration getPasswordExpiration() {
            return passwordExpiration;
        }

        public void setPasswordExpiration(PasswordExpiration passwordExpiration) {
            this.passwordExpiration = passwordExpiration;
        }
    }

    public class Setting {

        @SerializedName("DeviceId")
        @Expose
        private String deviceId;
        @SerializedName("Active")
        @Expose
        private Boolean active;
        @SerializedName("PushNotificationConsent")
        @Expose
        private Boolean pushNotificationConsent;
        @SerializedName("Model")
        @Expose
        private String model;
        @SerializedName("Platform")
        @Expose
        private String platform;

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public Boolean getActive() {
            return active;
        }

        public void setActive(Boolean active) {
            this.active = active;
        }

        public Boolean getPushNotificationConsent() {
            return pushNotificationConsent;
        }

        public void setPushNotificationConsent(Boolean pushNotificationConsent) {
            this.pushNotificationConsent = pushNotificationConsent;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

    }

    public class PasswordExpiration {

        @SerializedName("ExpiredDate")
        @Expose
        private String expiredDate;
        @SerializedName("DurationDays")
        @Expose
        private Integer durationDays;
        @SerializedName("IsExpiredSoon")
        @Expose
        private Boolean isExpiredSoon;

        public String getExpiredDate() {
            return expiredDate;
        }

        public void setExpiredDate(String expiredDate) {
            this.expiredDate = expiredDate;
        }

        public Integer getDurationDays() {
            return durationDays;
        }

        public void setDurationDays(Integer durationDays) {
            this.durationDays = durationDays;
        }

        public Boolean getIsExpiredSoon() {
            return isExpiredSoon;
        }

        public void setIsExpiredSoon(Boolean isExpiredSoon) {
            this.isExpiredSoon = isExpiredSoon;
        }

    }
}
