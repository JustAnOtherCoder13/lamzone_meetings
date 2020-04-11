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

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddNewMeetingFragment extends Fragment {
    private final CustomDatePicker customDatePicker = new CustomDatePicker(this);
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
    private Chip mChip;

    public static AddNewMeetingFragment newInstance() {
        Fragment fragment = AddNewMeetingFragment.newInstance();
        Bundle bundle = new Bundle();

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
        initRoomsDropDownMenu();
        initChipGroupParticipants();
        mReturnFab.setOnClickListener(v -> returnToList());
        mHourButton.setOnClickListener(v -> initTimePicker());
        mDateButton.setOnClickListener(v -> initDatePicker());
        mAddMeetingButton.setOnClickListener(v -> createMeeting());
    }

    private void initRoomsDropDownMenu() {
        mRoomTextView.setEnabled(false);
        List<Room> rooms = mService.getRooms();
        ArrayAdapter<Room> roomsAdapter = new ArrayAdapter<Room>((Objects.requireNonNull(getContext())), R.layout.room_item, rooms);
        mRoomTextView.setAdapter(roomsAdapter);
    }

    private void initChipGroupParticipants() {
        mEmployees = mService.getEmployees();
        for (int i = 0; i < mEmployees.size(); i++) {
            mChip = new Chip(Objects.requireNonNull(getContext()));
            mChip.setText(mEmployees.get(i).getName());
            mChip.setCheckable(true);
            mChip.setCheckedIconVisible(true);
            mChip.setCheckedIconResource(R.drawable.ic_check);
            mParticipantsChipGroup.addView(mChip);
        }
    }

    private void createMeeting() {

        if (mRoomTextView.getText().length()>2) mPlace = mRoomTextView.getText().toString();
        else mPlace = null;

        if (Objects.requireNonNull(mSubjectEditText.getText()).length()>2) mSubject = mSubjectEditText.getText().toString();
         else mSubject = null;

        getCheckedParticipants();

        if (mPlace != null && mFullHour != null && mSubject != null && !mParticipants.isEmpty() && mFullDate != null) {
            Meeting meeting = createNewMeeting(mFullHour, mSubject, mPlace, mParticipants, mFullDate);
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
    }

    private void initDatePicker() {
        // date picker dialog
        CustomDatePicker customDatePicker = new CustomDatePicker(this);
        customDatePicker.initDatePicker();
        Log.i("test", "initDatePicker: "+ CustomDatePicker.FULL_DATE);
        mDateTxt.setText(CustomDatePicker.FULL_DATE);
        mFullDate=String.valueOf(mDateTxt.getText());
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



    private Meeting createNewMeeting(String hour, String subject, String place, List<Employee> participants, String date) {
        return new Meeting(hour, subject, place, participants, date);
    }

    private void returnToList() {
        assert getFragmentManager() != null;
        Fragment fragment = getFragmentManager().findFragmentByTag("NEW_MEETING_FRAG");
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }
}
