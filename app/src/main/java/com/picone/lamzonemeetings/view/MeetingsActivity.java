package com.picone.lamzonemeetings.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.picone.lamzonemeetings.R;
import com.picone.lamzonemeetings.controller.di.DI;
import com.picone.lamzonemeetings.controller.event.AddMeetingEvent;
import com.picone.lamzonemeetings.controller.event.CancelFilterEvent;
import com.picone.lamzonemeetings.controller.event.SortByDateEvent;
import com.picone.lamzonemeetings.controller.event.FilterByPlace;
import com.picone.lamzonemeetings.controller.service.ApiService;
import com.picone.lamzonemeetings.model.Meeting;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class MeetingsActivity extends AppCompatActivity {

    private Fragment mFragment;
    private List<Meeting> mMeetings;
    private ApiService mService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings);
        mService = DI.getMeetingApiService();
        initListMeetingFragment();
    }

    private void initListMeetingFragment() {
        mMeetings = mService.getMeetings();
        if (getSupportFragmentManager().findFragmentByTag("NEW_MEETING_FRAG") == null) {
            mFragment = ListMeetingFragment.newInstance();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_place_holder, mFragment,"LIST_MEETING_FRAG");
            ft.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.lamzone_meeting_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.sort_by_date:
                EventBus.getDefault().post(new SortByDateEvent(mMeetings));
                return true;
            case R.id.sort_by_place:
                EventBus.getDefault().post(new FilterByPlace(mMeetings));
                return true;
            case R.id.cancel_filter:
                EventBus.getDefault().post(new CancelFilterEvent());
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }


    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe
    public void onAddMeetingEvent(AddMeetingEvent event) {
        mFragment = event.fragment;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_place_holder, mFragment, "NEW_MEETING_FRAG");
        ft.commit();
    }
}

