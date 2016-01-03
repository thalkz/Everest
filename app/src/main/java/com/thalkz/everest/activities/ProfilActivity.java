package com.thalkz.everest.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.thalkz.everest.R;
import com.thalkz.everest.lists.PlayerList;
import com.thalkz.everest.objects.Player;

public class ProfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        String name = getIntent().getStringExtra("name");
        Player user = PlayerList.getByName(name);

        String nameString = name;
        String rankString = Integer.toString(PlayerList.getRankByName(nameString));
        String pointsString = user.getPoints() + " m";
        String vicString = Integer.toString(user.getVictories());
        String defString = Integer.toString(user.getDefeats());

        getSupportActionBar().setTitle(nameString);

        TextView userRank = (TextView) this.findViewById(R.id.profil_rank_score);
        TextView userPoints = (TextView) this.findViewById(R.id.profil_points_score);
        TextView userVictories = (TextView) this.findViewById(R.id.profil_victories_score);
        TextView userDefeats = (TextView) this.findViewById(R.id.profil_defeats_score);

        userRank.setText(rankString);
        userPoints.setText(pointsString);
        userVictories.setText(vicString);
        userDefeats.setText(defString);

    }
}
