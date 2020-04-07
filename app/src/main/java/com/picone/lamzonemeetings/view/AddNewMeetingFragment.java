package com.picone.lamzonemeetings.view;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.picone.lamzonemeetings.R;
import com.picone.lamzonemeetings.controller.di.DI;
import com.picone.lamzonemeetings.controller.event.AddNewMeetingEvent;
import com.picone.lamzonemeetings.controller.service.ApiService;
import com.picone.lamzonemeetings.model.Meeting;
import com.picone.lamzonemeetings.model.Participant;
import com.picone.lamzonemeetings.model.Room;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.security.AccessController.getContext;

public class AddNewMeetingFragment extends Fragment {
    @BindView(R.id.return_button)
    ImageButton mReturnButton;
    @BindView(R.id.hour_spinner)
    TimePicker mTimePicker;
    @BindView(R.id.place_spinner)
    Spinner mPlaceSpinner;
    @BindView(R.id.subject_editText)
    EditText mSubjectEditText;
    @BindView(R.id.participants_spinner)
    Spinner mParticipantsSpinner;
    @BindView(R.id.add_meeting_button)
    Button mAddMeetingButton;

    private ApiService mService;
    private String mPlace;
    private String mHour;
    private String mParticipant;
    private String mSubject;

    public static AddNewMeetingFragment newInstance() {
        return new AddNewMeetingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mService = DI.getMeetingApiService();
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_meeting, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    private void initViews() {
        initParticipantsSpinner();
        initPlaceSpinner();
        mTimePicker.setIs24HourView(true);
        mReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToList();
            }
        });
        mAddMeetingButton.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                mPlace = mPlaceSpinner.getSelectedItem().toString();
                mHour = String.valueOf(mTimePicker.getHour()).concat("h").concat(String.valueOf(mTimePicker.getMinute()));
                mSubject = mSubjectEditText.getText().toString();
                mParticipant = mParticipantsSpinner.getSelectedItem().toString();
                if (mPlace != null && mHour!=null && mSubject != null && mParticipant!=null){

                    Meeting meeting = createNewMeeting(mHour, mSubject, mPlace, mParticipant);
                    EventBus.getDefault().post(new AddNewMeetingEvent(meeting));
                    returnToList();
                }
                else {
                    Toast.makeText(getContext(), "you have not choose all parameters", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initPlaceSpinner() {
        List<Room> rooms = mService.getRooms();
        ArrayAdapter<Room> roomsAdapter = new ArrayAdapter<Room>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_item, rooms);
        roomsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPlaceSpinner.setAdapter(roomsAdapter);
    }

    private void initParticipantsSpinner() {
        List<Participant> participants = mService.getParticipants();
        ArrayAdapter<Participant> participantsAdapter = new ArrayAdapter<Participant>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_item, participants);
        participantsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mParticipantsSpinner.setAdapter(participantsAdapter);
    }

    private Meeting createNewMeeting(String hour, String subject, String place, String participants) {
        return new Meeting(hour, subject, place, participants);
    }

    private void returnToList() {
        assert getFragmentManager() != null;
        Fragment fragment = getFragmentManager().findFragmentByTag("NEW_MEETING_FRAG");
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }
}
