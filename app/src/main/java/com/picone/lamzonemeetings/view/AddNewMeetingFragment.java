package com.picone.lamzonemeetings.view;

import android.os.Build;
import android.os.Bundle;
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

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.picone.lamzonemeetings.R;
import com.picone.lamzonemeetings.controller.di.DI;
import com.picone.lamzonemeetings.controller.event.AddNewMeetingEvent;
import com.picone.lamzonemeetings.controller.service.ApiService;
import com.picone.lamzonemeetings.model.Meeting;
import com.picone.lamzonemeetings.model.Room;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddNewMeetingFragment extends Fragment {
    @BindView(R.id.return_button)
    ImageButton mReturnButton;
    @BindView(R.id.hour_spinner)
    TimePicker mTimePicker;
    @BindView(R.id.place_spinner)
    Spinner mPlaceSpinner;
    @BindView(R.id.subject_editText)
    EditText mSubjectEditText;
    @BindView(R.id.adrien_checkBox)
    CheckBox mAdrienCheckBox;
    @BindView(R.id.add_meeting_button)
    Button mAddMeetingButton;

    private List<Room> mRooms;
    private ApiService service = DI.getMeetingApiService();
    private String mPlace;
    private String mHour;
    private String mParticipant;
    private String mSubject;

    public static AddNewMeetingFragment newInstance() {
        return new AddNewMeetingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_meeting, container, false);
        ButterKnife.bind(this,view);
        initViews();
        return view;
    }

    private void initViews() {
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
                mParticipant = "Adrien";
                Meeting meeting = createNewMeeting(mHour, mSubject, mPlace, mParticipant);
                EventBus.getDefault().post(new AddNewMeetingEvent(meeting));
                returnToList();
            }
        });
    }

    private void initPlaceSpinner() {
        mRooms = service.getRooms();
        ArrayAdapter<Room> adapter = new ArrayAdapter<Room>(getContext(),android.R.layout.simple_spinner_item,mRooms);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPlaceSpinner.setAdapter(adapter);
    }

    private Meeting createNewMeeting (String hour, String subject,String place, String participants){
        Meeting meeting = new Meeting(hour,subject,place,participants);
        return meeting;
    }

    private void returnToList() {
        Fragment fragment = getFragmentManager().findFragmentByTag("NEW_MEETING_FRAG");
        getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }
}
