package vn.homecredit.hcvn.ui.notification.model;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class ClwResultConverter {
    @TypeConverter
    public String fromObjectToString(ClwResult clwResult) {
        return new Gson().toJson(clwResult);
    }

    @TypeConverter
    public ClwResult fromStringToObject(String json) {
        return new Gson().fromJson(json, ClwResult.class);
    }
}
