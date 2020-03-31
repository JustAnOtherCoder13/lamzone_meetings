package com.picone.lamzonemeetings.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.picone.lamzonemeetings.R;
import com.picone.lamzonemeetings.controller.event.AddMeetingEvent;
import com.picone.lamzonemeetings.controller.event.DeleteMeetingEvent;
import com.picone.lamzonemeetings.controller.service.DummyMeetingService;
import com.picone.lamzonemeetings.model.Meeting;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListMeetingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListMeetingFragment extends Fragment {
    @BindView(R.id.container)
    RecyclerView mRecyclerView;
    @BindView(R.id.add_meeting_btn)
    public ImageButton mAddMeetingButton;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DummyMeetingService mService;
    private List<Meeting> mMeetings;



    public static ListMeetingFragment newInstance() {
        return new ListMeetingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_meeting, container, false);
        ButterKnife.bind(this,view);

        mService =  new DummyMeetingService();
        mMeetings = mService.getMeetings();
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MeetingsRecyclerViewAdapter(mMeetings);
        mRecyclerView.setAdapter(mAdapter);
        mAddMeetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new AddMeetingEvent());
            }
        });
        initList();
        return view;
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
    public void onDeleteMeeting(DeleteMeetingEvent event){

        initList();
        mService.deleteMeeting(event.meeting);
    }

    private void initList(){

        mMeetings = mService.getMeetings();
        mRecyclerView.setAdapter(new MeetingsRecyclerViewAdapter(mMeetings));
    }
}
