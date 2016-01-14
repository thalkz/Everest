package com.thalkz.everest.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.thalkz.everest.R;
import com.thalkz.everest.lists.PlayerList;
import com.thalkz.everest.objects.Event;

import static java.lang.Math.pow;

/**
 * MatchActivity lets the user insert the results of a match
 */

public class MatchActivity extends AppCompatActivity implements View.OnClickListener {

    TextView p1;
    TextView p2;
    TextView c1;
    TextView c2;
    TextView r1;
    TextView r2;
    TextView b1;
    TextView b2;
    Context matchContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        getSupportActionBar().setTitle("Publier un match");
        matchContext = this;

        String opponentName = getIntent().getStringExtra("opponentName");

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        final String userName = sharedPref.getString("sharedName", "");

        p1 = (TextView) findViewById(R.id.p1);
        p2 = (TextView) findViewById(R.id.p2);
        c1 = (TextView) findViewById(R.id.c1);
        c2 = (TextView) findViewById(R.id.c2);
        r1 = (TextView) findViewById(R.id.r1);
        r2 = (TextView) findViewById(R.id.r2);
        b1 = (TextView) findViewById(R.id.b1);
        b2 = (TextView) findViewById(R.id.b2);
        TextView cancel = (TextView) findViewById(R.id.mCancel);
        TextView ok = (TextView) findViewById(R.id.mOk);

        if (opponentName == null) {
            choosePlayer2Dialog();
        } else {
            p2.setText(opponentName);
        }

        p1.setText(userName);
        c1.setText("0");
        c2.setText("0");
        r1.setText("0");
        r2.setText("0");
        b1.setText("0");
        b2.setText("0");

