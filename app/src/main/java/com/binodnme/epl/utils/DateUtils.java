package com.binodnme.epl.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by binodnme on 2/21/16.
 */
public class DateUtils {

    public static Date getDateObject(String date, String format){
        SimpleDateFormat input = new SimpleDateFormat(format);
        long ts = System.currentTimeMillis();
        Date localTime = new Date(ts);

        Date matchDate = new Date();
        try {
            matchDate = input.parse(date);
        } catch (ParseException e) {

        }

//        return matchDate;
        return new Date(matchDate.getTime() + TimeZone.getDefault().getOffset(localTime.getTime()));
    }

    public static String getDateString(Date date, String format){
        SimpleDateFormat output = new SimpleDateFormat(format);
        return output.format(date);
    }
}
