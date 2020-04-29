package com.picone.lamzonemeetings.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.picone.lamzonemeetings.R;
import com.picone.lamzonemeetings.controller.di.DI;
import com.picone.lamzonemeetings.controller.event.AddNewMeetingEvent;
import com.picone.lamzonemeetings.controller.event.DeleteMeetingEvent;
import com.picone.lamzonemeetings.controller.service.ApiService;
import com.picone.lamzonemeetings.databinding.FragmentListMeetingBinding;
import com.picone.lamzonemeetings.model.Employee;
import com.picone.lamzonemeetings.model.Meeting;
import com.picone.lamzonemeetings.model.Room;
import com.picone.lamzonemeetings.utils.RecyclerViewOnLongClickUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.picone.lamzonemeetings.controller.service.utils.DateGeneratorUtils.generateDate;
import static com.picone.lamzonemeetings.controller.service.utils.DateGeneratorUtils.generateHour;
import static com.picone.lamzonemeetings.controller.service.utils.ParticipantsGeneratorUtils.generateParticipants;
import static com.picone.lamzonemeetings.utils.DatePickerUtils.formatPickedDate;
import static com.picone.lamzonemeetings.utils.ParticipantsMailUtils.getParticipantsMail;


public class ListMeetingFragment extends ShowDatePicker {


    private FragmentListMeetingBinding binding;

    private RecyclerView.Adapter mAdapter;
    private ApiService mService;
    private List<Meeting> mMeetings;
    private List<Meeting> mFilteredMeetings = new ArrayList<>();
    private List<Room> mRooms;
    private Room mRoom;
    private Date mPickedDate;

    static ListMeetingFragment newInstance() {
        return new ListMeetingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListMeetingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        mService = DI.getMeetingApiService();
        initList();
        initView();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.lamzone_meeting_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.filter_by_date:
                showDatePicker(getContext());
                return true;

            case R.id.filter_by_place:
                initAlertDialog(R.string.place_alert_dialog_title,0);
                return true;

            case R.id.cancel_filter:
                cancelFilter();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mPickedDate = formatPickedDate(dayOfMonth, month, year);
        filterByDate();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.container.setLayoutManager(linearLayoutManager);
        binding.addMeetingBtn.setOnClickListener(v -> {
            Fragment fragment = AddNewMeetingFragment.newInstance();
            assert getFragmentManager() != null;
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.fragment_place_holder, fragment, String.valueOf(R.string.frag_new_meeting_tag));
            ft.commit();
        });
    }

    private void initList() {
        configureOnClickRecyclerView();
        mMeetings = mService.getMeetings();
        mRooms = mService.getRooms();
        List<Employee> employees = mService.getEmployees();
        generateParticipants(mMeetings, employees);
        generateHour(mMeetings);
        generateDate(mMeetings);
        setAdapter(mMeetings);
    }

    private void initAlertDialog(int title, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(title);
        if (title == R.string.date_participants_alert_dialog) {
            String date = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).format(mMeetings.get(position).getDate());
            String participants = getParticipantsMail(mMeetings.get(position));
            builder.setMessage(date.concat("\n\n").concat(participants));
        }
        else{
            ArrayAdapter<Room> roomsAdapter = new ArrayAdapter<>((requireContext()), android.R.layout.simple_list_item_single_choice, mRooms);
            builder.setSingleChoiceItems(roomsAdapter, -1, ((dialog, which) -> mRoom = mRooms.get(which)));
            builder.setPositiveButton(R.string.positive_button, (dialog, which) -> filterByPlace());
        }
        builder.setNegativeButton(R.string.negative_button, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void filterByPlace() {
        mFilteredMeetings.clear();
        if (mRoom != null) {
            for (Meeting meeting : mMeetings) {
                if (meeting.getPlace().equals(mRoom.getRoomName())) mFilteredMeetings.add(meeting);
            }
           // mAdapter = new MeetingsRecyclerViewAdapter(mFilteredMeetings);
            mAdapter.notifyDataSetChanged();
            //setAdapter(mFilteredMeetings);
        } else
            Toast.makeText(getContext(), R.string.toast_filter_room_not_choose, Toast.LENGTH_SHORT).show();
    }

    private void filterByDate() {
        mFilteredMeetings.clear();
        for (Meeting meeting : mMeetings) {
            if (meeting.getDate().equals(mPickedDate))
                mFilteredMeetings.add(meeting);
        }
        setAdapter(mFilteredMeetings);
    }

    private void cancelFilter() {
        setAdapter(mMeetings);
    }

    private void setAdapter(List<Meeting> meetings) {
        mAdapter = new MeetingsRecyclerViewAdapter(meetings);
        binding.container.setAdapter(mAdapter);
    }

    private void configureOnClickRecyclerView() {
        RecyclerViewOnLongClickUtils.addTo(binding.container, R.layout.fragment_list_meeting)
                .setOnItemLongClickListener((recyclerView, position, v) -> {
                    initAlertDialog(R.string.date_participants_alert_dialog,position);
                    return true;
                });
    }

    @Subscribe
    public void onDeleteMeeting(DeleteMeetingEvent event) {
        mService.deleteMeeting(event.meeting);
        mAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void onAddNewMeeting(AddNewMeetingEvent event) {
        mService.addMeeting(event.meeting);
        mAdapter.notifyDataSetChanged();
    }
}