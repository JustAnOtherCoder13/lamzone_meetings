package com.picone.lamzonemeetings.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.picone.lamzonemeetings.R;
import com.picone.lamzonemeetings.controller.di.DI;
import com.picone.lamzonemeetings.controller.event.AddMeetingEvent;
import com.picone.lamzonemeetings.controller.event.AddNewMeetingEvent;
import com.picone.lamzonemeetings.controller.event.CancelFilterEvent;
import com.picone.lamzonemeetings.controller.event.DeleteMeetingEvent;
import com.picone.lamzonemeetings.controller.event.FilterByDateEvent;
import com.picone.lamzonemeetings.controller.event.FilterByPlace;
import com.picone.lamzonemeetings.controller.service.ApiService;
import com.picone.lamzonemeetings.model.Meeting;
import com.picone.lamzonemeetings.model.Room;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ListMeetingFragment extends Fragment {
    @BindView(R.id.container)
    RecyclerView mRecyclerView;
    @BindView(R.id.add_meeting_btn)
    public FloatingActionButton mAddMeetingButton;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ApiService mService;
    private List<Meeting> mMeetings;
    private List<Meeting> mFilteredMeetings = new ArrayList<>();
    private List<Room> mRooms;
    private Room mRoom;


    public static ListMeetingFragment newInstance() {
        return new ListMeetingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mService = DI.getMeetingApiService();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_meeting, container, false);
        ButterKnife.bind(this, view);
        initView();
        initList();
        return view;
    }

    private void initView() {
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        Fragment fragment = AddNewMeetingFragment.newInstance();
        mAddMeetingButton.setOnClickListener(v -> EventBus.getDefault().post(new AddMeetingEvent(fragment)));
    }

    private void initList() {
        mMeetings = mService.getMeetings();
        mRooms = mService.getRooms();
        mAdapter = new MeetingsRecyclerViewAdapter(mMeetings);
        mRecyclerView.setAdapter(mAdapter);
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

    @Subscribe
    public void onDeleteMeeting(DeleteMeetingEvent event) {
        mService.deleteMeeting(event.meeting);
        mAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void onFilterByDate(FilterByDateEvent event) {
        CustomDatePicker datePicker = new CustomDatePicker(this);
        datePicker.initDatePicker();

        mFilteredMeetings.clear();
        for (Meeting meeting : event.meetings) {
            if (meeting.getHour().equals(CustomDatePicker.FULL_DATE)) mFilteredMeetings.add(meeting);
        }
        mAdapter = new MeetingsRecyclerViewAdapter(mFilteredMeetings);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Subscribe
    public void onFilterByPlace(FilterByPlace event) {
        mFilteredMeetings.clear();
        ArrayAdapter<Room> roomsAdapter = new ArrayAdapter<Room>((Objects.requireNonNull(getContext())), R.layout.radio_room_item, mRooms);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
        mBuilder.setTitle("choose the room");
        mBuilder.setSingleChoiceItems( roomsAdapter, 1, (dialog, which) -> mRoom = mRooms.get(which));
        mBuilder.setPositiveButton("ok", (dialog, which) -> {
            for ( Meeting meeting: event.meetings){
                if (mRoom.getRoomName()!=null && meeting.getPlace().equals(mRoom.getRoomName())) mFilteredMeetings.add(meeting);
            }
            mAdapter = new MeetingsRecyclerViewAdapter(mFilteredMeetings);
            mRecyclerView.setAdapter(mAdapter);
           // mAdapter.notifyDataSetChanged();
        });

        mBuilder.setNegativeButton("cancel",null);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    @Subscribe
    public void onAddNewMeeting(AddNewMeetingEvent event) {
        mService.addMeeting(event.meeting);
        mAdapter.notifyDataSetChanged();
    }
    @Subscribe
    public void onCancelFilterEvent(CancelFilterEvent event){
        mAdapter = new MeetingsRecyclerViewAdapter(mMeetings);
        mRecyclerView.setAdapter(mAdapter);
    }
}
