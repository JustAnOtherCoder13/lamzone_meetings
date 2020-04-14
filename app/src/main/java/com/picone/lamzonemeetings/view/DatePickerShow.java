package com.picone.lamzonemeetings.view;

import android.app.DatePickerDialog;
import android.content.Context;

import androidx.fragment.app.Fragment;

import java.util.Calendar;

abstract class DatePickerShow extends Fragment implements DatePickerDialog.OnDateSetListener{

    void initDatePicker(Context context){

    final Calendar calendar = Calendar.getInstance();
    final int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
    final int month = calendar.get(Calendar.MONTH);
    final int year = calendar.get(Calendar.YEAR);

    DatePickerDialog picker = new DatePickerDialog(context,this,year,month,dayOfMonth);
    picker.show();
}
}
