package com.picone.lamzonemeetings.view;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.picone.lamzonemeetings.R;
import com.picone.lamzonemeetings.controller.event.AddMeetingEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeetingsActivity extends AppCompatActivity {

    @BindView(R.id.fragment_place_holder)
    FrameLayout mPlaceHolder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings);
        ButterKnife.bind(this);
        ListMeetingFragment fragment = ListMeetingFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_place_holder, fragment);
        ft.commit();
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
    public void onAddMeetingEvent(AddMeetingEvent event){

        AddNewMeetingFragment fragment = AddNewMeetingFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_place_holder, fragment);
        ft.commit();
    }
}

