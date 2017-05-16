package com.synacy.moviehouse.utilities;

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

    public static int getDiffInMinutes(Date startDateTime, Date endDateTime) {
        if( startDateTime == null || endDateTime == null ) {
            return 0;
        }
        return (int)((endDateTime.getTime()/60000) - (startDateTime.getTime()/60000));
    }

}
