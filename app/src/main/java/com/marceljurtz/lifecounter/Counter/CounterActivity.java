package com.marceljurtz.lifecounter.Counter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.marceljurtz.lifecounter.About.AboutActivity;
import com.marceljurtz.lifecounter.Dicing.DicingActivity;
import com.marceljurtz.lifecounter.Game.GameActivity;
import com.marceljurtz.lifecounter.Helper.Counter;
import com.marceljurtz.lifecounter.Helper.Player;
import com.marceljurtz.lifecounter.Helper.PlayerID;
import com.marceljurtz.lifecounter.Helper.PreferenceManager;
import com.marceljurtz.lifecounter.Helper.ViewHelper;
import com.marceljurtz.lifecounter.R;
import com.marceljurtz.lifecounter.Settings.SettingsActivity;

import org.w3c.dom.Text;

import java.io.InvalidObjectException;
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

    public Counter counterToAdd;
    public Counter counterToEdit;

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

        presenter = new CounterPresenter(this, preferences, players);

        lblCounterHeaderPlayer1 = (TextView) findViewById(R.id.lblCountersPlayer1);
        lblCounterHeaderPlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.OnPlayerIdentificationTap(player1.GetPlayerID());
            }
        });
        lblCounterHeaderPlayer1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                presenter.OnPlayerIdentificationLongTap(player1.GetPlayerID());
                return true;
            }
        });

        lblCounterHeaderPlayer2 = (TextView) findViewById(R.id.lblCountersPlayer2);
        lblCounterHeaderPlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.OnPlayerIdentificationTap(player2.GetPlayerID());
            }
        });
        lblCounterHeaderPlayer2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                presenter.OnPlayerIdentificationLongTap(player2.GetPlayerID());
                return true;
            }
        });

        lblCounterHeaderPlayer3 = (TextView) findViewById(R.id.lblCountersPlayer3);
        lblCounterHeaderPlayer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.OnPlayerIdentificationTap(player3.GetPlayerID());
            }
        });
        lblCounterHeaderPlayer3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                presenter.OnPlayerIdentificationLongTap(player3.GetPlayerID());
                return true;
            }
        });

        lblCounterHeaderPlayer4 = (TextView) findViewById(R.id.lblCountersPlayer4);
        lblCounterHeaderPlayer4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.OnPlayerIdentificationTap(player4.GetPlayerID());
            }
        });
        lblCounterHeaderPlayer4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                presenter.OnPlayerIdentificationLongTap(player4.GetPlayerID());
                return true;
            }
        });

        adapter = new ArrayAdapter<Player>(getApplicationContext(), R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);

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
                counterToAdd = null;
                try {
                    counterToAdd = new Counter(txtCardDescription.getText().toString(),
                            Integer.parseInt(txtATK.getText().toString()),
                            Integer.parseInt(txtDEF.getText().toString()));

                } catch (NullPointerException | NumberFormatException ex) {
                    DisplayErrorMessage(getString(R.string.dialog_countermanager_error_invalid_entry));
                }

                dialog.dismiss();

                if (counterToAdd != null) {
                    presenter.AddCounterToPlayer(((Player) spPlayers.getSelectedItem()).GetPlayerID(), counterToAdd);
                }
            }
        });

        dialog.show();
    }

    @Override
    public void AddCounterToPlayer(PlayerID playerId, Counter counter) {
        int paddingLeft = 50;
        int paddingRight = 50;
        int fontSize = 24;

        LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams llParamsSpacer = new LinearLayout.LayoutParams(0, 0, 1f);

        final LinearLayout wrapper = new LinearLayout(getApplicationContext());
        wrapper.setTag(counter.GetIdentifier());
        wrapper.setOrientation(LinearLayout.HORIZONTAL);
        wrapper.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        wrapper.setPadding(paddingLeft, 0, paddingRight, 0);

        TextView lblDescription = new TextView(getApplicationContext());
        lblDescription.setText(counter.GetDescription());
        lblDescription.setLayoutParams(llParams);
        lblDescription.setTextSize(fontSize);
        lblDescription.setTextColor(getResources().getColor(R.color.textColor));
        lblDescription.setTag(ViewHelper.lblDescriptionTag);
        wrapper.addView(lblDescription);

        View spacer = new View(getApplicationContext());
        spacer.setLayoutParams(llParamsSpacer);
        wrapper.addView(spacer);

        TextView lblATK = new TextView(getApplicationContext());
        lblATK.setText(counter.GetATK() + "");
        lblATK.setLayoutParams(llParams);
        lblATK.setTextSize(fontSize);
        lblATK.setTextColor(getResources().getColor(R.color.textColor));
        lblATK.setTag(ViewHelper.lblAtkTag);
        wrapper.addView(lblATK);

        TextView lblDivider = new TextView(getApplicationContext());
        lblDivider.setText(" / ");
        lblDivider.setLayoutParams(llParams);
        lblDivider.setTextSize(fontSize);
        lblDivider.setTextColor(getResources().getColor(R.color.textColor));
        wrapper.addView(lblDivider);

        TextView lblDEF = new TextView(getApplicationContext());
        lblDEF.setText(counter.GetDEF() + "");
        lblDEF.setLayoutParams(llParams);
        lblDEF.setTextSize(fontSize);
        lblDEF.setTextColor(getResources().getColor(R.color.textColor));
        lblDEF.setTag(ViewHelper.lblDefTag);
        wrapper.addView(lblDEF);

        wrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.OnEditCounterTap(wrapper.getTag().toString());
            }
        });

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
                counterToAdd = null;
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
    public void LoadMultipleCounterDeletionDialog(final PlayerID playerID) {
        new AlertDialog.Builder(CounterActivity.this)
                .setTitle(getResources().getString(R.string.dialog_countermanager_delete_title))
                .setMessage(getResources().getString(R.string.dialog_countermanager_delete_multiple_message))
                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        presenter.OnPlayerDeletionConfirmation(playerID);
                    }
                })
                .setNegativeButton(getResources().getString(R.string.no), null).show();
    }

    @Override
    public void DeleteAllCounters() {
        DeleteAllCountersForPlayer(player1.GetPlayerID());
        DeleteAllCountersForPlayer(player2.GetPlayerID());
        DeleteAllCountersForPlayer(player3.GetPlayerID());
        DeleteAllCountersForPlayer(player4.GetPlayerID());
    }

    @Override
    public void DeleteAllCountersForPlayer(PlayerID playerID) {
        switch (playerID) {
            case ONE:
                RemoveAllCounterViewsAndHideLayout(player1Layout, R.id.lblCountersPlayer1);
                break;
            case TWO:
                RemoveAllCounterViewsAndHideLayout(player2Layout, R.id.lblCountersPlayer2);
                break;
            case THREE:
                RemoveAllCounterViewsAndHideLayout(player3Layout, R.id.lblCountersPlayer3);
                break;
            case FOUR:
                RemoveAllCounterViewsAndHideLayout(player4Layout, R.id.lblCountersPlayer4);
                break;
            default:
                break;
        }
    }

    private void RemoveAllCounterViewsAndHideLayout(ViewGroup layout, int layoutIdentifier) {
        ArrayList<View> viewsToRemove = new ArrayList<>();

        for(int i = 0; i < layout.getChildCount(); i++) {
            View v = layout.getChildAt(i);
            if(v.getId() != layoutIdentifier) {
                viewsToRemove.add(v);
            }
        }

        for (View v: viewsToRemove) {
            layout.removeView(v);
        }

        viewsToRemove.clear();
        layout.setVisibility(View.GONE);
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
    public void DeleteCounter(LinearLayout counterLayout, boolean deleteParent) {
        if(deleteParent) {
            ((ViewGroup) counterLayout.getParent()).setVisibility(View.GONE);
        }
        ((ViewGroup)counterLayout.getParent()).removeView(counterLayout);
    }

    @Override
    public void LoadCounterEditDialog(ArrayList<Player> players, Counter counter) {
        adapter.clear();
        adapter.addAll(players);

        final String identifier = counter.GetIdentifier();

        final Dialog dialog = new Dialog(CounterActivity.this);
        dialog.setContentView(R.layout.dialog_countermanager_new);

        final EditText txtCardDescription = (EditText) dialog.findViewById(R.id.txtCardDescription);
        txtCardDescription.setText(counter.GetDescription());

        final EditText txtATK = (EditText) dialog.findViewById(R.id.txtCardAtk);
        txtATK.setText(Integer.toString(counter.GetATK()));

        final EditText txtDEF = (EditText) dialog.findViewById(R.id.txtCardDef);
        txtDEF.setText(Integer.toString(counter.GetDEF()));

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
                counterToEdit = null;
                try {
                    counterToEdit = new Counter(txtCardDescription.getText().toString(),
                            Integer.parseInt(txtATK.getText().toString()),
                            Integer.parseInt(txtDEF.getText().toString()));

                } catch (NullPointerException | NumberFormatException ex) {
                    Snackbar.make(findViewById(android.R.id.content), R.string.dialog_countermanager_error_invalid_entry, Snackbar.LENGTH_LONG).show();
                }

                dialog.dismiss();

                if (counterToEdit != null) {
                    presenter.OnCounterEditCompleted(((Player) spPlayers.getSelectedItem()).GetPlayerID(), identifier, counterToEdit);
                }
            }
        });

        dialog.show();
    }

    @Override
    public void UpdateCounterView(Player player, Counter counter) {
        LinearLayout currentPlayerLayout;

        try {
            switch(player.GetPlayerID()) {
                case ONE:
                    currentPlayerLayout = player1Layout;
                    break;
                case TWO:
                    currentPlayerLayout = player2Layout;
                    break;
                case THREE:
                    currentPlayerLayout = player3Layout;
                    break;
                case FOUR:
                    currentPlayerLayout = player4Layout;
                    break;
                default:
                    throw new InvalidObjectException(getString(R.string.exc_invalid_object_player));
            }

            LinearLayout counterView = (LinearLayout) ViewHelper.findFirstViewByTag(currentPlayerLayout, counter.GetIdentifier());
            if(counterView == null) {
                throw new InvalidObjectException(getString(R.string.exc_invalid_object_counter));
            }

            // Update Description
            ((TextView)ViewHelper.findFirstViewByTag(counterView, ViewHelper.lblDescriptionTag)).setText(counter.GetDescription());

            // Update ATK
            ((TextView)ViewHelper.findFirstViewByTag(counterView, ViewHelper.lblAtkTag)).setText(Integer.toString(counter.GetATK()));

            // Update DEF
            ((TextView)ViewHelper.findFirstViewByTag(counterView, ViewHelper.lblDefTag)).setText(Integer.toString(counter.GetDEF()));

        }
        catch(Exception ex) {
            DisplayErrorMessage(getString(R.string.dialog_countermanager_edit_counter_error));
        }
    }

    private void DisplayErrorMessage(String errormessage) {
        Snackbar.make(findViewById(android.R.id.content), errormessage, Snackbar.LENGTH_LONG).show();
    }
}
