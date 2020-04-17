package com.picone.lamzonemeetings.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.picone.lamzonemeetings.R;
import com.picone.lamzonemeetings.controller.event.DeleteMeetingEvent;
import com.picone.lamzonemeetings.model.Meeting;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.picone.lamzonemeetings.utils.CircleColorUtils.setCircleColor;
import static com.picone.lamzonemeetings.utils.ParticipantsMailUtils.getParticipantsMail;

public class MeetingsRecyclerViewAdapter extends RecyclerView.Adapter<MeetingsRecyclerViewAdapter.ViewHolder> {

    private List<Meeting> mMeetings;

    MeetingsRecyclerViewAdapter(List<Meeting> items) { mMeetings = items;}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Meeting meeting = mMeetings.get(position);
        holder.mCircleImg.setColorFilter(ContextCompat.getColor(holder.mCircleImg.getContext(),setCircleColor(meeting)));
        holder.mMeetingTitle.setText(meeting.getSubject().concat(" - ").concat(String.valueOf(meeting.getHour())).concat(" - ").concat(meeting.getPlace()));
        holder.mMeetingParticipants.setText(getParticipantsMail(meeting));
        holder.mDeleteButton.setOnClickListener(v -> EventBus.getDefault().post(new DeleteMeetingEvent(meeting)));
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

        ViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
