package com.picone.lamzonemeetings.view;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.picone.lamzonemeetings.R;

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
        ListMeetingFragment fragment = new ListMeetingFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_place_holder, fragment);
        ft.commit();
    }
}

