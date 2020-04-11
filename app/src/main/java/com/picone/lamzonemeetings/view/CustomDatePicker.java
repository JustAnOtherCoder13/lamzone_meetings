package com.picone.lamzonemeetings.view;

import android.app.DatePickerDialog;
import android.util.Log;

import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.Objects;

public class CustomDatePicker {
    private final Fragment fragment;

    CustomDatePicker(Fragment fragment) {
        this.fragment = fragment;
    }
    private String mYear;
    private String mMonth;
    private String mDay;
    static String FULL_DATE;

    void initDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int month = calendar.get(Calendar.MONTH);
        final int year = calendar.get(Calendar.YEAR);
        // date picker dialog
        DatePickerDialog picker;
        picker = new DatePickerDialog(Objects.requireNonNull(fragment.getContext()),
                (view, year1, monthOfYear, dayOfMonth) -> {
                    mYear =(String.valueOf(year1));
                    mMonth=(String.valueOf(monthOfYear + 1));
                    if (monthOfYear < 10)
                        mMonth=("0".concat(mMonth));
                    mDay=(String.valueOf(dayOfMonth));
                    if (dayOfMonth < 10)
                        mDay=("0".concat(mDay));
                    FULL_DATE = mDay.concat("/").concat(mMonth).concat("/").concat(mYear);

                }, year, month, day);
        picker.show();
        Log.i("test", "initDatePicker: "+FULL_DATE);


    }

}