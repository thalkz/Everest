package com.thalkz.everest;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * JournalAdapter binds data to the Journal recyclerView
 */

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.ViewHolder> {

    private Event[] itemsData;

    public JournalAdapter(Event[] itemsData) {
        this.itemsData = itemsData;
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

        public CardView cv;
        public TextView msg;
        public TextView date;
        public TextView poster;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            cv = (CardView) itemLayoutView.findViewById(R.id.event_cv);
            msg = (TextView) itemLayoutView.findViewById(R.id.event_msg);
            date = (TextView) itemLayoutView.findViewById(R.id.event_date);
            poster = (TextView) itemLayoutView.findViewById(R.id.event_poster);

        }
    }
}