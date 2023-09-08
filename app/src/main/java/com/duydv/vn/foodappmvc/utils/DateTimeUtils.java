package com.duydv.vn.foodappmvc.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {
    private static final String DEFAULT_FORMAT_DATE = "dd-MM-yyyy,hh:mm a";
    private static final String DEFAULT_FORMAT_DATE_2 = "dd/MM/yyyy";
    public static String convertTimeToDate(long time) {
        String strDate = "";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_FORMAT_DATE, Locale.ENGLISH);
            Date date = (new Date(time));
            strDate = simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    public static String convertTimeToDate_2(long time) {
        String result = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT_DATE_2, Locale.ENGLISH);
            Date date = (new Date(time));
            result = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String convertDate2ToTime(String strDate) {
        String result = "";
        if (strDate != null) {
            try {
                SimpleDateFormat format = new SimpleDateFormat(DEFAULT_FORMAT_DATE_2, Locale.ENGLISH);
                Date date = format.parse(strDate);
                if (date != null) {
                    Long time = date.getTime() / 1000;
                    result = String.valueOf(time);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
