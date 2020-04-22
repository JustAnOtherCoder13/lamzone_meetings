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
import com.picone.lamzonemeetings.controller.event.AddNewMeetingEvent;
import com.picone.lamzonemeetings.controller.service.ApiService;
import com.picone.lamzonemeetings.databinding.FragmentAddNewMeetingBinding;
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

import static com.picone.lamzonemeetings.controller.service.utils.DummyDateGeneratorUtils.CALENDAR;
import static com.picone.lamzonemeetings.utils.DatePickerUtils.FULL_DATE;
import static com.picone.lamzonemeetings.utils.DatePickerUtils.FULL_HOUR;
import static com.picone.lamzonemeetings.utils.DatePickerUtils.PICKED_DATE;
import static com.picone.lamzonemeetings.utils.DatePickerUtils.formatPickedDate;
import static com.picone.lamzonemeetings.utils.DatePickerUtils.formatPickedHour;

public class AddNewMeetingFragment extends InitDatePicker {

    private FragmentAddNewMeetingBinding binding;
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddNewMeetingBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
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
        binding.dateTxt.setText(FULL_DATE);
    }

    private void initViews() {
        initDropDownMenu(mRooms, binding.roomTextView);
        initDropDownMenu(mTowns, binding.townTextView);
        binding.townTextView.setOnItemClickListener((parent, view, position, id) -> initChipGroupParticipants());
        binding.returnButton.setOnClickListener(v -> returnToList());
        binding.hourBtn.setOnClickListener(v -> initTimePicker());
        binding.dateBtn.setOnClickListener(v -> initDatePicker(getContext()));
        binding.addNewMeetingBtn.setOnClickListener(v -> createMeeting());
    }

    private <T> void initDropDownMenu(List<T> list, AutoCompleteTextView view) {
        ArrayAdapter<T> objectArrayAdapter = new ArrayAdapter<>((requireContext()), android.R.layout.simple_list_item_1, list);
        view.setEnabled(false);
        view.setAdapter(objectArrayAdapter);
    }

    private void initChipGroupParticipants() {
        for (Employee employee : mEmployees) {
            Chip chip = new Chip(requireContext());
            chip.setText(employee.getName());
            chip.setCheckable(true);
            chip.setCheckedIconVisible(true);
            chip.setCheckedIconResource(R.drawable.ic_check);
            binding.participantsChipGroup.addView(chip);
        }
    }

    private void createMeeting() {

        String subject;
        String place;
        if (binding.roomTextView.getText().length() > 2) place = binding.roomTextView.getText().toString();
        else place = null;

        if (Objects.requireNonNull(binding.subjectEditText.getText()).length() > 2) subject = binding.subjectEditText.getText().toString();
        else subject = null;

        getCheckedParticipants();

        if (place != null && FULL_HOUR != null && subject != null && !mParticipants.isEmpty() && FULL_DATE != null) {
            Meeting meeting = createNewMeeting(FULL_HOUR, subject, place, mParticipants, PICKED_DATE);
            EventBus.getDefault().post(new AddNewMeetingEvent(meeting));
            returnToList();
        } else {
            Toast.makeText(getContext(), "you have not choose all parameters", Toast.LENGTH_SHORT).show();
        }
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

    private void initTimePicker() {
        // time picker dialog
        TimePickerDialog picker;
        picker = new TimePickerDialog(getContext(),
                (tp, sHour, sMinute) -> {
                    formatPickedHour(sHour, sMinute);
                    binding.hourTxt.setText(FULL_HOUR);
                }, CALENDAR.get(Calendar.HOUR_OF_DAY), CALENDAR.get(Calendar.MINUTE), true);
        picker.show();
    }

    private Meeting createNewMeeting(String hour, String subject, String place, List<Employee> participants, Date date) {
        return new Meeting(hour, subject, place, participants, date);
    }

    private void returnToList() {
        requireActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }
}