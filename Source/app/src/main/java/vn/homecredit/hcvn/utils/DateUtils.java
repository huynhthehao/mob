package vn.homecredit.hcvn.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static final String FORMAT_UTC = "yyyy-MM-dd'T'hh:mm:ss";
    public static final String FORMAT_SIMPLE = "dd/MM/yyyy";

    public static String convertDateFromUTCToSimple(String date) {
        return convertDate(date, FORMAT_UTC, FORMAT_SIMPLE);
    }

    public static String simplifyDateString(String utcDateString) {
        return convertDate(utcDateString, FORMAT_UTC, FORMAT_SIMPLE);
    }

    public static String convertDateFromUTC(String date, String newFormat) {
       return convertDate(date, FORMAT_UTC, newFormat);
    }

    public static String convertDate(String date, String oldFormat, String newFormat) {
        if (oldFormat == null || newFormat == null) {
            return "";
        }
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(oldFormat);
            SimpleDateFormat displayFormat = new SimpleDateFormat(newFormat);
            Date serverDate = simpleDateFormat.parse(date);
            String displayDate = displayFormat.format(serverDate);
            return displayDate;

        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
}
