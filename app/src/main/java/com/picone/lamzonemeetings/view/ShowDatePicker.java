package com.picone.lamzonemeetings.view;

import android.app.DatePickerDialog;
import android.content.Context;

import androidx.fragment.app.Fragment;

import static com.picone.lamzonemeetings.utils.CalendarStaticValues.MY_DAY_OF_MONTH;
import static com.picone.lamzonemeetings.utils.CalendarStaticValues.MY_MONTH;
import static com.picone.lamzonemeetings.utils.CalendarStaticValues.MY_YEAR;

abstract class ShowDatePicker extends Fragment implements DatePickerDialog.OnDateSetListener {

    void showDatePicker(Context context) {

        DatePickerDialog picker = new DatePickerDialog(context, this, MY_YEAR, MY_MONTH, MY_DAY_OF_MONTH);
        picker.show();
    }
}
