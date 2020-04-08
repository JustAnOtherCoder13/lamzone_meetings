package com.picone.lamzonemeetings.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.picone.lamzonemeetings.R;
import com.picone.lamzonemeetings.controller.di.DI;
import com.picone.lamzonemeetings.controller.event.AddNewMeetingEvent;
import com.picone.lamzonemeetings.controller.service.ApiService;
import com.picone.lamzonemeetings.model.Meeting;
import com.picone.lamzonemeetings.model.Participant;
import com.picone.lamzonemeetings.model.Room;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddNewMeetingFragment extends Fragment {
    @BindView(R.id.return_button)
    FloatingActionButton mReturnFab;
    @BindView(R.id.room_textView)
    AutoCompleteTextView mRoomTextView;
    @BindView(R.id.subject_editText)
    TextInputEditText mSubjectEditText;
    @BindView(R.id.hour_txt)
    TextView mHourTxt;
    @BindView(R.id.date_txt)
    TextView mDateTxt;
    @BindView(R.id.participants_spinner)
    Spinner mParticipantsSpinner;

    @BindView(R.id.add_meeting_button)
    Button mAddMeetingButton;
    @BindView(R.id.date_btn)
    Button mDateButton;
    @BindView(R.id.hour_btn)
    Button mHourButton;

    private ApiService mService;
    private String mPlace;
    private String mHour;
    private String mMinute;
    private String mFullHour;
    private String mYear;
    private String mMonth;
    private String mDay;
    private String mFullDate;
    private String mParticipant;
    private String mSubject;

    public static AddNewMeetingFragment newInstance() {
        return new AddNewMeetingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mService = DI.getMeetingApiService();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_meeting, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    private void initViews() {
        initParticipantsSpinner();
        initRoomsDropDownMenu();
        mReturnFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToList();
            }
        });
        mHourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initTimePicker();
            }
        });
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDatePicker();

            }
        });
        mAddMeetingButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                initAddMeeting();
            }
        });
    }

    private void initAddMeeting() {
        mPlace = mRoomTextView.getText().toString();
        mSubject = Objects.requireNonNull(mSubjectEditText.getText()).toString();
        mParticipant = mParticipantsSpinner.getSelectedItem().toString();
        if (mPlace != null && mHourTxt!=null && mSubject != null && mParticipant!=null && mDateTxt!=null){
            Meeting meeting = createNewMeeting(mFullHour, mSubject, mPlace, mParticipant, mFullDate);
            EventBus.getDefault().post(new AddNewMeetingEvent(meeting));
            returnToList();
        }
        else {
            Toast.makeText(getContext(), "you have not choose all parameters", Toast.LENGTH_SHORT).show();
        }
    }

    private void initDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        // date picker dialog
        DatePickerDialog picker;
        picker = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mYear = String.valueOf(year);
                        mMonth = String.valueOf(monthOfYear+1);
                        if (monthOfYear < 10) mMonth = "0".concat(mMonth);
                        mDay = String.valueOf(dayOfMonth);
                        if (dayOfMonth<10) mDay="0".concat(mDay);
                        mDateTxt.setText(mDay.concat("/").concat(mMonth).concat("/").concat(mYear));
                        mFullDate = String.valueOf(mDateTxt.getText());
                    }
                }, year, month, day);
        picker.show();
    }

    private void initTimePicker() {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        // time picker dialog
        TimePickerDialog picker;
        picker = new TimePickerDialog(getContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        mHour = String.valueOf(sHour);
                        if (sHour<10) mHour = "0".concat(mHour);
                        mMinute = String.valueOf(sMinute);
                        if (sMinute<10) mMinute = "0".concat(mMinute);
                        mHourTxt.setText(mHour.concat(":").concat(mMinute));
                        mFullHour = String.valueOf(mHourTxt.getText());
                    }
                }, hour, minutes, true);
        picker.show();
    }

    private void initRoomsDropDownMenu() {
        mRoomTextView.setEnabled(false);
        List<Room> rooms = mService.getRooms();
        ArrayAdapter<Room> roomsAdapter = new ArrayAdapter<Room>((Objects.requireNonNull(getContext())),R.layout.room_item , rooms);
        mRoomTextView.setAdapter(roomsAdapter);
    }

    private void initParticipantsSpinner() {
        List<Participant> participants = mService.getParticipants();
        ArrayAdapter<Participant> participantsAdapter = new ArrayAdapter<Participant>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_item, participants);
        participantsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mParticipantsSpinner.setAdapter(participantsAdapter);
    }

    private Meeting createNewMeeting(String hour, String subject, String place, String participants, String date) {
        return new Meeting(hour, subject, place, participants,date);
    }

    private void returnToList() {
        assert getFragmentManager() != null;
        Fragment fragment = getFragmentManager().findFragmentByTag("NEW_MEETING_FRAG");
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }
}
