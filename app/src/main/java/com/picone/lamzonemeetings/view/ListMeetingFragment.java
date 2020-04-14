package com.picone.lamzonemeetings.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ListMeetingFragment extends DatePickerShow {
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
    private final Calendar calendar = Calendar.getInstance();
    private final int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
    private final int month = calendar.get(Calendar.MONTH);
    private final int year = calendar.get(Calendar.YEAR);





    public static ListMeetingFragment newInstance() {
        return new ListMeetingFragment();
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mService = DI.getMeetingApiService();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_meeting, container, false);
        ButterKnife.bind(this, view);
        initList();
        initView();
        return view;
    }


    private void initView() {
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAddMeetingButton.setOnClickListener(v -> {
            Fragment fragment = AddNewMeetingFragment.newInstance();
            assert getFragmentManager() != null;
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.fragment_place_holder, fragment);
            ft.commit();
        });
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




    private void filterByDate() {

        mFilteredMeetings.clear();
        for (Meeting meeting : mMeetings) {
            if (meeting.getHour().equals(DatePickerUtils.FULL_DATE)) mFilteredMeetings.add(meeting);
        }
        mAdapter = new MeetingsRecyclerViewAdapter(mFilteredMeetings);
        mRecyclerView.setAdapter(mAdapter);
    }


    private void filterByPlace() {
        mFilteredMeetings.clear();
        ArrayAdapter<Room> roomsAdapter = new ArrayAdapter<Room>((Objects.requireNonNull(getContext())), R.layout.radio_room_item, mRooms);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
        mBuilder.setTitle("choose the room");
        mBuilder.setSingleChoiceItems( roomsAdapter, 1, (dialog, which) -> mRoom = mRooms.get(which));
        mBuilder.setPositiveButton("ok", (dialog, which) -> {
            for ( Meeting meeting: mMeetings){
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
    private void cancelFilter(){
        mAdapter = new MeetingsRecyclerViewAdapter(mMeetings);
        mRecyclerView.setAdapter(mAdapter);
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


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
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
                filterByPlace();
                return true;

            case R.id.cancel_filter:
                cancelFilter();

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Log.i("test", "onDateSet: "+"ok");
        DatePickerUtils.workWithDatePickerData(getContext(),dayOfMonth,month,year);
        filterByDate();
    }
}
