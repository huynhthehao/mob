package vn.homecredit.hcvn.ui.notification.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MessageSpan {
    @SerializedName("Text")
    @Expose
    private String text;

    @SerializedName("FontAttributes")
    @Expose
    private String fontAttributes;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFontAttributes() {
        return fontAttributes;
    }

    public void setFontAttributes(String fontAttributes) {
        this.fontAttributes = fontAttributes;
    }
}
