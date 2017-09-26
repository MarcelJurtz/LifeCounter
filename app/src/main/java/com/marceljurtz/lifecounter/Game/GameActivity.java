package com.marceljurtz.lifecounter.Game;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marceljurtz.lifecounter.BuildConfig;
import com.marceljurtz.lifecounter.Helper.ClickType;
import com.marceljurtz.lifecounter.Helper.MagicColor;
import com.marceljurtz.lifecounter.Helper.Operator;
import com.marceljurtz.lifecounter.Helper.PlayerID;
import com.marceljurtz.lifecounter.Helper.PreferenceManager;
import com.marceljurtz.lifecounter.R;
import com.marceljurtz.lifecounter.Settings.SettingsActivity;

import org.w3c.dom.Text;

public class GameActivity extends AppCompatActivity /*implements IGameView*/ {

    private IGamePresenter presenter;

    DrawerLayout mainLayout;
    RelativeLayout layoutPlayer1;
    RelativeLayout layoutPlayer2;
    RelativeLayout layoutPlayer3;
    RelativeLayout layoutPlayer4;

    ImageButton cmdPlusPlayer1;
    ImageButton cmdPlusPlayer2;
    ImageButton cmdPlusPlayer3;
    ImageButton cmdPlusPlayer4;

    ImageButton cmdMinusPlayer1;
    ImageButton cmdMinusPlayer2;
    ImageButton cmdMinusPlayer3;
    ImageButton cmdMinusPlayer4;

    ImageButton cmdResetLP;
    ImageButton cmdTogglePoison;

    ImageButton cmdPlusPoisonPlayer1;
    ImageButton cmdPlusPoisonPlayer2;
    ImageButton cmdPlusPoisonPlayer3;
    ImageButton cmdPlusPoisonPlayer4;

    ImageButton cmdMinusPoisonPlayer1;
    ImageButton cmdMinusPoisonPlayer2;
    ImageButton cmdMinusPoisonPlayer3;
    ImageButton cmdMinusPoisonPlayer4;

    ImageButton cmdToggleColorSettings;

    Button cmdBlackPlayer1;
    Button cmdBlackPlayer2;
    Button cmdBlackPlayer3;
    Button cmdBlackPlayer4;

    Button cmdBluePlayer1;
    Button cmdBluePlayer2;
    Button cmdBluePlayer3;
    Button cmdBluePlayer4;

    Button cmdGreenPlayer1;
    Button cmdGreenPlayer2;
    Button cmdGreenPlayer3;
    Button cmdGreenPlayer4;

    Button cmdRedPlayer1;
    Button cmdRedPlayer2;
    Button cmdRedPlayer3;
    Button cmdRedPlayer4;

    Button cmdWhitePlayer1;
    Button cmdWhitePlayer2;
    Button cmdWhitePlayer3;
    Button cmdWhitePlayer4;

    TextView txtLifeCountPlayer1;
    TextView txtLifeCountPlayer2;
    TextView txtLifeCountPlayer3;
    TextView txtLifeCountPlayer4;

    TextView txtPoisonCountPlayer1;
    TextView txtPoisonCountPlayer2;
    TextView txtPoisonCountPlayer3;
    TextView txtPoisonCountPlayer4;

    Button cmdDrawerTogglePowerSaving;

    int LP_Default;
    int PP_Default;

    // amount of points that will be added / substraced on long click
    // default: 5
    int longClickPoints;

    // Default settings
    boolean poisonEnabled = false;
    boolean colorSettingsEnabled = false;
    boolean powerSaveOn = false;

    Player player1;
    Player player2;

    SharedPreferences preferences;

    Toolbar toolbar;
    ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init SharedPreferences
        preferences = getApplicationContext().getSharedPreferences(PreferenceManager.PREFS, Activity.MODE_PRIVATE);

        int playeramount = PreferenceManager.getPlayerAmount(preferences);

