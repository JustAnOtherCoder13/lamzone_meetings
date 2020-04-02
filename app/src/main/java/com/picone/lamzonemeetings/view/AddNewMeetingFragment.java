package com.picone.lamzonemeetings.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.picone.lamzonemeetings.R;
import com.picone.lamzonemeetings.controller.service.DummyMeetingService;
import com.picone.lamzonemeetings.model.Room;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddNewMeetingFragment extends Fragment {
    @BindView(R.id.return_button)
    ImageButton mReturnButton;
    @BindView(R.id.hour_spinner)
    TimePicker mHourSpinner;
    @BindView(R.id.place_spinner_label)
    TextView mPlaceLabel;
    @BindView(R.id.place_spinner)
    Spinner mPlaceSpinner;
    @BindView(R.id.subject_editText_label)
    TextView mSubjectLabel;
    @BindView(R.id.subject_editText)
    EditText mSubjectEditText;
    @BindView(R.id.adrien_checkBox)
    CheckBox mAdrienCheckBox;

    private List<Room> mRooms;
    private DummyMeetingService service = new DummyMeetingService();

    public static AddNewMeetingFragment newInstance() {
        return new AddNewMeetingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_new_meeting, container, false);
        ButterKnife.bind(this,view);
        mRooms = service.getRooms();
        ArrayAdapter<Room> adapter = new ArrayAdapter<Room>(getContext(),android.R.layout.simple_spinner_item,mRooms);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPlaceSpinner.setAdapter(adapter);
        mReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToList();
            }
        });
        return view;
    }

    private void returnToList() {
        ListMeetingFragment fragment = ListMeetingFragment.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_place_holder, fragment);
        ft.commit();
    }
}
