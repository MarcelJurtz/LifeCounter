package com.marceljurtz.lifecounter.Counter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.marceljurtz.lifecounter.Helper.Counter;
import com.marceljurtz.lifecounter.Helper.Player;
import com.marceljurtz.lifecounter.Helper.PlayerID;
import com.marceljurtz.lifecounter.R;

import java.util.ArrayList;

public class CounterActivity extends AppCompatActivity {

    private CounterPresenter presenter;

    // TODO
    ArrayList<Player> players;

    //LinearLayout mainLayout;
    LinearLayout player1Layout;
    LinearLayout player2Layout;
    LinearLayout player3Layout;
    LinearLayout player4Layout;

    public Counter counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        player1Layout = (LinearLayout)findViewById(R.id.llCounterPlayer1);
        player2Layout = (LinearLayout)findViewById(R.id.llCounterPlayer2);
        player3Layout = (LinearLayout)findViewById(R.id.llCounterPlayer3);
        player4Layout = (LinearLayout)findViewById(R.id.llCounterPlayer4);

        // TODO
        players = new ArrayList<>();
        players.add(new Player(PlayerID.ONE));
        players.add(new Player(PlayerID.TWO));
        players.add(new Player(PlayerID.THREE));
        players.add(new Player(PlayerID.FOUR));

        final ArrayAdapter<Player> adapter =
                new ArrayAdapter<Player>(getApplicationContext(), R.layout.spinner_item, players);
        adapter.setDropDownViewResource(R.layout.spinner_item);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // custom dialog
                final Dialog dialog = new Dialog(CounterActivity.this);
                dialog.setContentView(R.layout.dialog_countermanager_new);
                //dialog.setTitle("Title...");

                final EditText txtCardDescription = (EditText)dialog.findViewById(R.id.txtCardDescription);
                final EditText txtATK = (EditText)dialog.findViewById(R.id.txtCardAtk);
                final EditText txtDEF = (EditText)dialog.findViewById(R.id.txtCardDef);
                Spinner spPlayers = (Spinner)dialog.findViewById(R.id.spUserSelection);
                spPlayers.setAdapter(adapter);

                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                // if button is clicked, close the custom dialog

                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        counter = null;
                        try {
                            counter = new Counter(txtCardDescription.getText().toString(),
                                    Integer.parseInt(txtATK.getText().toString()),
                                    Integer.parseInt(txtDEF.getText().toString()));


                            /*
                            LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                            LinearLayout wrapper = new LinearLayout(getApplicationContext());
                            wrapper.setOrientation(LinearLayout.HORIZONTAL);
                            wrapper.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT););
                            //LinearLayout.LayoutParams wrapperParams = new LinearLayout.LayoutParams()

                            TextView lblDescription = new TextView(getApplicationContext());
                            lblDescription.setText(counter.getDescription());
                            lblDescription.setLayoutParams(llParams);
                            wrapper.addView(lblDescription);

                            TextView lblATK = new TextView(getApplicationContext());
                            lblATK.setText(counter.getATK());
                            lblATK.setLayoutParams(llParams);
                            wrapper.addView(lblATK);

                            TextView lblDivider = new TextView(getApplicationContext());
                            lblDivider.setText("/");
                            lblDivider.setLayoutParams(llParams);
                            wrapper.addView(lblDivider);

                            TextView lblDEF = new TextView(getApplicationContext());
                            lblDEF.setText(counter.getDEF());
                            lblDEF.setLayoutParams(llParams);
                            wrapper.addView(lblDEF);

                            mainLayout.addView(wrapper);
                            */

                        } catch(NullPointerException | NumberFormatException ex) {
                            Snackbar.make(findViewById(android.R.id.content), R.string.dialog_countermanager_error_invalid_entry, Snackbar.LENGTH_LONG).show();
                        }

                        dialog.dismiss();

                        if(counter != null) {
                            // TODO
                            AddNewCounterEntry(new Player(PlayerID.ONE));
                        }
                    }
                });

                dialog.show();
            }
        });

        presenter = new CounterPresenter();
        presenter.onCreate();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        presenter.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    private void AddNewCounterEntry(Player player) {

        int paddingLeft = 50;
        int paddingRight = 50;
        int fontSize = 24;
        /*
        TextView hello = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        hello.setLayoutParams(params);
        hello.setText("Hello");

        mainLayout.addView(hello);
        */
        LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams llParamsSpacer = new LinearLayout.LayoutParams(0 ,0, 1f);

        LinearLayout wrapper = new LinearLayout(getApplicationContext());
        wrapper.setOrientation(LinearLayout.HORIZONTAL);
        wrapper.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        wrapper.setPadding(paddingLeft,0,paddingRight,0);

        //LinearLayout.LayoutParams wrapperParams = new LinearLayout.LayoutParams()

        TextView lblDescription = new TextView(getApplicationContext());
        lblDescription.setText(counter.getDescription());
        lblDescription.setLayoutParams(llParams);
        lblDescription.setTextSize(fontSize);
        lblDescription.setTextColor(getResources().getColor(R.color.textColor));
        wrapper.addView(lblDescription);

        View spacer = new View(getApplicationContext());
        spacer.setLayoutParams(llParamsSpacer);
        wrapper.addView(spacer);


        TextView lblATK = new TextView(getApplicationContext());
        lblATK.setText(counter.getATK() + "");
        lblATK.setLayoutParams(llParams);
        lblATK.setTextSize(fontSize);
        lblATK.setTextColor(getResources().getColor(R.color.textColor));
        wrapper.addView(lblATK);

        TextView lblDivider = new TextView(getApplicationContext());
        lblDivider.setText(" / ");
        lblDivider.setLayoutParams(llParams);
        lblDivider.setTextSize(fontSize);
        lblDivider.setTextColor(getResources().getColor(R.color.textColor));
        wrapper.addView(lblDivider);

        TextView lblDEF = new TextView(getApplicationContext());
        lblDEF.setText(counter.getDEF() + "");
        lblDEF.setLayoutParams(llParams);
        lblDEF.setTextSize(fontSize);
        lblDEF.setTextColor(getResources().getColor(R.color.textColor));
        wrapper.addView(lblDEF);

        wrapper.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(CounterActivity.this)
                        .setTitle(getResources().getString(R.string.dialog_countermanager_delete_title))
                        .setMessage(getResources().getString(R.string.dialog_countermanager_delete_message))
                        .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // TODO Delete item
                            }})
                        .setNegativeButton(getResources().getString(R.string.no), null).show();
                return true;
            }
        });

        switch(player.getPlayerID()) {
            case ONE:
                player1Layout.addView(wrapper);
                player1Layout.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }

        //mainLayout.addView(wrapper);
    }
}
