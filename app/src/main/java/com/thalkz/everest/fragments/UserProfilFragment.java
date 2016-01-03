package com.thalkz.everest.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thalkz.everest.R;
import com.thalkz.everest.lists.PlayerList;
import com.thalkz.everest.objects.Player;

/**
 * JournalFragment displays the journal.
 * All Events are published here.
 */

public class UserProfilFragment extends Fragment {

    public UserProfilFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_profil, container, false);

        Player user = PlayerList.getByName("Pops");

        String nameString = user.getName();
        String rankString = Integer.toString(PlayerList.getRankByName(nameString));
        String pointsString = user.getPoints() + " m";
        String vicString = Integer.toString(user.getVictories());
        String defString = Integer.toString(user.getDefeats());

        TextView userName = (TextView) view.findViewById(R.id.user_name_txt);
        TextView userRank = (TextView) view.findViewById(R.id.user_rank_score);
        TextView userPoints = (TextView) view.findViewById(R.id.user_points_score);
        TextView userVictories = (TextView) view.findViewById(R.id.user_victories_score);
        TextView userDefeats = (TextView) view.findViewById(R.id.user_defeats_score);

        userName.setText(nameString);
        userRank.setText(rankString);
        userPoints.setText(pointsString);
        userVictories.setText(vicString);
        userDefeats.setText(defString);

        return view;
    }
}
