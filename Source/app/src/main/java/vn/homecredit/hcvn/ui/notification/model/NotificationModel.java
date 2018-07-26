package vn.homecredit.hcvn.ui.notification.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import vn.homecredit.hcvn.BuildConfig;
import vn.homecredit.hcvn.ui.notification.NotificationType;

public class NotificationModel {
    @SerializedName("Id")
    @Expose
    private String id;

    @SerializedName("ContractNumber")
    @Expose
    private String contractNumber;

    @SerializedName("ContractSKP")
    @Expose
    private String contractSKP;

    @SerializedName("Data")
    @Expose
    private Object data;

    @SerializedName("UserId")
    @Expose
    private String userId;

    @SerializedName("Read")
    @Expose
    private boolean read;

    @SerializedName("Time")
    @Expose
    private String time;

    @SerializedName("Type")
    @Expose
    private int type;

    @SerializedName("TimeText")
    @Expose
    private String timeText;

    @SerializedName("PosterUrl")
    @Expose
    private String posterUrl;

    @SerializedName("MarketingUrlVi")
    @Expose
    private String marketingUrlVi;

    @SerializedName("MarketingUrlEn")
    @Expose
    private String marketingUrlEn;

    @SerializedName("Title")
    @Expose
    private String title;

    @SerializedName("MessageText")
    @Expose
    private String messageText;

    @SerializedName("MessageSpan")
    @Expose
    private List<MessageSpan> messageSpan;

    @SerializedName("Offer")
    @Expose
    private Object offer;

    private ClwResult clwResult;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getContractSKP() {
        return contractSKP;
    }

    public void setContractSKP(String contractSKP) {
        this.contractSKP = contractSKP;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTimeText() {
        return timeText;
    }

    public void setTimeText(String timeText) {
        this.timeText = timeText;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getMarketingUrlVi() {
        return marketingUrlVi;
    }

    public void setMarketingUrlVi(String marketingUrlVi) {
        this.marketingUrlVi = marketingUrlVi;
    }

    public String getMarketingUrlEn() {
        return marketingUrlEn;
    }

    public void setMarketingUrlEn(String marketingUrlEn) {
        this.marketingUrlEn = marketingUrlEn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public List<MessageSpan> getMessageSpan() {
        return messageSpan;
    }

    public void setMessageSpan(List<MessageSpan> messageSpan) {
        this.messageSpan = messageSpan;
    }

    public Object getOffer() {
        return offer;
    }

    public void setOffer(Object offer) {
        this.offer = offer;
    }

    public ClwResult getClwResult() {
        return clwResult;
    }

    public void setClwResult(ClwResult clwResult) {
        this.clwResult = clwResult;
    }

    public String getVersion() {
        if (type == NotificationType.UPDATE.getType() && messageSpan.size() > 1) {
            return messageSpan.get(1).getText().trim();
        }
        return "";
    }

    public boolean isNeedToUpdate() {
        if (getVersion().equalsIgnoreCase("")) {
            return false;
        }
        if (!getVersion().equalsIgnoreCase(BuildConfig.VERSION_NAME)) {
            return true;
        }
        return false;
    }
}
