package com.thalkz.everest.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.thalkz.everest.R;
import com.thalkz.everest.lists.PlayerList;
import com.thalkz.everest.objects.Player;

public class ProfilActivity extends AppCompatActivity {

    Context profilContext;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        profilContext = this;

        name = getIntent().getStringExtra("name");
        Player user = PlayerList.getByName(name);

        if(user!=null){
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
            FrameLayout p = (FrameLayout) this.findViewById(R.id.publier_card);

            userRank.setText(rankString);
            userPoints.setText(pointsString);
            userVictories.setText(vicString);
            userDefeats.setText(defString);

            p.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent matchIntent = new Intent(profilContext, MatchActivity.class);
                    matchIntent.putExtra("opponentName", name);
                    startActivity(matchIntent);
                }
            });
        }
    }
}
