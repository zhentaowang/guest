package com.zhiweicloud.guest.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tc on 2017/4/24.
 */
public class DateUtils {

    public static final String DEFAULT_FORMAT = "yyyy-mm-dd hh-mm-ss";

    public static Date stringToDate(String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_FORMAT);
        Date parse = simpleDateFormat.parse(time);
        return parse;
    }

    public static Date stringToDate(String time,String format) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date parse = simpleDateFormat.parse(time);
        return parse;
    }

    public static String dateToString(Date date,String format) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

}
