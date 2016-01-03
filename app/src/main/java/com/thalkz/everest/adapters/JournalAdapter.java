package com.thalkz.everest.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.thalkz.everest.objects.Event;
import com.thalkz.everest.activities.EventDetailActivity;
import com.thalkz.everest.R;

/**
 * JournalAdapter binds data to the Journal recyclerView
 */

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.ViewHolder> {

    private Event[] itemsData;
    Context context;

    public JournalAdapter(Event[] itemsData, Context context) {
        this.itemsData = itemsData;
        this.context = context;
    }

    @Override
    public JournalAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_cardview, null);

        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        viewHolder.getAdapterPosition();

        viewHolder.msg.setText(itemsData[position].getMessage());
        viewHolder.date.setText(itemsData[position].getFormattedDate());
        viewHolder.poster.setText(itemsData[position].getPoster());

        viewHolder.cv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent eventDetailIntent = new Intent(context, EventDetailActivity.class);
                context.startActivity(eventDetailIntent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsData.length;
    }

    public void updateData(Event[] newItemsData) {
        itemsData = newItemsData;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public FrameLayout cv;
        public TextView msg;
        public TextView date;
        public TextView poster;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            cv = (FrameLayout) itemLayoutView.findViewById(R.id.event_cv);
            msg = (TextView) itemLayoutView.findViewById(R.id.event_msg);
            date = (TextView) itemLayoutView.findViewById(R.id.event_date);
            poster = (TextView) itemLayoutView.findViewById(R.id.event_poster);

        }
    }
}