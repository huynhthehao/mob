package vn.homecredit.hcvn.ui.notification.model;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;

public class OfferConverter {
    @TypeConverter
    public String fromObjectToString(OfferModel model) {
        return new Gson().toJson(model);
    }

    @TypeConverter
    public OfferModel fromStringToObject(String json) {
        return new Gson().fromJson(json, OfferModel.class);
    }
}
