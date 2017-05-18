package com.synacy.moviehouse.utilities;

import com.synacy.moviehouse.exception.InvalidRequestException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static boolean onSameDate(Date startDateTime, Date endDateTime) {
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.setTime(startDateTime);
        end.setTime(endDateTime);
        return start.get(Calendar.YEAR) == end.get(Calendar.YEAR) &&
                start.get(Calendar.DAY_OF_YEAR) == end.get(Calendar.DAY_OF_YEAR);
    }

    public static String formatDateAsString(Date date) {
        return formatDateAsString(date, DEFAULT_PATTERN);
    }

    public static String formatDateAsString(Date date, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    public static Date formatStringAsDate(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_PATTERN);
        Date dateToParse;
        try {
            dateToParse = dateFormat.parse(dateStr);
        } catch (ParseException pe) {
            throw new InvalidRequestException("Cannot format the string into date. Kindly use this format 'yyyy-MM-dd HH:mm:ss' instead");
        }
        return dateToParse;
    }

    public static int getDiffInMinutes(Date startDateTime, Date endDateTime) {
        if( startDateTime == null || endDateTime == null ) {
            return 0;
        }
        return (int)((endDateTime.getTime()/60000) - (startDateTime.getTime()/60000));
    }

    public static Date getBegTimeOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getEndTimeOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

}
