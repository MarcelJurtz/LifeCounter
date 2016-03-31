package com.marceljurtz.lifecounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.hide();

        LP_Default = 20;

        /* ### TextViews ### */
        txtLifeCountGuest = (TextView)findViewById(R.id.txtLifeCountGuest);
        txtLifeCountHome = (TextView)findViewById(R.id.txtLifeCountHome);

        reset();

        /* ### ImageButtons ### */

        // Guest - Minus
        cmdMinusGuest = (ImageButton)findViewById(R.id.cmdMinusGuest);
        cmdMinusGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(LP_Guest > 0) {
                    LP_Guest--;
                    update(LP_Guest, txtLifeCountGuest);
                }
            }
        });

        // Home - Minus
        cmdMinusHome = (ImageButton)findViewById(R.id.cmdMinusHome);
        cmdMinusHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(LP_Home > 0) {
                    LP_Home--;
                    update(LP_Home, txtLifeCountHome);
                }
            }
        });

        // Guest - Plus
        cmdPlusGuest = (ImageButton)findViewById(R.id.cmdPlusGuest);
        cmdPlusGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LP_Guest++;
                update(LP_Guest,txtLifeCountGuest);
            }
        });

        // Home - Plus
        cmdPlusHome = (ImageButton)findViewById(R.id.cmdPlusHome);
        cmdPlusHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    LP_Home++;
                    update(LP_Home, txtLifeCountHome);
            }
        });

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
        LP_Guest = LP_Default;
        LP_Home = LP_Default;
        txtLifeCountGuest.setText(LP_Guest+"");
        txtLifeCountHome.setText(LP_Home+"");
    }
}
