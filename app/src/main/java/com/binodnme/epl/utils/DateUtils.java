package com.binodnme.epl.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by binodnme on 2/21/16.
 */
public class DateUtils {

    public static Date getDateObject(String date, String format){
        SimpleDateFormat input = new SimpleDateFormat(format, Locale.US);
        try {
            return input.parse(date);
        } catch (ParseException e) {
            return new Date();
        }
    }

    public static String getDateString(Date date, String format){
        SimpleDateFormat output = new SimpleDateFormat(format, Locale.US);
        return output.format(date);
    }
}
