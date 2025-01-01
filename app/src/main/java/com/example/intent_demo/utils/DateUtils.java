package com.example.intent_demo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String getNowTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(new Date());
    }
}
