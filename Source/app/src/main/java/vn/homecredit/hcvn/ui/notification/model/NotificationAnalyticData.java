package vn.homecredit.hcvn.ui.notification.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationAnalyticData {
    @SerializedName("UtmSource")
    @Expose
    private String utmSource;
    @SerializedName("UtmlMedium")
    @Expose
    private String utmMedium;
    @SerializedName("UtmCampaign")
    @Expose
    private String utmCampaign;
    @SerializedName("UtmContent")
    @Expose
    private String utmContent = "";
    @SerializedName("NotificationContent")
    @Expose
    private String notificationContent;

    public String getUtmSource() {
        return utmSource;
    }

    public void setUtmSource(String utmSource) {
        this.utmSource = utmSource;
    }

    public String getUtmMedium() {
        return utmMedium;
    }

    public void setUtmMedium(String utmMedium) {
        this.utmMedium = utmMedium;
    }

    public String getUtmCampaign() {
        return utmCampaign;
    }

    public void setUtmCampaign(String utmCampaign) {
        this.utmCampaign = utmCampaign;
    }

    public String getUtmContent() {
        return utmContent;
    }

    public void setUtmContent(String utmContent) {
        this.utmContent = utmContent;
    }

    public String getNotificationContent() {
        return notificationContent;
    }

    public void setNotificationContent(String notificationContent) {
        this.notificationContent = notificationContent;
    }
}
