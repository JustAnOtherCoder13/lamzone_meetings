package com.picone.lamzonemeetings.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.picone.lamzonemeetings.R;
import com.picone.lamzonemeetings.controller.di.DI;
import com.picone.lamzonemeetings.model.Meeting;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

public class Utils {

    public static class DatePickerUtils {
        private final Context context;


        DatePickerUtils(Context context ) {
            this.context = context;
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
            picker = new DatePickerDialog(Objects.requireNonNull(context),
                    (view, year1, monthOfYear, dayOfMonth) -> {
                        mYear = (String.valueOf(year1));
                        mMonth = (String.valueOf(monthOfYear + 1));
                        if (monthOfYear < 10)
                            mMonth = ("0".concat(mMonth));
                        mDay = (String.valueOf(dayOfMonth));
                        if (dayOfMonth < 10)
                            mDay = ("0".concat(mDay));
                        FULL_DATE = mDay.concat("/").concat(mMonth).concat("/").concat(mYear);

                    }, year, month, day);
            picker.show();
            Log.i("test", "initDatePicker: " + FULL_DATE);
        }
    }

}
