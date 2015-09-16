package com.tukaloff.annavk.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by user on 20.06.2015.
 */
public class UnixTimeParcer {

    GregorianCalendar gc;
    Date date;
    private long unixms;
    private int msec, sec, min, hour, dayOfWeek, dayOfMonth, month, year;
    private String smsec, ssec, smin, shour, sdayOfWeek,
            sdayOfMonth, sdayOfMonthWithZero, smonth, smonthWithZero, syear;

    public UnixTimeParcer () {
        date = new Date();
        unixms = date.getTime();
        gc = new GregorianCalendar();
        gc.setTime(date);
        getParts();
    }

    public UnixTimeParcer(long unixms) {
        this.unixms = unixms;
        date = new Date(unixms * 1000);
        gc = new GregorianCalendar();
        gc.setTime(date);
        getParts();
    }

    private void getParts() {
        msec = gc.get(Calendar.MILLISECOND);
        sec = gc.get(Calendar.SECOND);
        min = gc.get(Calendar.MINUTE);
        hour = gc.get(Calendar.HOUR_OF_DAY);
        dayOfWeek = gc.get(Calendar.DAY_OF_WEEK);
        dayOfMonth = gc.get(Calendar.DAY_OF_MONTH);
        month = gc.get(Calendar.MONTH);
        year = gc.get(Calendar.YEAR);


        ssec = withZero(sec);
        smin = withZero(min);
        shour = withZero(hour);
        sdayOfMonthWithZero = withZero(dayOfMonth);
        smonthWithZero = withZero(1 + month);
        syear = String.valueOf(year);
    }


    private String withZero(int field) {
        if (field < 10) {
            return "0" + field;
        } else {
            return String.valueOf(field);
        }
    }



    public String getSDateTTime() {
        return (sdayOfMonthWithZero + "." + smonthWithZero + "." + syear + " " +
                shour + ":" + smin + ":" + ssec);
    }

    public String getOrigin() {
        return date.toString();
    }
}
