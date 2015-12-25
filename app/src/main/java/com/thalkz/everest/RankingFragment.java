package com.thalkz.everest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * RankingFragment displays the players rankings in order.
 */

public class RankingFragment extends Fragment {

    public RankingFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranking, container, false);

        RecyclerView pRecyclerView = (RecyclerView) view.findViewById(R.id.player_rv);
        pRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        pRecyclerView.setAdapter(MainActivity.rankingAdapter);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        pRecyclerView.setItemAnimator(itemAnimator);

        return view;
    }
}