        if(playeramount == 4) {
            setContentView(R.layout.activity_main_4player);

            layoutPlayer1 = (RelativeLayout)findViewById(R.id.rl4Player1);
            layoutPlayer2 = (RelativeLayout)findViewById(R.id.rl4Player2);
            layoutPlayer3 = (RelativeLayout)findViewById(R.id.rl4Player3);
            layoutPlayer4 = (RelativeLayout)findViewById(R.id.rl4Player4);

            txtLifeCountPlayer1 = (TextView)findViewById(R.id.txtLifeCount4p1);
            txtLifeCountPlayer2 = (TextView)findViewById(R.id.txtLifeCount4p2);
            txtLifeCountPlayer3 = (TextView)findViewById(R.id.txtLifeCount4p3);
            txtLifeCountPlayer4 = (TextView)findViewById(R.id.txtLifeCount4p4);

            //txtPoisonCountPlayer1 = (TextView)findViewById(R.id.txtPoisonCount4p1);
            //txtPoisonCountPlayer2 = (TextView)findViewById(R.id.txtPoisonCount4p2);
            //txtPoisonCountPlayer3 = (TextView)findViewById(R.id.txtPoisonCount4p3);
            //txtPoisonCountPlayer4 = (TextView)findViewById(R.id.txtPoisonCount4p4);

            //cmdBlackPlayer1 = (ImageButton)findViewById(R.id.cmdBlack4p1);
            //cmdBlackPlayer2 = (ImageButton)findViewById(R.id.cmdBlack4p2);
            //cmdBlackPlayer3 = (ImageButton)findViewById(R.id.cmdBlack4p3);
            //cmdBlackPlayer4 = (ImageButton)findViewById(R.id.cmdBlack4p4);
            //cmdBluePlayer1 = (ImageButton)findViewById(R.id.cmdBlue4p1);
            //cmdBluePlayer2 = (ImageButton)findViewById(R.id.cmdBlue4p2);
            //cmdBluePlayer3 = (ImageButton)findViewById(R.id.cmdBlue4p3);
            //cmdBluePlayer4 = (ImageButton)findViewById(R.id.cmdBlue4p4);
            //cmdGreenPlayer1 = (ImageButton)findViewById(R.id.cmdGreen4p1);
            //cmdGreenPlayer2 = (ImageButton)findViewById(R.id.cmdGreen4p2);
            //cmdGreenPlayer3 = (ImageButton)findViewById(R.id.cmdGreen4p3);
            //cmdGreenPlayer4 = (ImageButton)findViewById(R.id.cmdGreen4p4);
            //cmdRedPlayer1 = (ImageButton)findViewById(R.id.cmdRed4p1);
            //cmdRedPlayer2 = (ImageButton)findViewById(R.id.cmdRed4p2);
            //cmdRedPlayer3 = (ImageButton)findViewById(R.id.cmdRed4p3);
            //cmdRedPlayer4 = (ImageButton)findViewById(R.id.cmdRed4p4);
            //cmdWhitePlayer1 = (ImageButton)findViewById(R.id.cmdWhite4p1);
            //cmdWhitePlayer2 = (ImageButton)findViewById(R.id.cmdWhite4p2);
            //cmdWhitePlayer3 = (ImageButton)findViewById(R.id.cmdWhite4p3);
            //cmdWhitePlayer4 = (ImageButton)findViewById(R.id.cmdWhite4p4);

        } else {
            setContentView(R.layout.activity_main_2player);

            layoutPlayer1 = (RelativeLayout)findViewById(R.id.rl2Player1);
            layoutPlayer2 = (RelativeLayout)findViewById(R.id.rl2Player2);

            txtLifeCountPlayer1 = (TextView)findViewById(R.id.txtLifeCount2p1);
            txtLifeCountPlayer2 = (TextView)findViewById(R.id.txtLifeCount2p2);

            txtPoisonCountPlayer1 = (TextView)findViewById(R.id.txtPoisonCount2p1);
            txtPoisonCountPlayer2 = (TextView)findViewById(R.id.txtPoisonCount2p2);

            cmdBlackPlayer1 = (ImageButton)findViewById(R.id.cmdBlack2p1);
            cmdBlackPlayer2 = (ImageButton)findViewById(R.id.cmdBlack2p2);
            cmdBluePlayer1 = (ImageButton)findViewById(R.id.cmdBlue2p1);
            cmdBluePlayer2 = (ImageButton)findViewById(R.id.cmdBlue2p2);
            cmdGreenPlayer1 = (ImageButton)findViewById(R.id.cmdGreen2p1);
            cmdGreenPlayer2 = (ImageButton)findViewById(R.id.cmdGreen2p2);
            cmdRedPlayer1 = (ImageButton)findViewById(R.id.cmdRed2p1);
            cmdRedPlayer2 = (ImageButton)findViewById(R.id.cmdRed2p2);
            cmdWhitePlayer1 = (ImageButton)findViewById(R.id.cmdWhite2p1);
            cmdWhitePlayer2 = (ImageButton)findViewById(R.id.cmdWhite2p2);

        }


