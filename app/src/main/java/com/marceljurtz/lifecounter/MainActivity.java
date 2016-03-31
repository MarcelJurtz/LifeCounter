package com.marceljurtz.lifecounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    RelativeLayout mainLayout;

    ImageButton cmdPlusGuest;
    ImageButton cmdPlusHome;
    ImageButton cmdMinusGuest;
    ImageButton cmdMinusHome;
    ImageButton cmdResetLP;

    TextView txtLifeCountGuest;
    TextView txtLifeCountHome;

    int LP_Home;
    int LP_Guest;
    int LP_Default;

    HashSet<View> views;
    final int PLAYER_HOME = 0;
    final int PLAYER_GUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.hide();

        mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);
        LP_Default = 20;

        views = new HashSet<View>();

        /* ### TextViews ### */
        txtLifeCountGuest = (TextView)findViewById(R.id.txtLifeCountGuest);
        txtLifeCountHome = (TextView)findViewById(R.id.txtLifeCountHome);
        views.add(txtLifeCountGuest);
        views.add(txtLifeCountHome);

        reset();

        /* ### ImageButtons ### */

        // Guest - Minus
        cmdMinusGuest = (ImageButton)findViewById(R.id.cmdMinusGuest);
        cmdMinusGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(LP_Guest > 1) {
                    LP_Guest--;
                    update(LP_Guest, txtLifeCountGuest);
                }
                else {
                    setWinner(PLAYER_HOME);
                }
            }
        });
        views.add(cmdMinusGuest);

        // Home - Minus
        cmdMinusHome = (ImageButton)findViewById(R.id.cmdMinusHome);
        cmdMinusHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(LP_Home > 1) {
                    LP_Home--;
                    update(LP_Home, txtLifeCountHome);
                }
                else {
                    setWinner(PLAYER_GUEST);
                }
            }
        });
        views.add(cmdMinusHome);

        // Guest - Plus
        cmdPlusGuest = (ImageButton)findViewById(R.id.cmdPlusGuest);
        cmdPlusGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LP_Guest++;
                update(LP_Guest,txtLifeCountGuest);
            }
        });
        views.add(cmdPlusGuest);

        // Home - Plus
        cmdPlusHome = (ImageButton)findViewById(R.id.cmdPlusHome);
        cmdPlusHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    LP_Home++;
                    update(LP_Home, txtLifeCountHome);
            }
        });
        views.add(cmdPlusHome);

        // Reset
        cmdResetLP = (ImageButton)findViewById(R.id.cmdResetLP);
        cmdResetLP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
    }

    // Update TextView
    private void update(int LP, TextView txtLifePoints) {
            txtLifePoints.setText(LP+"");
    }

    // Beide Leben wieder auf 20 setzen
    private void reset() {
        for(View view: views) {
            view.setVisibility(View.VISIBLE);
        }
        LP_Guest = LP_Default;
        LP_Home = LP_Default;
        txtLifeCountGuest.setText(LP_Guest+"");
        txtLifeCountHome.setText(LP_Home+"");
        mainLayout.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.background_small));
    }

    private void setWinner(int player) {
        for(View view: views) {
            view.setVisibility(View.INVISIBLE);
        }
        if(player == PLAYER_HOME) {
            mainLayout.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.background_small_winner_home));
        }
        else {
            mainLayout.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.background_small_winner_guest));
        }
    }
}
