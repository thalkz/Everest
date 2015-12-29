package com.thalkz.everest;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * RankingAdapter binds data to the Ranking recyclerView
 */

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> {

    private Player[] itemsData;

    public RankingAdapter(Player[] itemsData) {
        this.itemsData = itemsData;
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

        String vicDefText = "V " + itemsData[position].getVictories() + " / D " + itemsData[position].getDefeats();
        String pointsText = itemsData[position].getPoints() + " Points";
        String indicText;

        if(itemsData[position].getIndic()==-1){
            indicText = "v";
        }else if(itemsData[position].getIndic()==1){
            indicText = "^";
        }else{
            indicText = "-";
        }

        String rank = Integer.toString(position+1);

        viewHolder.rank.setText(rank);
        viewHolder.name.setText(itemsData[position].getName());
        viewHolder.vicDef.setText(vicDefText);
        viewHolder.points.setText(pointsText);
        viewHolder.indic.setText(indicText);

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

    public void updateData(Player[] newItemsData) {
        itemsData = newItemsData;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public FrameLayout cv;
        public TextView rank;
        public TextView name;
        public TextView vicDef;
        public TextView points;
        public TextView indic;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            cv = (FrameLayout) itemLayoutView.findViewById(R.id.player_cv);
            rank = (TextView) itemLayoutView.findViewById(R.id.player_rank);
            name = (TextView) itemLayoutView.findViewById(R.id.player_name);
            vicDef = (TextView) itemLayoutView.findViewById(R.id.player_vic_def);
            points = (TextView) itemLayoutView.findViewById(R.id.player_points);
            indic = (TextView) itemLayoutView.findViewById(R.id.player_indic);
        }
    }
}