package com.picone.lamzonemeetings.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.picone.lamzonemeetings.controller.event.DeleteMeetingEvent;
import com.picone.lamzonemeetings.databinding.RecyclerViewItemsBinding;
import com.picone.lamzonemeetings.model.Meeting;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static com.picone.lamzonemeetings.utils.CircleColorUtils.setCircleColor;
import static com.picone.lamzonemeetings.utils.ParticipantsMailUtils.getParticipantsMail;

public class MeetingsRecyclerViewAdapter extends RecyclerView.Adapter<MeetingsRecyclerViewAdapter.ViewHolder> {

    private List<Meeting> mMeetings;


    MeetingsRecyclerViewAdapter(List<Meeting> items) { mMeetings = items; }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerViewItemsBinding binding = RecyclerViewItemsBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Meeting meeting = mMeetings.get(position);
        holder.binding.itemCircleImg.setColorFilter(ContextCompat.getColor(holder.binding.itemCircleImg.getContext(), setCircleColor(meeting.getDate())));
        holder.binding.itemMeetingTitleTxt.setText(meeting.getSubject().concat(" - ").concat(String.valueOf(meeting.getHour())).concat(" - ").concat(meeting.getPlace()));
        holder.binding.itemMeetingParticipantsTxt.setText(getParticipantsMail(meeting));
        holder.binding.itemDeleteImgButton.setOnClickListener(v -> EventBus.getDefault().post(new DeleteMeetingEvent(meeting)));
    }

    @Override
    public int getItemCount() {
        return this.mMeetings.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private RecyclerViewItemsBinding binding;

        ViewHolder(RecyclerViewItemsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}