        c1.setOnClickListener(this);
        c2.setOnClickListener(this);
        r1.setOnClickListener(this);
        r2.setOnClickListener(this);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);

        p2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePlayer2Dialog();
            }
        });

        p1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePlayer1Dialog();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int winner = getWinner();

                if (winner != 0) {

                    String mes = generateMessage(winner);

                    String p1t = p1.getText().toString();
                    String p2t = p2.getText().toString();
                    int c1t = Integer.parseInt(c1.getText().toString());
                    int c2t = Integer.parseInt(c2.getText().toString());
                    int r1t = Integer.parseInt(r1.getText().toString());
                    int r2t = Integer.parseInt(r2.getText().toString());
                    int b1t = Integer.parseInt(b1.getText().toString());
                    int b2t = Integer.parseInt(b2.getText().toString());

                    int g1t = 0;
                    int g2t = 0;
                    int[] results;

                    int points1 = PlayerList.getByName(p1t).getPoints();
                    int points2 = PlayerList.getByName(p2t).getPoints();
                    if (winner == 1) {
                        results = getPoints(points1, points2);
                        g1t = results[0];
                        g2t = results[1];
                    } else if (winner == 2) {
                        results = getPoints(points2, points1);
                        g1t = results[1];
                        g2t = results[0];
                    }

                    Event e = new Event(p1t, p2t, c1t, c2t, r1t, r2t, b1t, b2t, g1t, g2t, userName);
                    MainActivity.insertEvent(e);

                    finish();

                    Toast.makeText(getApplicationContext(), mes, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Scores non valides", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public void onClick(View v) {

        int max = 4;

        if (v.getId() == R.id.b1 || v.getId() == R.id.b2) {
            max = 8;
        }

        int score = Integer.parseInt(((TextView) v).getText().toString());

        if (score < max) {
            ((TextView) v).setText(Integer.toString(score + 1));
        } else {
            ((TextView) v).setText("0");
        }

        if (score + 1 == max) {
            v.setBackgroundColor(ContextCompat.getColor(matchContext, R.color.colorPrimary));
            ((TextView) v).setTextColor(Color.WHITE);
        } else {
            v.setBackgroundColor(ContextCompat.getColor(matchContext, R.color.white));
            ((TextView) v).setTextColor(Color.BLACK);
        }
    }

    int getWinner() {

        int c1s = Integer.parseInt(c1.getText().toString());
        int c2s = Integer.parseInt(c2.getText().toString());
        int r1s = Integer.parseInt(r1.getText().toString());
        int r2s = Integer.parseInt(r2.getText().toString());
        int b1s = Integer.parseInt(b1.getText().toString());
        int b2s = Integer.parseInt(b2.getText().toString());

        int max = 4;

        if (c1s != max && c2s != max) {
            return 0;
        }

        if (r1s != max && r2s != max) {
            return 0;
        }

        //at least one is at max

        if (c1s == max && c2s == max) {
            return 0;
        }

        if (r1s == max && r2s == max) {
            return 0;
        }

        //both are not at max

        if (c1s == max && r2s == max) {
            if (b1s != 2 * max && b2s != 2 * max) {
                return 0;
            }

            if (b1s == 2 * max && b2s == 2 * max) {
                return 0;
            }
        }

        if (c2s == max && r1s == max) {
            if (b1s != 2 * max && b2s != 2 * max) {
                return 0;
            }

            if (b1s == 2 * max && b2s == 2 * max) {
                return 0;
            }
        }

        //Here, scores are valid, we now get the winner

        if (c1s == max && r1s == max) {
            return 1;
        }

        if (c2s == max && r2s == max) {
            return 2;
        }

        if (b1s == max * 2) {
            return 1;
        }

        if (b2s == max * 2) {
            return 2;
        }

        return 0;

    }

    String generateMessage(int winner) {
        String player1 = p1.getText().toString();
        String player2 = p2.getText().toString();

        switch (winner) {
            case 1:
                return player1 + " a battu " + player2;

            case 2:
                return player2 + " a battu " + player1;

            default:
                return "Erreur";
        }
    }

    void choosePlayer2Dialog() {

        final View aView = getLayoutInflater().inflate(R.layout.choose_player_dialog, null);
        final AutoCompleteTextView xName = (AutoCompleteTextView) aView.findViewById(R.id.player_actv);

        final ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<>(matchContext, android.R.layout.simple_list_item_1, PlayerList.getStringList());
        xName.setAdapter(autoCompleteAdapter);

        final AlertDialog dialog = new AlertDialog.Builder(matchContext)
                .setView(aView)
                .setPositiveButton("Ok", null)
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String x = xName.getText().toString();

                if (PlayerList.getByName(x) != null) {
                    p2.setText(x);
                    dialog.dismiss();
                } else {
                    xName.setError("Inconnu");
                }
            }
        });

    }

    void choosePlayer1Dialog() {

        final View aView = getLayoutInflater().inflate(R.layout.choose_player_dialog, null);
        final AutoCompleteTextView xName = (AutoCompleteTextView) aView.findViewById(R.id.player_actv);

        final ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<>(matchContext, android.R.layout.simple_list_item_1, PlayerList.getStringList());
        xName.setAdapter(autoCompleteAdapter);

        final AlertDialog dialog = new AlertDialog.Builder(matchContext)
                .setView(aView)
                .setPositiveButton("Ok", null)
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String x = xName.getText().toString();

                if (PlayerList.getByName(x) != null) {
                    p1.setText(x);
                    dialog.dismiss();
                } else {
                    xName.setError("Inconnu");
                }
            }
        });

    }

    public int[] getPoints(int win, int los) {

        double estimation_gagnant = 1.0 / (1.0 + pow(10.0, (((double) los - (double) win) / 400)));
        double estimation_perdant = 1.0 / (1.0 + (pow(10.0, (((double) win - (double) los) / 400))));

        int K_gagnant;
        int K_perdant;

        if ((double) win < 1000) {
            K_gagnant = 80;
        } else if (1000 <= (double) win && (double) win < 2000) {
            K_gagnant = 50;
        } else if (2000 <= (double) win && (double) win < 2300) {
            K_gagnant = 30;
        } else {
            K_gagnant = 20;
        }

        if ((double) los < 1000) {
            K_perdant = 80;
        } else if (1000 <= (double) los && (double) los < 2000) {
            K_perdant = 50;
        } else if (2000 <= (double) los && (double) los < 2300) {
            K_perdant = 30;
        } else {
            K_perdant = 20;
        }

        int new_elo_gagnant = (int) ((double) win + K_gagnant * (1 - estimation_gagnant));
        int new_elo_perdant = (int) ((double) los + K_perdant * (0 - estimation_perdant));

        int eloDiffGagnant = (int) (new_elo_gagnant - (double) win);
        int eloDiffPerdant = (int) (new_elo_perdant - (double) los);

        int[] intArray = new int[2];
        intArray[0] = eloDiffGagnant;
        intArray[1] = eloDiffPerdant;

        return intArray;
    }
}