package com.thalkz.everest.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
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

    String nameString = "";
    String rankString = "";
    String pointsString = "";
    String vicString = "";
    String defString = "";

    public UserProfilFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_profil, container, false);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        String userName = sharedPref.getString("sharedName", "");

        Player user = PlayerList.getByName(userName);

        if (user != null) {
            nameString = user.getName();
            rankString = Integer.toString(PlayerList.getRankByName(nameString));
            pointsString = user.getPoints() + " m";
            vicString = Integer.toString(user.getVictories());
            defString = Integer.toString(user.getDefeats());
        }

        TextView userRank = (TextView) view.findViewById(R.id.user_rank_score);
        TextView userPoints = (TextView) view.findViewById(R.id.user_points_score);
        TextView userVictories = (TextView) view.findViewById(R.id.user_victories_score);
        TextView userDefeats = (TextView) view.findViewById(R.id.user_defeats_score);

        userRank.setText(rankString);
        userPoints.setText(pointsString);
        userVictories.setText(vicString);
        userDefeats.setText(defString);

        return view;
    }
}
