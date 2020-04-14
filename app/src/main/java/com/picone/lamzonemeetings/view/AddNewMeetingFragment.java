package com.picone.lamzonemeetings.view;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.picone.lamzonemeetings.R;
import com.picone.lamzonemeetings.controller.di.DI;
import com.picone.lamzonemeetings.controller.event.AddNewMeetingEvent;
import com.picone.lamzonemeetings.controller.service.ApiService;
import com.picone.lamzonemeetings.model.Meeting;
import com.picone.lamzonemeetings.model.Employee;
import com.picone.lamzonemeetings.model.Room;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.zip.Inflater;

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
    @BindView(R.id.participants_chip_group)
    ChipGroup mParticipantsChipGroup;

    @BindView(R.id.add_meeting_button)
    Button mAddMeetingButton;
    @BindView(R.id.date_btn)
    Button mDateButton;
    @BindView(R.id.hour_btn)
    Button mHourButton;

    private ApiService mService;
    private List<Employee> mParticipants;
    private List<Employee> mEmployees;
    private String mPlace;
    private String mHour;
    private String mMinute;
    private String mFullHour;
    private String mFullDate;
    private String mSubject;
    private String mParticipantsMail;

    public static AddNewMeetingFragment newInstance() {
        return new AddNewMeetingFragment(); }

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
        initRoomsDropDownMenu();
        initChipGroupParticipants();
        mReturnFab.setOnClickListener(v -> returnToList());
        mHourButton.setOnClickListener(v -> initTimePicker());
        mDateButton.setOnClickListener(v -> initDatePicker());
        mAddMeetingButton.setOnClickListener(v -> createMeeting());
    }

    private void initRoomsDropDownMenu() {
        List<Room> rooms = mService.getRooms();
        ArrayAdapter<Room> roomsAdapter = new ArrayAdapter<>((Objects.requireNonNull(getContext())), R.layout.room_item, rooms);
        mRoomTextView.setEnabled(false);
        mRoomTextView.setAdapter(roomsAdapter);
    }

    private void initChipGroupParticipants() {
        mEmployees = mService.getEmployees();
        for (int i = 0; i < mEmployees.size(); i++) {
            Chip chip = new Chip(Objects.requireNonNull(getContext()));
            chip.setText(mEmployees.get(i).getName());
            chip.setCheckable(true);
            chip.setCheckedIconVisible(true);
            chip.setCheckedIconResource(R.drawable.ic_check);
            mParticipantsChipGroup.addView(chip);
        }
    }

    private void createMeeting() {

        if (mRoomTextView.getText().length()>2) mPlace = mRoomTextView.getText().toString();
        else mPlace = null;

        if (Objects.requireNonNull(mSubjectEditText.getText()).length()>2) mSubject = mSubjectEditText.getText().toString();
         else mSubject = null;

        getCheckedParticipants();

        if (mPlace != null && mFullHour != null && mSubject != null && !mParticipants.isEmpty() && mFullDate != null) {
            Meeting meeting = createNewMeeting(mFullHour, mSubject, mPlace, mParticipantsMail, mFullDate);
            EventBus.getDefault().post(new AddNewMeetingEvent(meeting,mParticipants));
            returnToList();
        } else {
            Toast.makeText(getContext(), "you have not choose all parameters", Toast.LENGTH_SHORT).show();
        }
    }

    private void getCheckedParticipants() {
        List<Employee> participantsChecked = new ArrayList<>();
        Employee participant;
        for (int i = 0; i < mParticipantsChipGroup.getChildCount(); i++) {
            Chip chip = (Chip) mParticipantsChipGroup.getChildAt(i);
            Boolean isParticipantChecked = chip.isChecked();
            if (isParticipantChecked) {
                participant = mEmployees.get(i);
                participantsChecked.add(participant);
            }
        }
        mParticipants=participantsChecked;
        String participants = null;
        for (Employee employee:mParticipants){
            mParticipantsMail = employee.getMail();
            if (participants == null) participants = mParticipantsMail;
            else participants = participants.concat(", ").concat(mParticipantsMail);
            mParticipantsMail = participants;
        }
    }

    private void initDatePicker() {
        // date picker dialog
       /* Utils.DatePickerUtils datePickerUtils = new Utils.DatePickerUtils(getContext());
        datePickerUtils.initDatePicker();
        Log.i("test", "initDatePicker: "+ Utils.DatePickerUtils.FULL_DATE);
        mDateTxt.setText(Utils.DatePickerUtils.FULL_DATE);
        mFullDate=String.valueOf(mDateTxt.getText());*/
    }

    private void initTimePicker() {
        final Calendar calendar = Calendar.getInstance();
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minutes = calendar.get(Calendar.MINUTE);
        // time picker dialog
        TimePickerDialog picker;
        picker = new TimePickerDialog(getContext(),
                (tp, sHour, sMinute) -> {
                    mHour = String.valueOf(sHour);
                    if (sHour < 10) mHour = "0".concat(mHour);
                    mMinute = String.valueOf(sMinute);
                    if (sMinute < 10) mMinute = "0".concat(mMinute);
                    mHourTxt.setText(mHour.concat(":").concat(mMinute));
                    mFullHour = String.valueOf(mHourTxt.getText());
                }, hour, minutes, true);
        picker.show();
    }



    private Meeting createNewMeeting(String hour, String subject, String place, String participants, String date) {
        return new Meeting(hour, subject, place, participants, date);
    }

    private void returnToList() {
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().remove(this).commit();
    }
}
