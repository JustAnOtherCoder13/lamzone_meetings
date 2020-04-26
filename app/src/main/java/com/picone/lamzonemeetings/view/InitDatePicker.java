package com.picone.lamzonemeetings.view;

import android.app.DatePickerDialog;
import android.content.Context;

import androidx.fragment.app.Fragment;

import static com.picone.lamzonemeetings.controller.service.utils.DummyDateGeneratorUtils.MY_DAY_OF_MONTH;
import static com.picone.lamzonemeetings.controller.service.utils.DummyDateGeneratorUtils.MY_MONTH;
import static com.picone.lamzonemeetings.controller.service.utils.DummyDateGeneratorUtils.MY_YEAR;

abstract class InitDatePicker extends Fragment implements DatePickerDialog.OnDateSetListener {

    void initDatePicker(Context context) {

        DatePickerDialog picker = new DatePickerDialog(context, this, MY_YEAR, MY_MONTH, MY_DAY_OF_MONTH);
        picker.show();
    }
}
