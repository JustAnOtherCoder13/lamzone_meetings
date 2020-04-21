package com.picone.lamzonemeetings.utils;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerViewOnLongClickUtils {

    private final RecyclerView mRecyclerView;
    private OnItemLongClickListener mOnItemLongClickListener;


    private View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (mOnItemLongClickListener != null) {
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                return mOnItemLongClickListener.onItemLongClicked(mRecyclerView, holder.getAdapterPosition(), v);
            }
            return false;
        }
    };

    private RecyclerViewOnLongClickUtils(RecyclerView recyclerView, int itemID) {
        mRecyclerView = recyclerView;
        mRecyclerView.setTag(itemID, this);
        RecyclerView.OnChildAttachStateChangeListener attachListener = new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {
                if (mOnItemLongClickListener != null) {
                    view.setOnLongClickListener(mOnLongClickListener);
                }
            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {
            }
        };
        mRecyclerView.addOnChildAttachStateChangeListener(attachListener);
    }

    public static RecyclerViewOnLongClickUtils addTo(RecyclerView view, int itemID) {
        RecyclerViewOnLongClickUtils support = (RecyclerViewOnLongClickUtils) view.getTag(itemID);
        if (support == null) {
            support = new RecyclerViewOnLongClickUtils(view, itemID);
        }
        return support;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClicked(RecyclerView recyclerView, int position, View v);
    }
}


