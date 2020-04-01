package com.picone.lamzonemeetings.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.picone.lamzonemeetings.R;
import com.picone.lamzonemeetings.controller.event.AddMeetingEvent;
import com.picone.lamzonemeetings.controller.service.DummyApiServiceGenerator;
import com.picone.lamzonemeetings.model.Meeting;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeetingsActivity extends AppCompatActivity {

    @BindView(R.id.fragment_place_holder)
    FrameLayout mPlaceHolder;
    private Fragment mFragment;
    private List<Meeting> mMeetings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings);
        ButterKnife.bind(this);
        mFragment = ListMeetingFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_place_holder, mFragment);
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.lamzone_meeting_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        mMeetings = DummyApiServiceGenerator.generateMeetings();

        switch (item.getItemId()){

            case R.id.sort_by_date :
                Collections.sort(mMeetings, new Comparator<Meeting>() {
                    @Override
                    public int compare(Meeting m1, Meeting m2) {
                        Integer date1 = m1.getHour();
                        Integer date2 = m2.getHour();
                        return date1.compareTo(date2);
                    }
                });
                return true;
            case R.id.sort_by_place :
                Collections.sort(mMeetings, new Comparator<Meeting>(){
                    @Override
                    public int compare(Meeting m1, Meeting m2) {
                        return m1.getPlace().compareTo(m2.getPlace());
                    }
                });
                return true;
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
    public void onAddMeetingEvent(AddMeetingEvent event){

        mFragment = AddNewMeetingFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_place_holder, mFragment);
        ft.commit();
    }
}

