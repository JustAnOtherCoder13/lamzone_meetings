package com.picone.lamzonemeetings.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.widget.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public  class DatePickerUtils {
    public static String FULL_DATE;
    public static Date PICKED_DATE;


    public static void initDatePicker(Context context,int dayOfMonth,int monthOfYear,int year) {

        // date picker dialog


                    String mYear = (String.valueOf(year));
                    String mMonth = (String.valueOf(monthOfYear + 1));
                    if (monthOfYear < 10)
                        mMonth = ("0".concat(mMonth));
                    String mDay = (String.valueOf(dayOfMonth));
                    if (dayOfMonth < 10)
                        mDay = ("0".concat(mDay));
                    FULL_DATE = mDay.concat("/").concat(mMonth).concat("/").concat(mYear);
                    try {
                        PICKED_DATE = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).parse(dayOfMonth+"/"+monthOfYear+"/"+year);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Log.i("test", "initDatePicker: " + FULL_DATE+" "+PICKED_DATE);
    }
}
