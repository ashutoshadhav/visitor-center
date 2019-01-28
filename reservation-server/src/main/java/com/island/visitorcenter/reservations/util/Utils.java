package com.island.visitorcenter.reservations.util;

import com.island.visitorcenter.reservations.errorhandler.ErrorResponseDetails;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.Date;

public class Utils {
    public static int numberOfDaysBetweenDates (Date startDate, Date endDate) {
        int days = Days.daysBetween(new DateTime(startDate), new DateTime(endDate)).getDays();
        return days;
    }


    public static ErrorResponseDetails buildErrorResponseDetails(Date date, String message, String details) {
        return new ErrorResponseDetails (date, message, details);
    }

    public static int maxDaysInCurrentMonth(Date date) {
        return new DateTime(date).dayOfMonth().getMaximumValue();
    }

    public static Date subtractOneDayFromDate(Date date) {
        DateTime dateTime = new DateTime(date);
        dateTime = dateTime.minusDays(1);
        return dateTime.toDate();
    }

    public static Date addDaysToDate(Date date, int days) {
        DateTime dateTime = new DateTime(date);
        dateTime = dateTime.plusDays(days);
        return dateTime.toDate();
    }
}
