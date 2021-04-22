package com.achao.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static final String YYYYMMDDHHMMSS_FORMAT = "yyyy-MM-dd HH:mm:ss:SSS";

    public static String format(Date date) {
        return format(date, YYYYMMDDHHMMSS_FORMAT);
    }

    private static String format(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String ts = sdf.format(date);
        ts = ts.replaceAll("-", "");
        ts = ts.replaceAll(" ", "");
        ts = ts.replaceAll(":", "");
        return ts;
    }

    public static Timestamp currentTime() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static int getWorkTime(Date create) {
        long time = System.currentTimeMillis() - create.getTime();
        return (int) (time / 1000 / 60 );
    }
}
