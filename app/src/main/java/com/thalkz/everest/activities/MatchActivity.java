package com.thalkz.everest.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.thalkz.everest.R;
import com.thalkz.everest.lists.PlayerList;
import com.thalkz.everest.objects.Player;

/**
 * MatchActivity lets the user insert the results of a match
 */

public class MatchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        getSupportActionBar().setTitle("Publier un match");

        String name = getIntent().getStringExtra("name");
        Player opponent = PlayerList.getByName(name);

        String nameString = name;

    }
}