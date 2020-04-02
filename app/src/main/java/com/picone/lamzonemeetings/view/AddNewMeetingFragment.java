package com.picone.lamzonemeetings.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.picone.lamzonemeetings.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddNewMeetingFragment extends Fragment {
    @BindView(R.id.return_button)
    ImageButton mReturnButton;
    @BindView(R.id.hour_spinner_label)
    TextView mHourLabel;
    @BindView(R.id.hour_spinner)
    Spinner mHourSpinner;
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
