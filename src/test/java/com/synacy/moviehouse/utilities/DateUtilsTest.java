package com.synacy.moviehouse.utilities;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class DateUtilsTest {

    SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.DEFAULT_PATTERN);

    @Test
    public void onSameDate() throws Exception {
        Date firstDate = dateFormat.parse("2017-05-16 07:30:00");
        Date secondDate = dateFormat.parse("2017-05-16 12:30:00");
        Date thirdDate = dateFormat.parse("2017-05-03 12:30:00");

        assertTrue(DateUtils.onSameDate(firstDate, secondDate));
        assertFalse(DateUtils.onSameDate(thirdDate, secondDate));
    }

    @Test
    public void formatDateAsString() throws Exception {
        Date actualDate = dateFormat.parse("2017-5-16 07:30:0");
        Date expectedDate = dateFormat.parse("2017-05-16 07:30:00");

        DateUtils.formatDateAsString(actualDate);

        assertEquals(expectedDate, actualDate);
    }

    @Test
    public void getDiffInMinutes() throws Exception {
        Date earlierDate = dateFormat.parse("2017-05-16 07:30:00");
        Date laterDate = dateFormat.parse("2017-05-16 08:30:00");

        int diffInMinutes = DateUtils.getDiffInMinutes(earlierDate, laterDate);

        assertEquals(60, diffInMinutes);
    }

}