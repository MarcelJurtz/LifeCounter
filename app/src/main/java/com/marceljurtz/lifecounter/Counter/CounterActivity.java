package com.marceljurtz.lifecounter.Counter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.QuickContactBadge;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.marceljurtz.lifecounter.About.AboutActivity;
import com.marceljurtz.lifecounter.Dicing.DicingActivity;
import com.marceljurtz.lifecounter.Game.GameActivity;
import com.marceljurtz.lifecounter.Helper.Counter;
import com.marceljurtz.lifecounter.Helper.Player;
import com.marceljurtz.lifecounter.Helper.PlayerID;
import com.marceljurtz.lifecounter.Helper.PreferenceManager;
import com.marceljurtz.lifecounter.R;
import com.marceljurtz.lifecounter.Settings.SettingsActivity;

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

    NavigationView  navigationView;

    public Counter counter;

    Player player1;
    Player player2;
    Player player3;
    Player player4;

    ArrayAdapter<Player> adapter;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().hide();

        preferences = getApplicationContext().getSharedPreferences(PreferenceManager.PREFS, Activity.MODE_PRIVATE);

        player1Layout = (LinearLayout) findViewById(R.id.llCounterPlayer1);
        player2Layout = (LinearLayout) findViewById(R.id.llCounterPlayer2);
        player3Layout = (LinearLayout) findViewById(R.id.llCounterPlayer3);
        player4Layout = (LinearLayout) findViewById(R.id.llCounterPlayer4);

        players = new ArrayList<>();

        player1 = new Player(PlayerID.ONE);
        player2 = new Player(PlayerID.TWO);
        player3 = new Player(PlayerID.THREE);
        player4 = new Player(PlayerID.FOUR);

        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);

        lblCounterHeaderPlayer1 = (TextView) findViewById(R.id.lblCountersPlayer1);
        lblCounterHeaderPlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.OnPlayerIdentificationTap(player1.getPlayerID());
            }
        });
        lblCounterHeaderPlayer2 = (TextView) findViewById(R.id.lblCountersPlayer2);
        lblCounterHeaderPlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.OnPlayerIdentificationTap(player2.getPlayerID());
            }
        });

        lblCounterHeaderPlayer3 = (TextView) findViewById(R.id.lblCountersPlayer3);
        lblCounterHeaderPlayer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.OnPlayerIdentificationTap(player3.getPlayerID());
            }
        });
        lblCounterHeaderPlayer4 = (TextView) findViewById(R.id.lblCountersPlayer4);
        lblCounterHeaderPlayer4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.OnPlayerIdentificationTap(player4.getPlayerID());
            }
        });

        adapter = new ArrayAdapter<Player>(getApplicationContext(), R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);

        presenter = new CounterPresenter(this, preferences, players);
        presenter.OnCreate();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.OnCreateNewCounterButtonTap();
            }
        });

        navigationView = (NavigationView)findViewById(R.id.navigationViewCountermanager);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                switch(id) {
                    case R.id.nav_countermanager_useramount_2:
                        //presenter.OnSettingsButtonClick(ClickType.LONG);
                        presenter.OnMenuEntryTwoPlayerClick();
                        break;
                    case R.id.nav_countermanager_useramount_4:
                        presenter.OnMenuEntryFourPlayerClick();
                        break;
                    case R.id.nav_countermanager_dicing:
                        presenter.OnMenuEntryDicingClick();
                        break;
                    case R.id.nav_countermanager_settings:
                        presenter.OnMenuEntrySettingsClick();
                        break;
                    case R.id.nav_countermanager_about:
                        presenter.OnMenuEntryAboutClick();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onDestroy() {
        presenter.OnDestroy();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        presenter.OnPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.OnResume();
    }

    @Override
    public void LoadCounterAddDialog(ArrayList<Player> players) {

        adapter.clear();
        adapter.addAll(players);

        final Dialog dialog = new Dialog(CounterActivity.this);
        dialog.setContentView(R.layout.dialog_countermanager_new);

        final EditText txtCardDescription = (EditText) dialog.findViewById(R.id.txtCardDescription);
        final EditText txtATK = (EditText) dialog.findViewById(R.id.txtCardAtk);
        final EditText txtDEF = (EditText) dialog.findViewById(R.id.txtCardDef);
        final Spinner spPlayers = (Spinner) dialog.findViewById(R.id.spUserSelection);
        final ImageButton cmdIncrease = (ImageButton)dialog.findViewById(R.id.cmdIncreaseCounter);
        final ImageButton cmdDecrease = (ImageButton)dialog.findViewById(R.id.cmdDecreaseCounter);

        spPlayers.setAdapter(adapter);

        cmdIncrease.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    txtATK.setText(Integer.toString(Integer.parseInt(txtATK.getText().toString()) + 1));
                } catch (NumberFormatException e) {
                    txtATK.setText("1");
                }

                try {
                    txtDEF.setText(Integer.toString(Integer.parseInt(txtDEF.getText().toString()) + 1));
                } catch(NumberFormatException e) {
                    txtDEF.setText("1");
                }
            }
        });

        cmdDecrease.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    txtATK.setText(Integer.toString(Integer.parseInt(txtATK.getText().toString()) - 1));
                } catch (NumberFormatException e) {
                    txtATK.setText("0");
                }

                try {
                    txtDEF.setText(Integer.toString(Integer.parseInt(txtDEF.getText().toString()) - 1));
                } catch(NumberFormatException e) {
                    txtDEF.setText("0");
                }
            }
        });

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
                    presenter.AddCounterToPlayer(((Player) spPlayers.getSelectedItem()).getPlayerID(), counter);
                }
            }
        });

        dialog.show();
    }

    @Override
    public void DisplayNewCounterEntryToPlayer(PlayerID playerId, Counter counter) {
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
                LoadCounterDeletionDialog(wrapper); // TODO: Add Parameters
                return true;
            }
        });

        switch (playerId) {
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
    public void LoadPlayerIdentificationDialog(final PlayerID playerID, final String playername) {
        final Dialog dialog = new Dialog(CounterActivity.this);
        dialog.setContentView(R.layout.dialog_countermanager_playerdescription);

        final EditText txtPlayerDescription = (EditText) dialog.findViewById(R.id.txtCounterPlayerDescription);
        txtPlayerDescription.setText(playername);

        Button dialogButton = (Button) dialog.findViewById(R.id.cmdCounterPlayerDescrptionDialogOK);

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = null;
                String newDescription = txtPlayerDescription.getText().toString();
                dialog.dismiss();

                if (newDescription != null && newDescription.length() > 0) {
                    presenter.OnPlayerIdentificationChanged(playerID, newDescription);
                }
            }
        });

        dialog.show();
    }

    @Override
    public void SetPlayerIdentificationText(PlayerID playerId, String headerText) {
        switch (playerId) {
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

    @Override
    public void LoadCounterDeletionDialog(final LinearLayout wrapperLayout) {
        new AlertDialog.Builder(CounterActivity.this)
                .setTitle(getResources().getString(R.string.dialog_countermanager_delete_title))
                .setMessage(getResources().getString(R.string.dialog_countermanager_delete_message))
                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        presenter.OnCounterDeletionConfirmation(wrapperLayout);
                    }
                })
                .setNegativeButton(getResources().getString(R.string.no), null).show();
    }

    @Override
    public void DeleteAllCounters() {

        for (int i = 0; i < player1Layout.getChildCount(); i++) {
            View v = player1Layout.getChildAt(i);
            if(v.getId() != R.id.lblCountersPlayer1) player1Layout.removeView(v);
        }

        for (int i = 0; i < player2Layout.getChildCount(); i++) {
            View v = player2Layout.getChildAt(i);
            if(v.getId() != R.id.lblCountersPlayer2) player2Layout.removeView(v);
        }

        for (int i = 0; i < player3Layout.getChildCount(); i++) {
            View v = player3Layout.getChildAt(i);
            if(v.getId() != R.id.lblCountersPlayer3) player3Layout.removeView(v);
        }

        for (int i = 0; i < player4Layout.getChildCount(); i++) {
            View v = player4Layout.getChildAt(i);
            if(v.getId() != R.id.lblCountersPlayer4) player4Layout.removeView(v);
        }

        player1Layout.setVisibility(View.GONE);
        player2Layout.setVisibility(View.GONE);
        player3Layout.setVisibility(View.GONE);
        player4Layout.setVisibility(View.GONE);
    }

    @Override
    public void LoadGameActivity() {
        Intent intent = new Intent(getApplicationContext(), GameActivity.class);
        startActivity(intent);
    }

    @Override
    public void LoadDicingActivity() {
        Intent intent = new Intent(getApplicationContext(), DicingActivity.class);
        startActivity(intent);
    }

    @Override
    public void LoadSettingsActivity() {
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void LoadAboutActivity() {
        Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
        startActivity(intent);
    }

    @Override
    public void DeleteCounter(LinearLayout counterLayout) {
        if(((ViewGroup)counterLayout.getParent()).getChildCount() == 2) { // TODO: replace 2 by presenter check for player
            ((ViewGroup) counterLayout.getParent()).setVisibility(View.GONE);
        }
        ((ViewGroup)counterLayout.getParent()).removeView(counterLayout);
    }

    @Override
    public void UpdateCounter(LinearLayout counterLayout) {

    }
}
