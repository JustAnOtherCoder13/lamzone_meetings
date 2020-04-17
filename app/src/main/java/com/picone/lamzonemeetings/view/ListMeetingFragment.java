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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.picone.lamzonemeetings.R;
import com.picone.lamzonemeetings.controller.di.DI;
import com.picone.lamzonemeetings.controller.event.AddNewMeetingEvent;
import com.picone.lamzonemeetings.controller.event.DeleteMeetingEvent;
import com.picone.lamzonemeetings.controller.service.ApiService;
import com.picone.lamzonemeetings.model.Meeting;
import com.picone.lamzonemeetings.model.Room;
import com.picone.lamzonemeetings.utils.DatePickerUtils;
import com.picone.lamzonemeetings.utils.RecyclerViewOnLongClickUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.picone.lamzonemeetings.utils.DatePickerUtils.formatPickedDate;
import static com.picone.lamzonemeetings.utils.ParticipantsMailUtils.getParticipantsMail;


public class ListMeetingFragment extends InitDatePicker {
    @BindView(R.id.container)
    RecyclerView mRecyclerView;
    @BindView(R.id.add_meeting_btn)
    public FloatingActionButton mAddMeetingButton;

    private RecyclerView.Adapter mAdapter;
    private ApiService mService;
    private List<Meeting> mMeetings;
    private List<Meeting> mFilteredMeetings = new ArrayList<>();
    private List<Room> mRooms;
    private Room mRoom;

    static ListMeetingFragment newInstance() {
        return new ListMeetingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_meeting, container, false);
        ButterKnife.bind(this, view);
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
                initDatePicker(getContext());
                return true;

            case R.id.filter_by_place:
                initPlaceAlertDialog();
                return true;

            case R.id.cancel_filter:
                cancelFilter();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        formatPickedDate(dayOfMonth, month, year);
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
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAddMeetingButton.setOnClickListener(v -> {
            Fragment fragment = AddNewMeetingFragment.newInstance();
            assert getFragmentManager() != null;
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.fragment_place_holder, fragment, "NEW_MEETING_FRAG");
            ft.commit();
        });
    }
    private void initList() {
        configureOnClickRecyclerView();
        mMeetings = mService.getMeetings();
        mRooms = mService.getRooms();
        mService.getParticipants();
        mService.getHour();
        mAdapter = new MeetingsRecyclerViewAdapter(mMeetings);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initPlaceAlertDialog() {
        ArrayAdapter<Room> roomsAdapter = new ArrayAdapter<>((Objects.requireNonNull(getContext())), R.layout.radio_room_item, mRooms);
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
        builder.setTitle("Choose the room");
        builder.setSingleChoiceItems(roomsAdapter, 1, (dialog, which) -> mRoom = mRooms.get(which));
        builder.setPositiveButton("Ok", (dialog, which) -> filterByPlace());
        builder.setNegativeButton("Cancel", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void filterByPlace() {
        mFilteredMeetings.clear();
        for (Meeting meeting : mMeetings) {
            if (mRoom.getRoomName() != null && meeting.getPlace().equals(mRoom.getRoomName()))
                mFilteredMeetings.add(meeting);
        }
        mAdapter = new MeetingsRecyclerViewAdapter(mFilteredMeetings);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void filterByDate() {
        mFilteredMeetings.clear();
        for (Meeting meeting : mMeetings) {
            if (meeting.getDate().equals(DatePickerUtils.PICKED_DATE))
                mFilteredMeetings.add(meeting);
        }
        //TODO date and participants change when filter
        mAdapter = new MeetingsRecyclerViewAdapter(mFilteredMeetings);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void cancelFilter() {
        mAdapter = new MeetingsRecyclerViewAdapter(mMeetings);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void configureOnClickRecyclerView() {
        RecyclerViewOnLongClickUtils.addTo(mRecyclerView, R.layout.fragment_list_meeting)
                .setOnItemLongClickListener((recyclerView, position, v) -> {
                    initDetailAlertDialog(position);
                   return true;
                });
    }
    private void initDetailAlertDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
        builder.setTitle("Date/Participants");
        String date = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).format( mMeetings.get(position).getDate());
        String participants = getParticipantsMail(mMeetings.get(position));
        builder.setMessage(date.concat("\n\n").concat(participants));
        builder.setNegativeButton("Back", null);
        AlertDialog dialog = builder.create();
        dialog.show();
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
