package vn.homecredit.hcvn.utils;

import vn.homecredit.hcvn.R;

public enum CountryValue {
    ENGLISH("en", R.string.english, R.drawable.ic_flag_en),
    VIETNAMESE("vi", R.string.vietnamese, R.drawable.ic_flag_vn);

    private String languageCode;
    private int displayNameResId;
    private int flagResId;


    CountryValue(String code, int displayNameResId, int flagResId) {
        this.languageCode = code;
        this.displayNameResId = displayNameResId;
        this.flagResId = flagResId;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public int getDisplayNameResId() {
        return displayNameResId;
    }

    public static int getDisplayNameResIdFromCode(String code) {
        for (CountryValue value : CountryValue.values()) {
            if(value.getLanguageCode().equals(code)){
                return value.getDisplayNameResId();
            }
        }
        return VIETNAMESE.getDisplayNameResId();
    }

    public int getFlagResId() {
        return flagResId;
    }
}
