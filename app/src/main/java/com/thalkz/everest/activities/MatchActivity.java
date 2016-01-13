package com.thalkz.everest.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.thalkz.everest.R;
import com.thalkz.everest.lists.PlayerList;
import com.thalkz.everest.objects.Event;
import com.thalkz.everest.objects.Player;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        getSupportActionBar().setTitle("Publier un match");

        String opponentName = getIntent().getStringExtra("opponentName");
        Player opponent = PlayerList.getByName(opponentName);

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

        p1.setText("Pops");
        p2.setText(opponentName);
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

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int winner = getWinner();

                if(winner!=0){

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


                    Event e = new Event(p1t, p2t, c1t, c2t, r1t, r2t ,b1t, b2t, g1t, g2t, "Pops" );
                    MainActivity.insertEvent(e);

                    finish();

                    Toast.makeText(getApplicationContext(),mes, Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(getApplicationContext(), "Scores non valides" , Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public void onClick(View v) {

        int max = 4;

        if(v.getId()==R.id.b1||v.getId()==R.id.b2){
            max = 8;
        }

        int score = Integer.parseInt(((TextView) v).getText().toString());

        if(score<max){
            ((TextView) v).setText(Integer.toString(score + 1));
        }
        else{
            ((TextView) v).setText("0");
        }

        if(score+1 == max){
            v.setBackground(getDrawable(R.color.colorPrimary));
            ((TextView) v).setTextColor(Color.WHITE);
        }
        else{
            v.setBackground(getDrawable(R.color.white));
            ((TextView) v).setTextColor(Color.BLACK);
        }
    }

    int getWinner(){

        int c1s = Integer.parseInt(c1.getText().toString());
        int c2s = Integer.parseInt(c2.getText().toString());
        int r1s = Integer.parseInt(r1.getText().toString());
        int r2s = Integer.parseInt(r2.getText().toString());
        int b1s = Integer.parseInt(b1.getText().toString());
        int b2s = Integer.parseInt(b2.getText().toString());

        int max = 4;

        if(c1s!=max&&c2s!=max){
            return 0;
        }

        if(r1s!=max&&r2s!=max){
            return 0;
        }

        //at least one is at max

        if(c1s==max&&c2s==max){
            return 0;
        }

        if(r1s==max&&r2s==max){
            return 0;
        }

        //both are not at max

        if(c1s==max&&r2s==max){
            if(b1s!=2*max&&b2s!=2*max){
                return 0;
            }

            if(b1s==2*max&&b2s==2*max){
                return 0;
            }
        }

        if(c2s==max&&r1s==max){
            if(b1s!=2*max&&b2s!=2*max){
                return 0;
            }

            if(b1s==2*max&&b2s==2*max){
                return 0;
            }
        }

        //Here, scores are valid, we now get the winner

        if(c1s==max&&r1s==max){
            return 1;
        }

        if(c2s==max&&r2s==max){
            return 2;
        }

        if(b1s==max*2){
            return 1;
        }

        if(b2s==max*2){
            return 2;
        }

        return 0;

    }

    String generateMessage(int winner){
        String player1 = p1.getText().toString();
        String player2 = p2.getText().toString();

        switch(winner){
            case 1 :
                return player1 +" a battu " + player2;

            case 2 :
                return player2 + " a battu " + player1;

            default:
                return "Erreur";
        }
    }

}