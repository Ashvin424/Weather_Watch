package com.example.weatherwatch.Common;

import androidx.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {
    public static String API_KEY = "98c9cbcc672f3f5997e070c05b171858";
    public static String API_LINK = "https://api.openweathermap.org/data/2.5/weather";

    @NonNull
    public static String apiRequest(String lat , String lng){
        StringBuilder sb = new StringBuilder(API_LINK);
        sb.append(String.format("?lat=%s&lon=%s&APPID=%s&units=metric",lat,lng,API_KEY));
        return sb.toString();
    }

    @NonNull
    public static String unixTimeStampToDateTime(double unixTimeStamp){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        date.setTime((long)unixTimeStamp * 1000); // Multiply by 1000
        return dateFormat.format(date);
    }

    @NonNull
    public  static String getImage(String icon){
        return String.format("https://openweathermap.org/img/w/%s.png",icon);
    }

    public static String getDateNow(){
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
