package com.thalkz.everest.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thalkz.everest.R;
import com.thalkz.everest.activities.EventDetailActivity;
import com.thalkz.everest.objects.Event;

/**
 * JournalAdapter binds data to the Journal recyclerView
 */

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.ViewHolder> {

    Context context;
    private Event[] itemsData;

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

        viewHolder.p1c.setText(itemsData[position].getPlayer1());
        viewHolder.p2c.setText(itemsData[position].getPlayer2());

        viewHolder.c1c.setText(Integer.toString(itemsData[position].getCaps1()));
        viewHolder.c2c.setText(Integer.toString(itemsData[position].getCaps2()));
        viewHolder.r1c.setText(Integer.toString(itemsData[position].getRev1()));
        viewHolder.r2c.setText(Integer.toString(itemsData[position].getRev2()));
        viewHolder.b1c.setText(Integer.toString(itemsData[position].getBel1()));
        viewHolder.b2c.setText(Integer.toString(itemsData[position].getBel2()));

        viewHolder.a1c.setText(Integer.toString(itemsData[position].getGain1()));
        viewHolder.a2c.setText(Integer.toString(itemsData[position].getGain2()));

        viewHolder.poster.setText(itemsData[position].getPoster());
        viewHolder.date.setText(itemsData[position].getFormattedDate());

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

        public LinearLayout cv;
        public TextView p1c;
        public TextView p2c;
        public TextView c1c;
        public TextView c2c;
        public TextView r1c;
        public TextView r2c;
        public TextView b1c;
        public TextView b2c;
        public TextView a1c;
        public TextView a2c;
        public TextView poster;
        public TextView date;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            cv = (LinearLayout) itemLayoutView.findViewById(R.id.event_cv);
            p1c = (TextView) itemLayoutView.findViewById(R.id.p1c);
            p2c = (TextView) itemLayoutView.findViewById(R.id.p2c);
            c1c = (TextView) itemLayoutView.findViewById(R.id.c1c);
            c2c = (TextView) itemLayoutView.findViewById(R.id.c2c);
            r1c = (TextView) itemLayoutView.findViewById(R.id.r1c);
            r2c = (TextView) itemLayoutView.findViewById(R.id.r2c);
            b1c = (TextView) itemLayoutView.findViewById(R.id.b1c);
            b2c = (TextView) itemLayoutView.findViewById(R.id.b2c);
            a1c = (TextView) itemLayoutView.findViewById(R.id.a1c);
            a2c = (TextView) itemLayoutView.findViewById(R.id.a2c);
            poster = (TextView) itemLayoutView.findViewById(R.id.event_poster);
            date = (TextView) itemLayoutView.findViewById(R.id.event_date);
        }
    }
}