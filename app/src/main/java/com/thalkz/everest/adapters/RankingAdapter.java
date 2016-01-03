package com.thalkz.everest.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.thalkz.everest.objects.Player;
import com.thalkz.everest.activities.ProfilActivity;
import com.thalkz.everest.R;

/**
 * RankingAdapter binds data to the Ranking recyclerView
 */

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> {

    Context context;
    private Player[] itemsData;

    public RankingAdapter(Player[] itemsData, Context context) {
        this.itemsData = itemsData;
        this.context = context;
    }

    @Override
    public RankingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_cardview, null);

        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        viewHolder.getAdapterPosition();

        String rankText = Integer.toString(position + 1);
        String nameText = itemsData[position].getName();
        String pointsText = itemsData[position].getPoints() + " m";


        viewHolder.rank.setText(rankText);
        viewHolder.name.setText(nameText);
        viewHolder.points.setText(pointsText);

        viewHolder.cv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent profilIntent = new Intent(context, ProfilActivity.class);
                context.startActivity(profilIntent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsData.length;
    }

    public void updateData(Player[] newItemsData) {
        itemsData = newItemsData;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public FrameLayout cv;
        public TextView rank;
        public TextView name;
        public TextView points;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            cv = (FrameLayout) itemLayoutView.findViewById(R.id.player_cv);
            rank = (TextView) itemLayoutView.findViewById(R.id.player_rank);
            name = (TextView) itemLayoutView.findViewById(R.id.player_name);
            points = (TextView) itemLayoutView.findViewById(R.id.player_points);
        }
    }
}