package com.picone.lamzonemeetings.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.picone.lamzonemeetings.R;
import com.picone.lamzonemeetings.controller.event.AddNewMeetingEvent;
import com.picone.lamzonemeetings.controller.event.DeleteMeetingEvent;
import com.picone.lamzonemeetings.model.Employee;
import com.picone.lamzonemeetings.model.Meeting;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeetingsRecyclerViewAdapter extends RecyclerView.Adapter<MeetingsRecyclerViewAdapter.ViewHolder> {

    private List<Meeting> mMeetings;
    private List<Employee> mParticipants;
    private List<Employee> mDummyParticipants;
    private String mParticipantsMail;

    MeetingsRecyclerViewAdapter(List<Meeting> items) {
        mMeetings = items;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_items, parent, false);
        EventBus.getDefault().register(this);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Meeting meeting = mMeetings.get(position);
        mDummyParticipants = meeting.getParticipants();
        createParticipantsMail();
        holder.mMeetingTitle.setText(meeting.getSubject().concat(" ").concat(String.valueOf(meeting.getHour())).concat("h").concat(" ").concat(meeting.getPlace()));
        holder.mMeetingParticipants.setText(mParticipantsMail);
        holder.mDeleteButton.setOnClickListener(v -> EventBus.getDefault().post(new DeleteMeetingEvent(meeting)));

    }
    private void createParticipantsMail(){
        mParticipants = mDummyParticipants;
        for (Employee employee:mParticipants){
            String participantMail = employee.getMail();
            String participants = null;
            if (participants == null) participants = participantMail;
            else participants = participants.concat(", ").concat(participantMail);
            mParticipantsMail = participants;
        }

    }
    @Subscribe
    public  void onAddNewMeetingEvent(AddNewMeetingEvent event){
        mParticipants = event.participants;
        for (Employee employee:mParticipants){
            String participantMail = employee.getMail();
            String participants = null;
            if (participants == null) participants = participantMail;
            else participants = participants.concat(", ").concat(participantMail);
            mParticipantsMail = participants;
        }
    }

    public void unregisterPost(){


        }
    @Override
    public int getItemCount() {
        return this.mMeetings.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_circle_img)
        public ImageView mCircleImg;
        @BindView(R.id.item_meeting_title_txt)
        public TextView mMeetingTitle;
        @BindView(R.id.item_meeting_participants_txt)
        public TextView mMeetingParticipants;
        @BindView(R.id.item_delete_img_button)
        public ImageButton mDeleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