        //region Button Black

        cmdBlackPlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onColorButtonClick(PlayerID.ONE, MagicColor.BLACK, ClickType.SHORT);
            }
        });
        cmdBlackPlayer1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // toggleEnergySaveMode();
                presenter.onColorButtonClick(PlayerID.ONE, MagicColor.BLACK, ClickType.LONG);
                return true;
            }
        });

        cmdBlackPlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onColorButtonClick(PlayerID.TWO, MagicColor.BLACK, ClickType.SHORT);
            }
        });
        cmdBlackPlayer2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // toggleEnergySaveMode();
                presenter.onColorButtonClick(PlayerID.TWO, MagicColor.BLACK, ClickType.LONG);
                return true;
            }
        });

        if(playeramount == 4) {
            cmdBlackPlayer3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onColorButtonClick(PlayerID.THREE, MagicColor.BLACK, ClickType.SHORT);
                }
            });
            cmdBlackPlayer3.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // toggleEnergySaveMode();
                    presenter.onColorButtonClick(PlayerID.THREE, MagicColor.BLACK, ClickType.LONG);
                    return true;
                }
            });

            cmdBlackPlayer4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onColorButtonClick(PlayerID.FOUR, MagicColor.BLACK, ClickType.SHORT);
                }
            });
            cmdBlackPlayer4.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // toggleEnergySaveMode();
                    presenter.onColorButtonClick(PlayerID.FOUR, MagicColor.BLACK, ClickType.LONG);
                    return true;
                }
            });
        }

        //endregion

        //region Button Blue

        cmdBluePlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onColorButtonClick(PlayerID.ONE, MagicColor.BLUE, ClickType.SHORT);
            }
        });

        cmdBluePlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onColorButtonClick(PlayerID.TWO, MagicColor.BLUE, ClickType.SHORT);
            }
        });

        if(playeramount == 4) {
            cmdBluePlayer3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onColorButtonClick(PlayerID.THREE, MagicColor.BLUE, ClickType.SHORT);
                }
            });

            cmdBluePlayer4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onColorButtonClick(PlayerID.FOUR, MagicColor.BLUE, ClickType.SHORT);
                }
            });
        }

        //endregion

        //region Button Green
        cmdGreenPlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onColorButtonClick(PlayerID.ONE, MagicColor.GREEN, ClickType.SHORT);
            }
        });

        cmdGreenPlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onColorButtonClick(PlayerID.TWO, MagicColor.GREEN, ClickType.SHORT);
            }
        });

        if(playeramount == 4) {
            cmdGreenPlayer3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onColorButtonClick(PlayerID.THREE, MagicColor.GREEN, ClickType.SHORT);
                }
            });

            cmdGreenPlayer4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onColorButtonClick(PlayerID.FOUR, MagicColor.GREEN, ClickType.SHORT);
                }
            });
        }
        //endregion

        //region Button Red
        cmdRedPlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onColorButtonClick(PlayerID.ONE, MagicColor.RED, ClickType.SHORT);
            }
        });

        cmdRedPlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onColorButtonClick(PlayerID.TWO, MagicColor.RED, ClickType.SHORT);
            }
        });

        if(playeramount == 4) {
            cmdRedPlayer3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onColorButtonClick(PlayerID.THREE, MagicColor.RED, ClickType.SHORT);
                }
            });

            cmdRedPlayer4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onColorButtonClick(PlayerID.FOUR, MagicColor.RED, ClickType.SHORT);
                }
            });
        }
        //endregion

        //region Button White
        cmdWhitePlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onColorButtonClick(PlayerID.ONE, MagicColor.WHITE, ClickType.SHORT);
            }
        });

        cmdWhitePlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onColorButtonClick(PlayerID.TWO, MagicColor.WHITE, ClickType.SHORT);
            }
        });

        if(playeramount == 4) {
            cmdWhitePlayer3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onColorButtonClick(PlayerID.THREE, MagicColor.WHITE, ClickType.SHORT);
                }
            });

            cmdWhitePlayer4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onColorButtonClick(PlayerID.FOUR, MagicColor.WHITE, ClickType.SHORT);
                }
            });
        }
        //endregion


        mainLayout = (DrawerLayout)findViewById(R.id.mainLayout);




        toolbar = (Toolbar) findViewById(R.id.tbMain);
        setSupportActionBar(toolbar);

        // DEBUG ONLY
        getSupportActionBar().hide();

        //drawerToggle = new ActionBarDrawerToggle(GameActivity.this, mainLayout, R.string.drawer_open, R.string.drawer_close);
        //mainLayout.setDrawerListener(drawerToggle);

        // Disable screen timeout
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);



        // Players
        player1 = new Player(PlayerID.ONE);
        player2 = new Player(PlayerID.TWO);

        // Init GamePresenter
        //presenter = new GamePresenter(this, preferences);

        /*

        // Home - Plus
        cmdPlusPlayer2 = (ImageButton)findViewById(R.id.cmdPlus_p1);
        cmdPlusPlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onLifeUpdate(player_home.getPlayerID(), ClickType.SHORT, Operator.ADD);
            }
        });
        cmdPlusPlayer2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                presenter.onLifeUpdate(player_home.getPlayerID(), ClickType.LONG, Operator.ADD);
                return true;
            }
        });

        // Home - Minus
        cmdMinusPlayer2 = (ImageButton)findViewById(R.id.cmdMinus_p1);
        cmdMinusPlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onLifeUpdate(player_home.getPlayerID(), ClickType.SHORT, Operator.SUBSTRACT);
            }
        });
        cmdMinusPlayer2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                presenter.onLifeUpdate(player_home.getPlayerID(), ClickType.LONG, Operator.SUBSTRACT);
                return true;
            }
        });

        //endregion

        //region Lifepoints Guest

        // Guest - Plus
        cmdPlusPlayer1 = (ImageButton)findViewById(R.id.cmdPlus_p2);
        cmdPlusPlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onLifeUpdate(player_guest.getPlayerID(), ClickType.SHORT, Operator.ADD);
            }
        });
        cmdPlusPlayer1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                presenter.onLifeUpdate(player_guest.getPlayerID(), ClickType.LONG, Operator.ADD);
                return true;
            }
        });

        // Guest - Minus
        cmdMinusPlayer1 = (ImageButton)findViewById(R.id.cmdMinus_p2);
        cmdMinusPlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onLifeUpdate(player_guest.getPlayerID(), ClickType.SHORT, Operator.SUBSTRACT);
            }
        });
        cmdMinusPlayer1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                presenter.onLifeUpdate(player_guest.getPlayerID(), ClickType.LONG, Operator.SUBSTRACT);
                return true;
            }
        });

        //endregion

        //region Reset Button

        // Reset Button
        cmdResetLP = (ImageButton)findViewById(R.id.cmdResetLP);
        cmdResetLP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onResetButtonClick();
            }
        });

        // endregion

        //region Button Toggle Poison

        cmdTogglePoison = (ImageButton)findViewById(R.id.cmdTogglePoison);
        cmdTogglePoison.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onPoisonButtonClick();
            }
        });

        //endregion

        //region Poison Buttons Home

        // Poison Home Plus
        cmdPlusPoisonPlayer1 = (ImageButton)findViewById(R.id.cmdPlusPoison_p1);
        cmdPlusPoisonPlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onPoisonUpdate(player_home.getPlayerID(), ClickType.SHORT, Operator.ADD);
            }
        });
        cmdPlusPoisonPlayer1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                presenter.onPoisonUpdate(player_home.getPlayerID(), ClickType.LONG, Operator.ADD);
                return true;
            }
        });

        // Poison Home Minus
        cmdMinusPoisonPlayer1 = (ImageButton)findViewById(R.id.cmdMinusPoison_p1);
        cmdMinusPoisonPlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onPoisonUpdate(player_home.getPlayerID(), ClickType.SHORT, Operator.SUBSTRACT);
            }
        });
        cmdMinusPoisonPlayer1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                presenter.onPoisonUpdate(player_home.getPlayerID(), ClickType.LONG, Operator.SUBSTRACT);
                return true;
            }
        });

        //endregion

        //region Poison Buttons Guest

        // Poison Guest Plus
        cmdPlusPoisonPlayer2 = (ImageButton)findViewById(R.id.cmdPlusPoison_p2);
        cmdPlusPoisonPlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onPoisonUpdate(player_guest.getPlayerID(), ClickType.SHORT, Operator.ADD);
            }
        });
        cmdPlusPoisonPlayer2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                presenter.onPoisonUpdate(player_guest.getPlayerID(), ClickType.LONG, Operator.ADD);
                return true;
            }
        });

        // Poison Guest Minus
        cmdMinusPoisonPlayer2 = (ImageButton)findViewById(R.id.cmdMinusPoison_p2);
        cmdMinusPoisonPlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onPoisonUpdate(player_guest.getPlayerID(), ClickType.SHORT, Operator.SUBSTRACT);
            }
        });
        cmdMinusPoisonPlayer2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                presenter.onPoisonUpdate(player_guest.getPlayerID(), ClickType.LONG, Operator.SUBSTRACT);
                return true;
            }
        });

        //endregion

        //region Settings Button

        cmdToggleColorSettings = (ImageButton)findViewById(R.id.cmdToggleColors);
        cmdToggleColorSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onSettingsButtonClick(ClickType.SHORT);
            }
        });
        cmdToggleColorSettings.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                presenter.onSettingsButtonClick(ClickType.LONG);
                return true;
            }
        });

        //endregion

        //region Drawer Layout

        cmdDrawerTogglePowerSaving = (Button)findViewById(R.id.cmdTogglePowerSave);
        cmdDrawerTogglePowerSaving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onTogglePowerSaveClick();
            }
        });

        TextView lblVersionInfo = (TextView)findViewById(R.id.lblAppVersion);
        String versionName = BuildConfig.VERSION_NAME;
        lblVersionInfo.setText("Version " + versionName);

        Button cmdSettings = (Button)findViewById(R.id.cmdSettings);
        cmdSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onSettingsButtonClick(ClickType.LONG);
            }
        });

        //endregion

        //endregion
    }

    @Override
    protected void onStart() {
        presenter.onResume();
        super.onStart();
    }

    @Override
    public void initColorButton(MagicColor colorLocation, int color) {
        Button buttonHome;
        Button buttonGuest;
        switch(colorLocation) {
            case BLACK:
                buttonHome = cmdBlackHome;
                buttonGuest = cmdBlackPlayer1;
                break;
            case BLUE:
                buttonHome = cmdBlueHome;
                buttonGuest = cmdBluePlayer1;
                break;
            case GREEN:
                buttonHome = cmdGreenHome;
                buttonGuest = cmdGreenPlayer1;
                break;
            case RED:
                buttonHome = cmdRedHome;
                buttonGuest = cmdRedPlayer1;
                break;
            case WHITE:
                buttonHome = cmdWhiteHome;
                buttonGuest = cmdWhitePlayer1;
                break;
            default:
                buttonHome = null;
                buttonGuest = null;
        }
        if(buttonHome != null && buttonGuest != null) {
            ((GradientDrawable)buttonHome.getBackground()).setColor(color);
            ((GradientDrawable)buttonGuest.getBackground()).setColor(color);
        }
    }

    @Override
    public void loadSettingsActivity() {
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void setLayoutColor(PlayerID playerID, int color) {
        if(playerID.equals(PlayerID.ONE)) {
            layoutPlayer1.setBackgroundColor(color);
        } else if(playerID.equals(PlayerID.TWO)) {
            layoutGuest.setBackgroundColor(color);
        }
    }

    //region Toggle Poison Controls
    @Override
    public void enablePoisonControls() {
        txtPoisonCountGuest.setVisibility(View.VISIBLE);
        txtPoisonCountHome.setVisibility(View.VISIBLE);
        cmdPlusPoisonPlayer2.setVisibility(View.VISIBLE);
        cmdPlusPoisonPlayer1.setVisibility(View.VISIBLE);
        cmdMinusPoisonPlayer2.setVisibility(View.VISIBLE);
        cmdMinusPoisonPlayer1.setVisibility(View.VISIBLE);
    }

    @Override
    public void poisonButtonEnable() {
        cmdTogglePoison.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.icon_poison));
    }

    @Override
    public void disablePoisonControls() {
        txtPoisonCountGuest.setVisibility(View.INVISIBLE);
        txtPoisonCountHome.setVisibility(View.INVISIBLE);
        cmdPlusPoisonPlayer2.setVisibility(View.INVISIBLE);
        cmdPlusPoisonPlayer1.setVisibility(View.INVISIBLE);
        cmdMinusPoisonPlayer2.setVisibility(View.INVISIBLE);
        cmdMinusPoisonPlayer1.setVisibility(View.INVISIBLE);
    }

    @Override
    public void poisonButtonDisable(){
        cmdTogglePoison.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.icon_poison_disabled));
    }
    //endregion

    //region Toggle Settings Controls
    @Override
    public void enableSettingsControls() {
        cmdToggleColorSettings.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.icon_settings));
        cmdBlackHome.setVisibility(View.VISIBLE);
        cmdBlackPlayer1.setVisibility(View.VISIBLE);
        cmdBlueHome.setVisibility(View.VISIBLE);
        cmdBluePlayer1.setVisibility(View.VISIBLE);
        cmdGreenHome.setVisibility(View.VISIBLE);
        cmdGreenPlayer1.setVisibility(View.VISIBLE);
        cmdRedHome.setVisibility(View.VISIBLE);
        cmdRedPlayer1.setVisibility(View.VISIBLE);
        cmdWhiteHome.setVisibility(View.VISIBLE);
        cmdWhitePlayer1.setVisibility(View.VISIBLE);
    }

    @Override
    public void settingsButtonEnable() {
        cmdToggleColorSettings.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.icon_settings));
    }

    @Override
    public void disableSettingsControls() {
        cmdToggleColorSettings.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.icon_settings_disabled));
        cmdBlackHome.setVisibility(View.INVISIBLE);
        cmdBlackPlayer1.setVisibility(View.INVISIBLE);
        cmdBlueHome.setVisibility(View.INVISIBLE);
        cmdBluePlayer1.setVisibility(View.INVISIBLE);
        cmdGreenHome.setVisibility(View.INVISIBLE);
        cmdGreenPlayer1.setVisibility(View.INVISIBLE);
        cmdRedHome.setVisibility(View.INVISIBLE);
        cmdRedPlayer1.setVisibility(View.INVISIBLE);
        cmdWhiteHome.setVisibility(View.INVISIBLE);
        cmdWhitePlayer1.setVisibility(View.INVISIBLE);
    }

    @Override
    public void settingsButtonDisable() {
        cmdToggleColorSettings.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.icon_settings_disabled));
    }


    //endregion

    //region Toggle Energy Saving Option
    @Override
    public void enableEnergySaving(int powerSaveColor, int powerSaveTextColor) {
        layoutPlayer1.setBackgroundColor(powerSaveColor);
        layoutGuest.setBackgroundColor(powerSaveColor);
        txtLifeCountGuest.setTextColor(powerSaveTextColor);
        txtLifeCountHome.setTextColor(powerSaveTextColor);
        txtPoisonCountGuest.setTextColor(powerSaveTextColor);
        txtPoisonCountHome.setTextColor(powerSaveTextColor);
    }

    @Override
    public void disableEnergySaving(int defaultBlack, int regularTextColor) {
        layoutPlayer1.setBackgroundColor(defaultBlack);
        layoutGuest.setBackgroundColor(defaultBlack);
        txtLifeCountGuest.setTextColor(regularTextColor);
        txtLifeCountHome.setTextColor(regularTextColor);
        txtPoisonCountGuest.setTextColor(regularTextColor);
        txtPoisonCountHome.setTextColor(regularTextColor);
    }
    //endregion

    //region Set Life- and Poisonpoints
    @Override
    public void setLifepoints(PlayerID id, String points) {
        if(id.equals(PlayerID.ONE)) {
            txtLifeCountHome.setText(points);
        } else if(id.equals(PlayerID.TWO)) {
            txtLifeCountGuest.setText(points);
        }
    }

    @Override
    public void setPoisonpoints(PlayerID id, String points) {
        if(id.equals(PlayerID.ONE)) {
            txtPoisonCountHome.setText(points);
        } else if(id.equals(PlayerID.TWO)) {
            txtPoisonCountGuest.setText(points);
        }
    }
    //endregion

    //region Overrides for NavigationDrawer

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(new Configuration());
    }

    //endregion


    //region Navigation Drawer

    @Override
    public void setDrawerTextPowerSaving(boolean shouldBeEnabled) {
        String string = "";
        if(shouldBeEnabled) {
            string = getResources().getString(R.string.cmdPowerSaveEnable);
        } else {
            string = getResources().getString(R.string.cmdPowerSaveDisable);
        }

        // Close drawer
        cmdDrawerTogglePowerSaving.setText(string);
        mainLayout.closeDrawer(Gravity.START);
    }

    //endregion
    */
    }
}