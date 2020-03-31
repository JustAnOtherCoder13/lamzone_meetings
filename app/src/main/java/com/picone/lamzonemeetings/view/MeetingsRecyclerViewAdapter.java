package com.picone.lamzonemeetings.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.picone.lamzonemeetings.R;
import com.picone.lamzonemeetings.controller.DummyMeetingService;
import com.picone.lamzonemeetings.model.Meeting;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeetingsRecyclerViewAdapter extends RecyclerView.Adapter<MeetingsRecyclerViewAdapter.ViewHolder> {

    private DummyMeetingService mService = new DummyMeetingService();
    private  List<Meeting> mMeetings = mService.getMeetings();



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_items, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Meeting meeting = mMeetings.get(position);
        holder.mMeetingTitle.setText(meeting.getPlace().concat(" ").concat(String.valueOf(meeting.getHour())).concat(" ").concat(meeting.getSubject()));
        holder.mMeetingParticipants.setText(meeting.getParticipants());
        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mService.deleteMeeting(meeting);
                Log.i("test", "onClick: "+ mMeetings.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.mMeetings.size();
    }
    private void initList(){
        mMeetings = mService.getMeetings();
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
            ButterKnife.bind(this,itemView);
        }
    }
}
