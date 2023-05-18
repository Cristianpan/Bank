package com.bank.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateFormatter {
    public static String generarFechaDeVencimiento(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 5);
        return new SimpleDateFormat("MM/yy").format(calendar.getTime()); 
    }
}
