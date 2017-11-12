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
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.marceljurtz.lifecounter.Helper.Counter;
import com.marceljurtz.lifecounter.Helper.Player;
import com.marceljurtz.lifecounter.Helper.PlayerID;
import com.marceljurtz.lifecounter.R;

import java.util.ArrayList;

public class CounterActivity extends AppCompatActivity implements ICounterView {

    private CounterPresenter presenter;

    // TODO
    ArrayList<Player> players;

    //LinearLayout mainLayout;
    LinearLayout player1Layout;
    LinearLayout player2Layout;
    LinearLayout player3Layout;
    LinearLayout player4Layout;

    TextView lblCounterHeaderPlayer1;
    TextView lblCounterHeaderPlayer2;
    TextView lblCounterHeaderPlayer3;
    TextView lblCounterHeaderPlayer4;

    public Counter counter;

    Player player1;
    Player player2;
    Player player3;
    Player player4;

    ArrayAdapter<Player> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        player1Layout = (LinearLayout) findViewById(R.id.llCounterPlayer1);
        player2Layout = (LinearLayout) findViewById(R.id.llCounterPlayer2);
        player3Layout = (LinearLayout) findViewById(R.id.llCounterPlayer3);
        player4Layout = (LinearLayout) findViewById(R.id.llCounterPlayer4);

        player1 = new Player(PlayerID.ONE);
        player2 = new Player(PlayerID.TWO);
        player3 = new Player(PlayerID.THREE);
        player4 = new Player(PlayerID.FOUR);

        lblCounterHeaderPlayer1 = (TextView) findViewById(R.id.lblCountersPlayer1);
        lblCounterHeaderPlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadPlayerDescriptionDialog(player1);
            }
        });
        lblCounterHeaderPlayer2 = (TextView) findViewById(R.id.lblCountersPlayer2);
        lblCounterHeaderPlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadPlayerDescriptionDialog(player2);
            }
        });
        lblCounterHeaderPlayer3 = (TextView) findViewById(R.id.lblCountersPlayer3);
        lblCounterHeaderPlayer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadPlayerDescriptionDialog(player3);
            }
        });
        lblCounterHeaderPlayer4 = (TextView) findViewById(R.id.lblCountersPlayer4);
        lblCounterHeaderPlayer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadPlayerDescriptionDialog(player4);
            }
        });

        // TODO
        players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);

        adapter = new ArrayAdapter<Player>(getApplicationContext(), R.layout.spinner_item, players);
        adapter.setDropDownViewResource(R.layout.spinner_item);

        presenter = new CounterPresenter(this, players);
        presenter.onCreate();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.OnCreateNewCounterButtonTap();
            }
        });
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

    @Override
    public void LoadNewCounterDialog() {
        final Dialog dialog = new Dialog(CounterActivity.this);
        dialog.setContentView(R.layout.dialog_countermanager_new);

        final EditText txtCardDescription = (EditText) dialog.findViewById(R.id.txtCardDescription);
        final EditText txtATK = (EditText) dialog.findViewById(R.id.txtCardAtk);
        final EditText txtDEF = (EditText) dialog.findViewById(R.id.txtCardDef);
        final Spinner spPlayers = (Spinner) dialog.findViewById(R.id.spUserSelection);
        spPlayers.setAdapter(adapter);

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = null;
                try {
                    counter = new Counter(txtCardDescription.getText().toString(),
                            Integer.parseInt(txtATK.getText().toString()),
                            Integer.parseInt(txtDEF.getText().toString()));

                } catch (NullPointerException | NumberFormatException ex) {
                    Snackbar.make(findViewById(android.R.id.content), R.string.dialog_countermanager_error_invalid_entry, Snackbar.LENGTH_LONG).show();
                }

                dialog.dismiss();

                if (counter != null) {
                    presenter.AddCounterToPlayer((Player) spPlayers.getSelectedItem(), counter);
                }
            }
        });

        dialog.show();
    }

    @Override
    public void DisplayNewCounterEntryToPlayer(Player player, Counter counter) {
        int paddingLeft = 50;
        int paddingRight = 50;
        int fontSize = 24;

        LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams llParamsSpacer = new LinearLayout.LayoutParams(0, 0, 1f);

        final LinearLayout wrapper = new LinearLayout(getApplicationContext());
        wrapper.setOrientation(LinearLayout.HORIZONTAL);
        wrapper.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        wrapper.setPadding(paddingLeft, 0, paddingRight, 0);

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
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.no), null).show();
                return true;
            }
        });

        switch (player.getPlayerID()) {
            case ONE:
                player1Layout.addView(wrapper);
                player1Layout.setVisibility(View.VISIBLE);
                break;
            case TWO:
                player2Layout.addView(wrapper);
                player2Layout.setVisibility(View.VISIBLE);
                break;
            case THREE:
                player3Layout.addView(wrapper);
                player3Layout.setVisibility(View.VISIBLE);
                break;
            case FOUR:
                player4Layout.addView(wrapper);
                player4Layout.setVisibility(View.VISIBLE);
                break;
            default:
                break;


        }
    }

    @Override
    public void LoadPlayerDescriptionDialog(final Player player) {
        final Dialog dialog = new Dialog(CounterActivity.this);
        dialog.setContentView(R.layout.dialog_countermanager_playerdescription);

        final EditText txtPlayerDescription = (EditText) dialog.findViewById(R.id.txtCounterPlayerDescription);
        txtPlayerDescription.setText(player.getPlayerIdentification());

        Button dialogButton = (Button) dialog.findViewById(R.id.cmdCounterPlayerDescrptionDialogOK);

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = null;
                String newDescription = txtPlayerDescription.getText().toString();
                dialog.dismiss();

                if (newDescription != null && newDescription.length() > 0) {
                    presenter.OnPlayerIdentificationChanged(player, newDescription);
                }
            }
        });

        dialog.show();
    }

    @Override
    public void SetPlayerLabelHeader(Player player, String headerText) {
        switch (player.getPlayerID()) {
            case ONE:
                lblCounterHeaderPlayer1.setText(headerText);
                break;
            case TWO:
                lblCounterHeaderPlayer2.setText(headerText);
                break;
            case THREE:
                lblCounterHeaderPlayer3.setText(headerText);
                break;
            case FOUR:
                lblCounterHeaderPlayer4.setText(headerText);
                break;
            default:
                break;
        }
    }
}
