package vn.homecredit.hcvn.utils;

import vn.homecredit.hcvn.R;

public enum LanguageValue {
    ENGLISH("en", R.string.english),
    VIETNAMESE("vi", R.string.vietnamese);

    private String code;
    private int displayNameResId;

    LanguageValue(String code, int displayNameResId) {
        this.code = code;
        this.displayNameResId = displayNameResId;
    }

    public String getCode() {
        return code;
    }

    public int getDisplayNameResId() {
        return displayNameResId;
    }

    public static int getDisplayNameResIdFromCode(String code) {
        for (LanguageValue value : LanguageValue.values()) {
            if(value.getCode().equals(code)){
                return value.getDisplayNameResId();
            }
        }
        return VIETNAMESE.getDisplayNameResId();
    }
}
