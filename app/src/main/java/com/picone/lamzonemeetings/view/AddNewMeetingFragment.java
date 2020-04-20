package com.picone.lamzonemeetings.view;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.picone.lamzonemeetings.R;
import com.picone.lamzonemeetings.controller.di.DI;
import com.picone.lamzonemeetings.controller.event.AddNewMeetingEvent;
import com.picone.lamzonemeetings.controller.service.ApiService;
import com.picone.lamzonemeetings.model.Employee;
import com.picone.lamzonemeetings.model.Meeting;
import com.picone.lamzonemeetings.model.Room;
import com.picone.lamzonemeetings.model.Town;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.picone.lamzonemeetings.controller.service.utils.DummyDateGeneratorUtils.CALENDAR;
import static com.picone.lamzonemeetings.utils.DatePickerUtils.FULL_DATE;
import static com.picone.lamzonemeetings.utils.DatePickerUtils.FULL_HOUR;
import static com.picone.lamzonemeetings.utils.DatePickerUtils.PICKED_DATE;
import static com.picone.lamzonemeetings.utils.DatePickerUtils.formatPickedDate;
import static com.picone.lamzonemeetings.utils.DatePickerUtils.formatPickedHour;

public class AddNewMeetingFragment extends InitDatePicker {
    @BindView(R.id.return_button)
    FloatingActionButton mReturnFab;
    @BindView(R.id.room_textView)
    AutoCompleteTextView mRoomTextView;
    @BindView(R.id.subject_editText)
    TextInputEditText mSubjectEditText;
    @BindView(R.id.town_textView)
    AutoCompleteTextView mTownTextView;
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

    private List<Employee> mParticipants;
    private List<Employee> mEmployees;
    private List<Town> mTowns;
    private List<Room> mRooms;

    static AddNewMeetingFragment newInstance() {
        return new AddNewMeetingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        ApiService service = DI.getMeetingApiService();
        mEmployees = service.getEmployees();
        mTowns = service.getTowns();
        mRooms = service.getRooms();
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.setGroupVisible(R.id.filter_group, false);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        formatPickedDate(dayOfMonth, month, year);
        mDateTxt.setText(FULL_DATE);
    }

    private void initViews() {
        initDropDownMenu(mRooms,mRoomTextView);
        initDropDownMenu(mTowns,mTownTextView);
        mTownTextView.setOnItemClickListener((parent, view, position, id) -> initChipGroupParticipants());
        mReturnFab.setOnClickListener(v -> returnToList());
        mHourButton.setOnClickListener(v -> initTimePicker());
        mDateButton.setOnClickListener(v -> initDatePicker(getContext()));
        mAddMeetingButton.setOnClickListener(v -> createMeeting());
    }

      private <T> void initDropDownMenu( List<T> list,AutoCompleteTextView view){
          ArrayAdapter<T> objectArrayAdapter = new ArrayAdapter<>((Objects.requireNonNull(getContext())), android.R.layout.simple_list_item_1, list);
          view.setEnabled(false);
          view.setAdapter(objectArrayAdapter);
      }

    private void initChipGroupParticipants() {
        for (Employee employee:mEmployees) {
            Chip chip = new Chip(Objects.requireNonNull(getContext()));
            chip.setText(employee.getName());
            chip.setCheckable(true);
            chip.setCheckedIconVisible(true);
            chip.setCheckedIconResource(R.drawable.ic_check);
            mParticipantsChipGroup.addView(chip);
        }
    }

    private void createMeeting() {

        String subject;
        String place;
        if (mRoomTextView.getText().length() > 2) place = mRoomTextView.getText().toString();
        else place = null;

        if (Objects.requireNonNull(mSubjectEditText.getText()).length() > 2) subject = mSubjectEditText.getText().toString();
        else subject = null;

        getCheckedParticipants();

        if (place != null && FULL_HOUR != null && subject != null && !mParticipants.isEmpty() && FULL_DATE != null) {
            Meeting meeting = createNewMeeting(FULL_HOUR, subject, place, mParticipants,PICKED_DATE);
            EventBus.getDefault().post(new AddNewMeetingEvent(meeting));
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
            if (chip.isChecked()) {
                participant = mEmployees.get(i);
                participantsChecked.add(participant);
            }
        }
        mParticipants = participantsChecked;
    }

    private void initTimePicker() {
        // time picker dialog
        TimePickerDialog picker;
        picker = new TimePickerDialog(getContext(),
                (tp, sHour, sMinute) -> {
                    formatPickedHour(sHour,sMinute);
                    mHourTxt.setText(FULL_HOUR);
                }, CALENDAR.get(Calendar.HOUR_OF_DAY), CALENDAR.get(Calendar.MINUTE), true);
        picker.show();
    }

    private Meeting createNewMeeting(String hour, String subject, String place, List<Employee> participants, Date date) {
        return new Meeting(hour, subject, place, participants, date);
    }

    private void returnToList() {
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

}
