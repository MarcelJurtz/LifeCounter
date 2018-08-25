package com.marceljurtz.lifecounter.views.Counter;

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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.marceljurtz.lifecounter.models.Color;
import com.marceljurtz.lifecounter.models.Counter;
import com.marceljurtz.lifecounter.models.Player;
import com.marceljurtz.lifecounter.enums.PlayerIdEnum;
import com.marceljurtz.lifecounter.models.PreferenceManager;
import com.marceljurtz.lifecounter.models.ViewHelper;
import com.marceljurtz.lifecounter.R;

import java.io.InvalidObjectException;
import java.util.ArrayList;

public class CounterActivity extends AppCompatActivity implements ICounterView {

    private CounterPresenter presenter;

    ArrayList<Player> players;

    RelativeLayout rlCounter;
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
        rlCounter = (RelativeLayout)findViewById(R.id.rlCounter);

        preferences = getApplicationContext().getSharedPreferences(PreferenceManager.PREFS, Activity.MODE_PRIVATE);

        player1Layout = (LinearLayout) findViewById(R.id.llCounterPlayer1);
        player2Layout = (LinearLayout) findViewById(R.id.llCounterPlayer2);
        player3Layout = (LinearLayout) findViewById(R.id.llCounterPlayer3);
        player4Layout = (LinearLayout) findViewById(R.id.llCounterPlayer4);

        players = new ArrayList<>();

