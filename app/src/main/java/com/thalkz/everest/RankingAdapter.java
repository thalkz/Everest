package com.thalkz.everest;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

/**
 * RankingAdapter binds data to the Ranking recyclerView
 */

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> {

    private Player[] itemsData;
    Context context;

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

        String vicDefText = "V " + itemsData[position].getVictories() + " / D " + itemsData[position].getDefeats();
        String pointsText = itemsData[position].getPoints() + " Points";
        Drawable indic;

        if(itemsData[position].getIndic()==-1){
            indic = ContextCompat.getDrawable(context, R.drawable.ic_down);
        }else if(itemsData[position].getIndic()==1){
            indic = ContextCompat.getDrawable(context, R.drawable.ic_up);
        }else{
            indic = ContextCompat.getDrawable(context, R.drawable.ic_sameRank);
        }

        String rank = Integer.toString(position+1);

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(rank, R.color.colorPrimary); //This can be changed to a real color using getResources().getColor()
        viewHolder.rank.setImageDrawable(drawable);

        viewHolder.name.setText(itemsData[position].getName());
        viewHolder.vicDef.setText(vicDefText);
        viewHolder.points.setText(pointsText);

        viewHolder.indic.setImageDrawable(indic);

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
        public ImageView rank;
        public TextView name;
        public TextView vicDef;
        public TextView points;
        public ImageView indic;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            cv = (FrameLayout) itemLayoutView.findViewById(R.id.player_cv);
            rank = (ImageView) itemLayoutView.findViewById(R.id.player_rank);
            name = (TextView) itemLayoutView.findViewById(R.id.player_name);
            vicDef = (TextView) itemLayoutView.findViewById(R.id.player_vic_def);
            points = (TextView) itemLayoutView.findViewById(R.id.player_points);
            indic = (ImageView) itemLayoutView.findViewById(R.id.player_indic);
        }
    }
}