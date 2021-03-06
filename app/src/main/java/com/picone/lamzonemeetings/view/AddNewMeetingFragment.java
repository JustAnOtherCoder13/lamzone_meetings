package com.picone.lamzonemeetings.view;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.chip.Chip;
import com.picone.lamzonemeetings.R;
import com.picone.lamzonemeetings.controller.di.DI;
import com.picone.lamzonemeetings.controller.event.CreateNewMeetingEvent;
import com.picone.lamzonemeetings.controller.service.ApiService;
import com.picone.lamzonemeetings.databinding.FragmentAddNewMeetingBinding;
import com.picone.lamzonemeetings.model.Employee;
import com.picone.lamzonemeetings.model.Meeting;
import com.picone.lamzonemeetings.model.Room;
import com.picone.lamzonemeetings.model.Town;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.picone.lamzonemeetings.utils.CalendarStaticValues.RIGHT_NOW_HOUR;
import static com.picone.lamzonemeetings.utils.CalendarStaticValues.RIGHT_NOW_MINUTE;
import static com.picone.lamzonemeetings.utils.DatePickerUtils.formatPickedDate;
import static com.picone.lamzonemeetings.utils.DatePickerUtils.formatPickedHour;

public class AddNewMeetingFragment extends ShowDatePicker {

    private FragmentAddNewMeetingBinding binding;
    private List<Employee> mParticipants;
    private List<Employee> mEmployees;
    private List<Town> mTowns;
    private List<Room> mRooms;
    private ApiService mService;
    private Date mPickedDate;

    static AddNewMeetingFragment newInstance() {
        return new AddNewMeetingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mService = DI.getMeetingApiService();
        mEmployees = mService.getEmployees();
        mTowns = mService.getTowns();
        mRooms = mService.getRooms();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddNewMeetingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.setGroupVisible(R.id.filter_group, false);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mPickedDate = formatPickedDate(dayOfMonth, month, year);
        binding.dateTxt.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).format(mPickedDate));
    }

    private void initViews() {
        initDropDownMenu(mRooms, binding.roomTextView);
        initDropDownMenu(mTowns, binding.townTextView);
        binding.townTextView.setOnItemClickListener((parent, view, position, id) -> initChipGroupParticipants());
        binding.returnButton.setOnClickListener(v -> returnToList());
        binding.hourBtn.setOnClickListener(v -> initTimePicker());
        binding.dateBtn.setOnClickListener(v -> showDatePicker(getContext()));
        binding.addNewMeetingBtn.setOnClickListener(v -> createMeeting());
    }

    private <T> void initDropDownMenu(List<T> list, AutoCompleteTextView view) {
        ArrayAdapter<T> objectArrayAdapter = new ArrayAdapter<>((requireContext()), android.R.layout.simple_list_item_1, list);
        view.setEnabled(false);
        view.setAdapter(objectArrayAdapter);
    }

    private void initChipGroupParticipants() {
        if (binding.participantsChipGroup.getChildCount() == 0) {
            for (Employee employee : mEmployees) {
                Chip chip = new Chip(requireContext());
                chip.setText(employee.getName());
                chip.setCheckable(true);
                chip.setCheckedIconVisible(true);
                chip.setCheckedIconResource(R.drawable.ic_check);
                binding.participantsChipGroup.addView(chip);
            }
        }
    }

    private void initTimePicker() {
        TimePickerDialog picker;
        picker = new TimePickerDialog(getContext(),
                android.R.style.Theme_Holo_Light_Dialog,
                (tp, sHour, sMinute) ->
                        binding.hourTxt.setText(formatPickedHour(sHour, sMinute)), RIGHT_NOW_HOUR, RIGHT_NOW_MINUTE, true);
        picker.show();
    }

    private void createMeeting() {
        getCheckedParticipants();

        if (isMeetingIsValid() && isRoomIsFree()) {
            Meeting meeting = createNewMeeting(binding.hourTxt.getText().toString(), binding.subjectEditText.getText().toString(), binding.roomTextView.getText().toString(), mParticipants, mPickedDate);
            EventBus.getDefault().post(new CreateNewMeetingEvent(meeting));
            returnToList();
        }
    }

    private boolean isMeetingIsValid() {
        boolean myBol = false;
        if (!binding.roomTextView.getText().toString().trim().isEmpty()
                && !Objects.requireNonNull(binding.subjectEditText.getText()).toString().trim().isEmpty()
                && !binding.hourTxt.getText().toString().trim().isEmpty()
                && !binding.dateTxt.getText().toString().trim().isEmpty()
                && !mParticipants.isEmpty()) {
            myBol = true;
        } else {
            Toast.makeText(getContext(), R.string.toast_parameters, Toast.LENGTH_SHORT).show();
        }
        return myBol;
    }

    private boolean isRoomIsFree() {
        boolean myBol = true;
        for (Meeting meeting : mService.getMeetings()) {
            if (meeting.getDate().compareTo(mPickedDate) == 0
                    && binding.roomTextView.getText().toString().equals(meeting.getPlace())
                    && meeting.getHour().equals(binding.hourTxt.getText().toString())) {
                Toast.makeText(getContext(), R.string.toast_room_is_not_free, Toast.LENGTH_LONG).show();
                myBol = false;
            }
        }
        return myBol;
    }

    private void getCheckedParticipants() {
        List<Employee> participantsChecked = new ArrayList<>();
        Employee participant;
        for (int i = 0; i < binding.participantsChipGroup.getChildCount(); i++) {
            Chip chip = (Chip) binding.participantsChipGroup.getChildAt(i);
            if (chip.isChecked()) {
                participant = mEmployees.get(i);
                participantsChecked.add(participant);
            }
        }
        mParticipants = participantsChecked;
    }

    private Meeting createNewMeeting(String hour, String subject, String place, List<Employee> participants, Date date) {
        return new Meeting(hour, subject, place, participants, date);
    }

    private void returnToList() {
        requireActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }
}