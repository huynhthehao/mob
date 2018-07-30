package vn.homecredit.hcvn.ui.notification.model;

import android.arch.persistence.room.TypeConverter;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class MessageSpanConverter {
    @TypeConverter
    public String fromListToString(List<MessageSpan> list) {
        return new Gson().toJson(list);
    }

    @TypeConverter
    public List<MessageSpan> fromStringToList(String json) {
        return new Gson().fromJson(json, new TypeToken<List<MessageSpan>>() {
        }.getType());
    }
}
