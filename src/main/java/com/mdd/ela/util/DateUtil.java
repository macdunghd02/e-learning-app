package com.mdd.ela.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {
    private DateUtil() {
    }

    public static final String DATE_FORMAT = "dd-MM-yyyy";

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static boolean isDateValid(String dateStr) {

        if (null == dateStr || dateStr.isEmpty()) {
            return false;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            sdf.setLenient(false);
            Date date = sdf.parse(dateStr);
            sdf.format(date);

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static String toValidDate(String dateStr) {
        if (null == dateStr || dateStr.isEmpty()) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            sdf.setLenient(false);
            Date date = sdf.parse(dateStr);
            return sdf.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date fromStringToValidDate(String dateStr) {
        if (null == dateStr || dateStr.isEmpty()) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            sdf.setLenient(false);
            return sdf.parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }


    public static String toValidDateTime(String dateStr) {
        if (null == dateStr || dateStr.isEmpty()) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
            sdf.setLenient(false);
            Date date = sdf.parse(dateStr);
            return sdf.format(date);

        } catch (Exception e) {
            return null;
        }
    }

    public static String toValidDateDefault(String dateStr) {
        if (null == dateStr || dateStr.isEmpty()) {
            return "0000-00-00";
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            sdf.setLenient(false);
            Date date = sdf.parse(dateStr);
            return sdf.format(date);

        } catch (Exception e) {
            return "0000-00-00";
        }
    }

    //Accept empty string as default
    public static String toValidDateDefaultEmpty(String dateStr) {
        if (null == dateStr || dateStr.isEmpty()) {
            return "";
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            sdf.setLenient(false);
            Date date = sdf.parse(dateStr);
            return sdf.format(date);

        } catch (Exception e) {
            return "";
        }
    }

    public static String toDateOnlyFromFormattedDt(String dt) {
        if (null != dt && dt.length() > 10) {
            return dt.substring(0, 10);
        }
        return dt;
    }

    public static boolean isBefore(String dateFromStr, String dateToStr){
        // Define the date formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        // Parse the string dates into LocalDate objects
        LocalDate dateFrom = LocalDate.parse(dateFromStr, formatter);
        LocalDate dateTo = LocalDate.parse(dateToStr, formatter);
        return dateFrom.isBefore(dateTo);
    }
}
