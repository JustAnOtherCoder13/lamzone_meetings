package com.picone.lamzonemeetings.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.picone.lamzonemeetings.R;

public class MeetingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings);
        initListMeetingFragment();
    }

    private void initListMeetingFragment() {
        if (getSupportFragmentManager().findFragmentByTag("NEW_MEETING_FRAG") == null) {
        Fragment fragment = ListMeetingFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_place_holder, fragment);
        ft.commit();
        }
    }
}

