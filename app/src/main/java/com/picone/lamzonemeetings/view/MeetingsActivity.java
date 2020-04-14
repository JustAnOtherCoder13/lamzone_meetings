package com.picone.lamzonemeetings.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.picone.lamzonemeetings.R;
import com.picone.lamzonemeetings.controller.di.DI;
import com.picone.lamzonemeetings.controller.service.ApiService;
import com.picone.lamzonemeetings.model.Meeting;

import java.util.List;

public class MeetingsActivity extends AppCompatActivity {

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
        //if (getSupportFragmentManager().findFragmentByTag("NEW_MEETING_FRAG") == null) {
        Fragment fragment = ListMeetingFragment.newInstance();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_place_holder, fragment);
            ft.commit();
        //}
    }


}

