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
import com.picone.lamzonemeetings.controller.event.CancelFilterEvent;
import com.picone.lamzonemeetings.controller.event.DeleteMeetingEvent;
import com.picone.lamzonemeetings.controller.event.FilterByDateEvent;
import com.picone.lamzonemeetings.controller.event.FilterByPlaceEvent;
import com.picone.lamzonemeetings.controller.service.ApiService;
import com.picone.lamzonemeetings.model.Meeting;
import com.picone.lamzonemeetings.model.Room;
import com.picone.lamzonemeetings.utils.DatePickerUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ListMeetingFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
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

    @Subscribe
    public void onDeleteMeeting(DeleteMeetingEvent event) {
        mService.deleteMeeting(event.meeting);
        mAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void onFilterByDate(FilterByDateEvent event) {
        /*Utils.DatePickerUtils datePickerUtils = new Utils.DatePickerUtils(getContext());
        datePickerUtils.initDatePicker();*/
        DatePickerUtils.initDatePicker(getContext());
        Log.i("test", "onFilterByDate: "+DatePickerUtils.PICKED_DATE+"full date"+DatePickerUtils.FULL_DATE);

       /* mFilteredMeetings.clear();
        Log.i("test", "onFilterByDate: "+event.meetings.size());
        for (Meeting meeting : event.meetings) {
            if (meeting.getHour().equals()) mFilteredMeetings.add(meeting);
        }
        mAdapter = new MeetingsRecyclerViewAdapter(mFilteredMeetings);
        mRecyclerView.setAdapter(mAdapter);*/
    }

    @Subscribe
    public void onFilterByPlace(FilterByPlaceEvent event) {
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
    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        inflater.inflate(R.menu.lamzone_meeting_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.sort_by_date:
                DatePickerUtils.initDatePicker(getContext());
                //EventBus.getDefault().post(new FilterByDateEvent(mMeetings));
                return true;
            case R.id.sort_by_place:
                EventBus.getDefault().post(new FilterByPlaceEvent(mMeetings));
                return true;
            case R.id.cancel_filter:
                EventBus.getDefault().post(new CancelFilterEvent());
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }
}
