package com.thalkz.everest;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * JournalAdapter binds data to the Journal
 */

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.ViewHolder> {

    private Event[] itemsData;
    private Context context;

    public JournalAdapter(Event[] itemsData, Context context) {
        this.itemsData = itemsData;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public JournalAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_cardview, null);

        // create ViewHolder

        return new ViewHolder(itemLayoutView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData
        viewHolder.getAdapterPosition();

        /**
        viewHolder.TxtSurnom.setText(itemsData[position].getSurnom());

        viewHolder.cv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            }
        });*/
    }

    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemsData.length;
    }

    public void updateData(Event[] newItemsData) {
        itemsData = newItemsData;
        notifyDataSetChanged();
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        /*public CardView cv;
        public TextView TxtSurnom;*/

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            /*TxtSurnom = (TextView) itemLayoutView.findViewById(R.id.list_item_surnom_textview);
            cv = (CardView) itemLayoutView.findViewById(R.id.cv);*/

        }
    }
}