        player1 = new Player(PlayerIdEnum.ONE);
        player2 = new Player(PlayerIdEnum.TWO);
        player3 = new Player(PlayerIdEnum.THREE);
        player4 = new Player(PlayerIdEnum.FOUR);

        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);

        presenter = new CounterPresenter(this, preferences, players);

        lblCounterHeaderPlayer1 = (TextView) findViewById(R.id.lblCountersPlayer1);
        lblCounterHeaderPlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onPlayerIdentificationTap(player1.getPlayerIdEnum());
            }
        });
        lblCounterHeaderPlayer1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                presenter.onPlayerIdentificationLongTap(player1.getPlayerIdEnum());
                return true;
            }
        });

        lblCounterHeaderPlayer2 = (TextView) findViewById(R.id.lblCountersPlayer2);
        lblCounterHeaderPlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onPlayerIdentificationTap(player2.getPlayerIdEnum());
            }
        });
        lblCounterHeaderPlayer2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                presenter.onPlayerIdentificationLongTap(player2.getPlayerIdEnum());
                return true;
            }
        });

        lblCounterHeaderPlayer3 = (TextView) findViewById(R.id.lblCountersPlayer3);
        lblCounterHeaderPlayer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onPlayerIdentificationTap(player3.getPlayerIdEnum());
            }
        });
        lblCounterHeaderPlayer3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                presenter.onPlayerIdentificationLongTap(player3.getPlayerIdEnum());
                return true;
            }
        });

        lblCounterHeaderPlayer4 = (TextView) findViewById(R.id.lblCountersPlayer4);
        lblCounterHeaderPlayer4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onPlayerIdentificationTap(player4.getPlayerIdEnum());
            }
        });
        lblCounterHeaderPlayer4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                presenter.onPlayerIdentificationLongTap(player4.getPlayerIdEnum());
                return true;
            }
        });

        adapter = new ArrayAdapter<Player>(getApplicationContext(), R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);

        presenter.onCreate();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onFloatingActionButtonTap();
            }
        });

        navigationView = (NavigationView)findViewById(R.id.navigationViewCountermanager);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                switch(id) {
                    case R.id.nav_game_2players:
                        presenter.onMenuEntryTwoPlayerClick();
                        break;
                    case R.id.nav_game_4players:
                        presenter.onMenuEntryFourPlayerClick();
                        break;
                    case R.id.nav_countermanager:
                        presenter.onMenuEntryDicingClick();
                        break;
                    case R.id.nav_settings:
                        presenter.onMenuEntrySettingsClick();
                        break;
                    case R.id.nav_about:
                        presenter.onMenuEntryAboutClick();
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
    public void loadCounterAddDialog(ArrayList<Player> players) {

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

        Button cmdDialogOk = (Button) dialog.findViewById(R.id.dialogButtonOK);
        cmdDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counterToAdd = null;
                try {
                    counterToAdd = new Counter(txtCardDescription.getText().toString(),
                            Integer.parseInt(txtATK.getText().toString()),
                            Integer.parseInt(txtDEF.getText().toString()));

                } catch (NullPointerException | NumberFormatException ex) {
                    displayErrorMessage(getString(R.string.dialog_countermanager_error_invalid_entry));
                }

                dialog.dismiss();

                if (counterToAdd != null) {
                    presenter.addCounter(((Player) spPlayers.getSelectedItem()).getPlayerIdEnum(), counterToAdd);
                }
            }
        });

        // Delete button only for editing entries
        Button cmdDialogDelete = (Button)dialog.findViewById(R.id.dialogButtonDelete);
        cmdDialogDelete.setVisibility(View.INVISIBLE);

        dialog.show();
    }

    @Override
    public void addCounter(PlayerIdEnum playerIdEnum, Counter counter) {
        int paddingLeft = 50;
        int paddingRight = 50;
        int fontSize = 24;

        LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams llParamsSpacer = new LinearLayout.LayoutParams(0, 0, 1f);

        final LinearLayout wrapper = new LinearLayout(getApplicationContext());
        wrapper.setTag(counter.getIdentifier());
        wrapper.setOrientation(LinearLayout.HORIZONTAL);
        wrapper.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        wrapper.setPadding(paddingLeft, 0, paddingRight, 0);

        TextView lblDescription = new TextView(getApplicationContext());
        lblDescription.setText(counter.getDescription());
        lblDescription.setLayoutParams(llParams);
        lblDescription.setTextSize(fontSize);
        lblDescription.setTextColor(getResources().getColor(R.color.textColor));
        lblDescription.setTag(ViewHelper.lblDescriptionTag);
        wrapper.addView(lblDescription);

        View spacer = new View(getApplicationContext());
        spacer.setLayoutParams(llParamsSpacer);
        wrapper.addView(spacer);

        TextView lblATK = new TextView(getApplicationContext());
        lblATK.setText(counter.getATK() + "");
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
        lblDEF.setText(counter.getDEF() + "");
        lblDEF.setLayoutParams(llParams);
        lblDEF.setTextSize(fontSize);
        lblDEF.setTextColor(getResources().getColor(R.color.textColor));
        lblDEF.setTag(ViewHelper.lblDefTag);
        wrapper.addView(lblDEF);

        wrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onCounterTap(wrapper);
            }
        });

        wrapper.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                presenter.onCounterLongTap(wrapper);
                return true;
            }
        });

        switch (playerIdEnum) {
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
    public void loadPlayerIdentificationDialog(final PlayerIdEnum playerIdEnum, final String playername) {
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
                    presenter.onPlayerIdentificationChangeConfirmed(playerIdEnum, newDescription);
                }
            }
        });

        dialog.show();
    }

    @Override
    public void setPlayerIdentificationText(PlayerIdEnum playerIdEnum, String headerText) {
        switch (playerIdEnum) {
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
    public void loadCounterDeletionDialog(final LinearLayout wrapperLayout) {
        new AlertDialog.Builder(CounterActivity.this)
                .setTitle(getResources().getString(R.string.dialog_countermanager_delete_title))
                .setMessage(getResources().getString(R.string.dialog_countermanager_delete_message))
                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        presenter.onCounterDeletionConfirmed(wrapperLayout);
                    }
                })
                .setNegativeButton(getResources().getString(R.string.no), null).show();
    }

    @Override
    public void loadPlayerDeletionDialog(final PlayerIdEnum playerIdEnum) {
        new AlertDialog.Builder(CounterActivity.this)
                .setTitle(getResources().getString(R.string.dialog_countermanager_delete_title))
                .setMessage(getResources().getString(R.string.dialog_countermanager_delete_multiple_message))
                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        presenter.onPlayerDeletionConfirmed(playerIdEnum);
                    }
                })
                .setNegativeButton(getResources().getString(R.string.no), null).show();
    }

    @Override
    public void deleteAllCounters() {
        deleteAllCountersForPlayer(player1.getPlayerIdEnum());
        deleteAllCountersForPlayer(player2.getPlayerIdEnum());
        deleteAllCountersForPlayer(player3.getPlayerIdEnum());
        deleteAllCountersForPlayer(player4.getPlayerIdEnum());
    }

    @Override
    public void deleteAllCountersForPlayer(PlayerIdEnum playerIdEnum) {
        switch (playerIdEnum) {
            case ONE:
                removeAllCounterViewsAndHideLayout(player1Layout, R.id.lblCountersPlayer1);
                break;
            case TWO:
                removeAllCounterViewsAndHideLayout(player2Layout, R.id.lblCountersPlayer2);
                break;
            case THREE:
                removeAllCounterViewsAndHideLayout(player3Layout, R.id.lblCountersPlayer3);
                break;
            case FOUR:
                removeAllCounterViewsAndHideLayout(player4Layout, R.id.lblCountersPlayer4);
                break;
            default:
                break;
        }
    }

    private void removeAllCounterViewsAndHideLayout(ViewGroup layout, int layoutIdentifier) {
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
    public void loadActivity(Class c) {
        Intent intent = new Intent(getApplicationContext(), c);
        finish();
        startActivity(intent);
    }

    @Override
    public void deleteCounter(LinearLayout counterLayout, boolean deleteParent) {
        if(deleteParent) {
            ((ViewGroup) counterLayout.getParent()).setVisibility(View.GONE);
        }
        ((ViewGroup)counterLayout.getParent()).removeView(counterLayout);
    }

    @Override
    public void loadCounterEditDialog(final LinearLayout counterLayout, Player player, final Counter counter) {
        adapter.clear();
        //adapter.addAll(players);
        adapter.add(player);

        final String identifier = counter.getIdentifier();

        final Dialog dialog = new Dialog(CounterActivity.this);
        dialog.setContentView(R.layout.dialog_countermanager_new);

        final EditText txtCardDescription = (EditText) dialog.findViewById(R.id.txtCardDescription);
        txtCardDescription.setText(counter.getDescription());

        final EditText txtATK = (EditText) dialog.findViewById(R.id.txtCardAtk);
        txtATK.setText(Integer.toString(counter.getATK()));

        final EditText txtDEF = (EditText) dialog.findViewById(R.id.txtCardDef);
        txtDEF.setText(Integer.toString(counter.getDEF()));

        final Spinner spPlayers = (Spinner) dialog.findViewById(R.id.spUserSelection);
        spPlayers.setAdapter(adapter);
        spPlayers.setEnabled(false);

        final ImageButton cmdIncrease = (ImageButton)dialog.findViewById(R.id.cmdIncreaseCounter);
        final ImageButton cmdDecrease = (ImageButton)dialog.findViewById(R.id.cmdDecreaseCounter);

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
                    presenter.onCounterEditCompleted(((Player) spPlayers.getSelectedItem()).getPlayerIdEnum(), identifier, counterToEdit);
                }
            }
        });

        Button cmdDialogDelete = (Button) dialog.findViewById(R.id.dialogButtonDelete);
        cmdDialogDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                presenter.onCounterLongTap(counterLayout);
            }
        });

        dialog.show();
    }

    @Override
    public void updateCounterView(Player player, Counter counter) {
        LinearLayout currentPlayerLayout;

        try {
            switch(player.getPlayerIdEnum()) {
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

            LinearLayout counterView = (LinearLayout) ViewHelper.findFirstViewByTag(currentPlayerLayout, counter.getIdentifier());
            if(counterView == null) {
                throw new InvalidObjectException(getString(R.string.exc_invalid_object_counter));
            }

            // Update Description
            ((TextView)ViewHelper.findFirstViewByTag(counterView, ViewHelper.lblDescriptionTag)).setText(counter.getDescription());

            // Update ATK
            ((TextView)ViewHelper.findFirstViewByTag(counterView, ViewHelper.lblAtkTag)).setText(Integer.toString(counter.getATK()));

            // Update DEF
            ((TextView)ViewHelper.findFirstViewByTag(counterView, ViewHelper.lblDefTag)).setText(Integer.toString(counter.getDEF()));

        }
        catch(Exception ex) {
            displayErrorMessage(getString(R.string.dialog_countermanager_edit_counter_error));
        }
    }

    private void displayErrorMessage(String errormessage) {
        Snackbar.make(findViewById(android.R.id.content), errormessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void setBackgroundColor(Color color) {
        rlCounter.setBackgroundColor(color.getIntValue());
    }
}
