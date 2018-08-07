package vn.homecredit.hcvn.utils;

import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimeHelper {
    public static String getTimeAgo(String notificationTime) {
        String value = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        long time = 0;
        try {
            time = sdf.parse(notificationTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (time == 0) {
            return value;
        }
        long now = System.currentTimeMillis();
        value = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS).toString();
        return value;
    }
}
