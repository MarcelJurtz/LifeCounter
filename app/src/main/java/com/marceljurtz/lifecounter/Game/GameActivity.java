package com.marceljurtz.lifecounter.Game;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marceljurtz.lifecounter.About.AboutActivity;
import com.marceljurtz.lifecounter.Counter.CounterActivity;
import com.marceljurtz.lifecounter.Dicing.DicingActivity;
import com.marceljurtz.lifecounter.Helper.ClickType;
import com.marceljurtz.lifecounter.Helper.MagicColor;
import com.marceljurtz.lifecounter.Helper.Operator;
import com.marceljurtz.lifecounter.Helper.Player;
import com.marceljurtz.lifecounter.Helper.PlayerID;
import com.marceljurtz.lifecounter.Helper.PreferenceManager;
import com.marceljurtz.lifecounter.R;
import com.marceljurtz.lifecounter.Settings.SettingsActivity;

public class GameActivity extends AppCompatActivity implements IGameView {

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
    TextView lblVersionInfo;
    Button cmdSettings;

    NavigationView  navigationView;

    int playeramount;

    Player player1;
    Player player2;
    Player player3;
    Player player4;

    SharedPreferences preferences;

    Toolbar toolbar;
    ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init SharedPreferences
        preferences = getApplicationContext().getSharedPreferences(PreferenceManager.PREFS, Activity.MODE_PRIVATE);
        playeramount = PreferenceManager.getPlayerAmount(preferences);

        if(playeramount == 4) {
            setContentView(R.layout.activity_main_4player);
        } else {
            setContentView(R.layout.activity_main_2player);
        }

        mainLayout = (DrawerLayout)findViewById(R.id.mainLayout);


        toolbar = (Toolbar) findViewById(R.id.tbMain);
        setSupportActionBar(toolbar);

        // DEBUG ONLY
        //getSupportActionBar().hide();

        //drawerToggle = new ActionBarDrawerToggle(GameActivity.this, mainLayout, R.string.drawer_open, R.string.drawer_close);
        //mainLayout.setDrawerListener(drawerToggle);


        // Players
        player1 = new Player(PlayerID.ONE);
        player2 = new Player(PlayerID.TWO);
        if(playeramount == 4) {
            player3 = new Player(PlayerID.THREE);
            player4 = new Player(PlayerID.FOUR);
        }

        //playeramount = 4;

        if (playeramount == 4) {
            layoutPlayer1 = (RelativeLayout) findViewById(R.id.rl4Player1);
            layoutPlayer2 = (RelativeLayout) findViewById(R.id.rl4Player2);
            layoutPlayer3 = (RelativeLayout) findViewById(R.id.rl4Player3);
            layoutPlayer4 = (RelativeLayout) findViewById(R.id.rl4Player4);

            txtLifeCountPlayer1 = (TextView) findViewById(R.id.txtLifeCount4p1);
            txtLifeCountPlayer2 = (TextView) findViewById(R.id.txtLifeCount4p2);
            txtLifeCountPlayer3 = (TextView) findViewById(R.id.txtLifeCount4p3);
            txtLifeCountPlayer4 = (TextView) findViewById(R.id.txtLifeCount4p4);

            cmdPlusPlayer1 = (ImageButton)findViewById(R.id.cmdPlus4p1);
            cmdPlusPlayer2 = (ImageButton)findViewById(R.id.cmdPlus4p2);
            cmdPlusPlayer3 = (ImageButton)findViewById(R.id.cmdPlus4p3);
            cmdPlusPlayer4 = (ImageButton)findViewById(R.id.cmdPlus4p4);

            cmdMinusPlayer1 = (ImageButton)findViewById(R.id.cmdMinus4p1);
            cmdMinusPlayer2 = (ImageButton)findViewById(R.id.cmdMinus4p2);
            cmdMinusPlayer3 = (ImageButton)findViewById(R.id.cmdMinus4p3);
            cmdMinusPlayer4 = (ImageButton)findViewById(R.id.cmdMinus4p4);

            txtPoisonCountPlayer1 = (TextView)findViewById(R.id.txtPoisonCount4p1);
            txtPoisonCountPlayer2 = (TextView)findViewById(R.id.txtPoisonCount4p2);
            txtPoisonCountPlayer3 = (TextView)findViewById(R.id.txtPoisonCount4p3);
            txtPoisonCountPlayer4 = (TextView)findViewById(R.id.txtPoisonCount4p4);

            cmdPlusPoisonPlayer1 = (ImageButton)findViewById(R.id.cmdPlusPoison4p1);
            cmdPlusPoisonPlayer2 = (ImageButton)findViewById(R.id.cmdPlusPoison4p2);
            cmdPlusPoisonPlayer3 = (ImageButton)findViewById(R.id.cmdPlusPoison4p3);
            cmdPlusPoisonPlayer4 = (ImageButton)findViewById(R.id.cmdPlusPoison4p4);

            cmdMinusPoisonPlayer1 = (ImageButton)findViewById(R.id.cmdMinusPoison4p1);
            cmdMinusPoisonPlayer2 = (ImageButton)findViewById(R.id.cmdMinusPoison4p2);
            cmdMinusPoisonPlayer3 = (ImageButton)findViewById(R.id.cmdMinusPoison4p3);
            cmdMinusPoisonPlayer4 = (ImageButton)findViewById(R.id.cmdMinusPoison4p4);

            cmdBlackPlayer1 = (Button)findViewById(R.id.cmdBlack4p1);
            cmdBlackPlayer2 = (Button)findViewById(R.id.cmdBlack4p2);
            cmdBlackPlayer3 = (Button)findViewById(R.id.cmdBlack4p3);
            cmdBlackPlayer4 = (Button)findViewById(R.id.cmdBlack4p4);
            cmdBluePlayer1 = (Button)findViewById(R.id.cmdBlue4p1);
            cmdBluePlayer2 = (Button)findViewById(R.id.cmdBlue4p2);
            cmdBluePlayer3 = (Button)findViewById(R.id.cmdBlue4p3);
            cmdBluePlayer4 = (Button)findViewById(R.id.cmdBlue4p4);
            cmdGreenPlayer1 = (Button)findViewById(R.id.cmdGreen4p1);
            cmdGreenPlayer2 = (Button)findViewById(R.id.cmdGreen4p2);
            cmdGreenPlayer3 = (Button)findViewById(R.id.cmdGreen4p3);
            cmdGreenPlayer4 = (Button)findViewById(R.id.cmdGreen4p4);
            cmdRedPlayer1 = (Button)findViewById(R.id.cmdRed4p1);
            cmdRedPlayer2 = (Button)findViewById(R.id.cmdRed4p2);
            cmdRedPlayer3 = (Button)findViewById(R.id.cmdRed4p3);
            cmdRedPlayer4 = (Button)findViewById(R.id.cmdRed4p4);
            cmdWhitePlayer1 = (Button)findViewById(R.id.cmdWhite4p1);
            cmdWhitePlayer2 = (Button)findViewById(R.id.cmdWhite4p2);
            cmdWhitePlayer3 = (Button)findViewById(R.id.cmdWhite4p3);
            cmdWhitePlayer4 = (Button)findViewById(R.id.cmdWhite4p4);

            cmdToggleColorSettings = (ImageButton)findViewById(R.id.cmdToggleColors4p);
            cmdTogglePoison = (ImageButton)findViewById(R.id.cmdTogglePoison4p);
            cmdResetLP = (ImageButton)findViewById(R.id.cmdResetLP4p);

            /*
            cmdDrawerTogglePowerSaving = (Button)findViewById(R.id.nav_energy_save_mode);
            cmdSettings = (Button)findViewById(R.id.nav_settings);
            lblVersionInfo = (TextView)findViewById(R.id.lblVersionInfo);
            */
            navigationView = (NavigationView) findViewById(R.id.navigationView4p);
            navigationView.getMenu().findItem(R.id.nav_useramount).setTitle(R.string.menu_entry_load_players_2);

        } else {
            layoutPlayer1 = (RelativeLayout) findViewById(R.id.rl2Player1);
            layoutPlayer2 = (RelativeLayout) findViewById(R.id.rl2Player2);

            txtLifeCountPlayer1 = (TextView) findViewById(R.id.txtLifeCount2p1);
            txtLifeCountPlayer2 = (TextView) findViewById(R.id.txtLifeCount2p2);

            txtPoisonCountPlayer1 = (TextView) findViewById(R.id.txtPoisonCount2p1);
            txtPoisonCountPlayer2 = (TextView) findViewById(R.id.txtPoisonCount2p2);

            cmdBlackPlayer1 = (Button)findViewById(R.id.cmdBlack2p1);
            cmdBlackPlayer2 = (Button)findViewById(R.id.cmdBlack2p2);
            cmdBluePlayer1 = (Button)findViewById(R.id.cmdBlue2p1);
            cmdBluePlayer2 = (Button)findViewById(R.id.cmdBlue2p2);
            cmdGreenPlayer1 = (Button)findViewById(R.id.cmdGreen2p1);
            cmdGreenPlayer2 = (Button)findViewById(R.id.cmdGreen2p2);
            cmdRedPlayer1 = (Button)findViewById(R.id.cmdRed2p1);
            cmdRedPlayer2 = (Button)findViewById(R.id.cmdRed2p2);
            cmdWhitePlayer1 = (Button)findViewById(R.id.cmdWhite2p1);
            cmdWhitePlayer2 = (Button)findViewById(R.id.cmdWhite2p2);

            cmdPlusPlayer1 = (ImageButton)findViewById(R.id.cmdPlusGuest);
            cmdPlusPlayer2 = (ImageButton)findViewById(R.id.cmdPlusHome);
            cmdMinusPlayer1 = (ImageButton)findViewById(R.id.cmdMinusGuest);
            cmdMinusPlayer2 = (ImageButton)findViewById(R.id.cmdMinusHome);

            cmdPlusPoisonPlayer1 = (ImageButton)findViewById(R.id.cmdPlusPoisonGuest);
            cmdPlusPoisonPlayer2 = (ImageButton)findViewById(R.id.cmdPlusPoisonHome);
            cmdMinusPoisonPlayer1 = (ImageButton)findViewById(R.id.cmdMinusPoisonGuest);
            cmdMinusPoisonPlayer2 = (ImageButton)findViewById(R.id.cmdMinusPoisonHome);

            cmdToggleColorSettings = (ImageButton)findViewById(R.id.cmdToggleColors);
            cmdTogglePoison = (ImageButton)findViewById(R.id.cmdTogglePoison);
            cmdResetLP = (ImageButton)findViewById(R.id.cmdResetLP);

            /*
            cmdDrawerTogglePowerSaving = (Button)findViewById(R.id.cmdTogglePowerSave2p);
            cmdSettings = (Button)findViewById(R.id.cmdSettings2p);
            lblVersionInfo = (TextView)findViewById(R.id.lblAppVersion2p);
            */

            navigationView = (NavigationView) findViewById(R.id.navigationView2p);
            navigationView.getMenu().findItem(R.id.nav_useramount).setTitle(R.string.menu_entry_load_players_4);
        }

        // Init GamePresenter
        presenter = new GamePresenter(this, preferences);

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


        cmdPlusPlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onLifeUpdate(player1.getPlayerID(), ClickType.SHORT, Operator.ADD);
            }
        });
        cmdPlusPlayer1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                presenter.onLifeUpdate(player1.getPlayerID(), ClickType.LONG, Operator.ADD);
                return true;
            }
        });

        cmdMinusPlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onLifeUpdate(player1.getPlayerID(), ClickType.SHORT, Operator.SUBSTRACT);
            }
        });
        cmdMinusPlayer1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                presenter.onLifeUpdate(player1.getPlayerID(), ClickType.LONG, Operator.SUBSTRACT);
                return true;
            }
        });

        //endregion

        //region Lifepoints Guest

        cmdPlusPlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onLifeUpdate(player2.getPlayerID(), ClickType.SHORT, Operator.ADD);
            }
        });
        cmdPlusPlayer2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                presenter.onLifeUpdate(player2.getPlayerID(), ClickType.LONG, Operator.ADD);
                return true;
            }
        });

        cmdMinusPlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onLifeUpdate(player2.getPlayerID(), ClickType.SHORT, Operator.SUBSTRACT);
            }
        });
        cmdMinusPlayer2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                presenter.onLifeUpdate(player2.getPlayerID(), ClickType.LONG, Operator.SUBSTRACT);
                return true;
            }
        });

        if(playeramount == 4) {
            cmdPlusPlayer3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onLifeUpdate(player3.getPlayerID(), ClickType.SHORT, Operator.ADD);
                }
            });
            cmdPlusPlayer3.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    presenter.onLifeUpdate(player3.getPlayerID(), ClickType.LONG, Operator.ADD);
                    return true;
                }
            });

            cmdMinusPlayer3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onLifeUpdate(player3.getPlayerID(), ClickType.SHORT, Operator.SUBSTRACT);
                }
            });
            cmdMinusPlayer3.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    presenter.onLifeUpdate(player3.getPlayerID(), ClickType.LONG, Operator.SUBSTRACT);
                    return true;
                }
            });

            cmdPlusPlayer4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onLifeUpdate(player4.getPlayerID(), ClickType.SHORT, Operator.ADD);
                }
            });
            cmdPlusPlayer4.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    presenter.onLifeUpdate(player4.getPlayerID(), ClickType.LONG, Operator.ADD);
                    return true;
                }
            });

            cmdMinusPlayer4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onLifeUpdate(player4.getPlayerID(), ClickType.SHORT, Operator.SUBSTRACT);
                }
            });
            cmdMinusPlayer4.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    presenter.onLifeUpdate(player4.getPlayerID(), ClickType.LONG, Operator.SUBSTRACT);
                    return true;
                }
            });
        }

        //endregion

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                switch(id) {
                    case R.id.nav_settings:
                        //presenter.onSettingsButtonClick(ClickType.LONG);
                        presenter.onMenuEntrySettingsTap();
                        break;
                    case R.id.nav_about:
                        presenter.onMenuEntryAboutTap();
                        break;
                    case R.id.nav_energy_save_mode:
                        presenter.onMenuEntryEnergySaveTap();
                        break;
                    case R.id.nav_useramount:
                        presenter.onMenuEntryTogglePlayerTap();
                        break;
                    case R.id.nav_dicing:
                        presenter.onMenuEntryDicingTap();
                        break;
                    case R.id.nav_countermanager:
                        presenter.onMenuEntryCounterManagerTap();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });



        //region Reset Button

        // Reset Button
        cmdResetLP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onResetButtonClick();
            }
        });

        // endregion

        //region Button Toggle Poison

        cmdTogglePoison.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onPoisonButtonClick();
            }
        });

        //endregion

        //region Poison Buttons Home

        // Poison Home Plus
        cmdPlusPoisonPlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onPoisonUpdate(player1.getPlayerID(), ClickType.SHORT, Operator.ADD);
            }
        });
        cmdPlusPoisonPlayer1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                presenter.onPoisonUpdate(player1.getPlayerID(), ClickType.LONG, Operator.ADD);
                return true;
            }
        });

        // Poison Home Minus
        cmdMinusPoisonPlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onPoisonUpdate(player1.getPlayerID(), ClickType.SHORT, Operator.SUBSTRACT);
            }
        });
        cmdMinusPoisonPlayer1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                presenter.onPoisonUpdate(player1.getPlayerID(), ClickType.LONG, Operator.SUBSTRACT);
                return true;
            }
        });

        //endregion

        //region Poison Buttons Guest

        // Poison Guest Plus
        cmdPlusPoisonPlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onPoisonUpdate(player2.getPlayerID(), ClickType.SHORT, Operator.ADD);
            }
        });
        cmdPlusPoisonPlayer2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                presenter.onPoisonUpdate(player2.getPlayerID(), ClickType.LONG, Operator.ADD);
                return true;
            }
        });

        // Poison Guest Minus
        cmdMinusPoisonPlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onPoisonUpdate(player2.getPlayerID(), ClickType.SHORT, Operator.SUBSTRACT);
            }
        });
        cmdMinusPoisonPlayer2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                presenter.onPoisonUpdate(player2.getPlayerID(), ClickType.LONG, Operator.SUBSTRACT);
                return true;
            }
        });

        //endregion

        if(playeramount == 4) {
            cmdPlusPoisonPlayer3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onPoisonUpdate(player3.getPlayerID(), ClickType.SHORT, Operator.ADD);
                }
            });
            cmdPlusPoisonPlayer3.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    presenter.onPoisonUpdate(player3.getPlayerID(), ClickType.LONG, Operator.ADD);
                    return true;
                }
            });

            cmdMinusPoisonPlayer3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onPoisonUpdate(player3.getPlayerID(), ClickType.SHORT, Operator.SUBSTRACT);
                }
            });
            cmdMinusPoisonPlayer3.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    presenter.onPoisonUpdate(player3.getPlayerID(), ClickType.LONG, Operator.SUBSTRACT);
                    return true;
                }
            });


            cmdPlusPoisonPlayer4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onPoisonUpdate(player4.getPlayerID(), ClickType.SHORT, Operator.ADD);
                }
            });
            cmdPlusPoisonPlayer4.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    presenter.onPoisonUpdate(player4.getPlayerID(), ClickType.LONG, Operator.ADD);
                    return true;
                }
            });

            cmdMinusPoisonPlayer4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onPoisonUpdate(player4.getPlayerID(), ClickType.SHORT, Operator.SUBSTRACT);
                }
            });
            cmdMinusPoisonPlayer4.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    presenter.onPoisonUpdate(player4.getPlayerID(), ClickType.LONG, Operator.SUBSTRACT);
                    return true;
                }
            });
        }

        //region Settings Button
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

        /* region Drawer Layout

        cmdDrawerTogglePowerSaving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onTogglePowerSaveClick();
            }
        });



        String versionName = BuildConfig.VERSION_NAME;
        lblVersionInfo.setText("Version " + versionName);



        cmdSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onSettingsButtonClick(ClickType.LONG);
            }
        });

        //endregion

        //endregion
        */
    }

    @Override
    protected void onStart() {
        presenter.onResume();
        super.onStart();
    }

    @Override
    public void restartActivity() {
        mainLayout.closeDrawer(Gravity.START);
        recreate();
    }

    @Override
    public void initColorButton(MagicColor colorLocation, int color) {

        Button buttonPlayer1;
        Button buttonPlayer2;
        Button buttonPlayer3;
        Button buttonPlayer4;

        switch(colorLocation) {
            case BLACK:
                buttonPlayer1 = cmdBlackPlayer1;
                buttonPlayer2 = cmdBlackPlayer2;
                buttonPlayer3 = cmdBlackPlayer3;
                buttonPlayer4 = cmdBlackPlayer4;
                break;
            case BLUE:
                buttonPlayer1 = cmdBluePlayer1;
                buttonPlayer2 = cmdBluePlayer2;
                buttonPlayer3 = cmdBluePlayer3;
                buttonPlayer4 = cmdBluePlayer4;
                break;
            case GREEN:
                buttonPlayer1 = cmdGreenPlayer1;
                buttonPlayer2 = cmdGreenPlayer2;
                buttonPlayer3 = cmdGreenPlayer3;
                buttonPlayer4 = cmdGreenPlayer4;
                break;
            case RED:
                buttonPlayer1 = cmdRedPlayer1;
                buttonPlayer2 = cmdRedPlayer2;
                buttonPlayer3 = cmdRedPlayer3;
                buttonPlayer4 = cmdRedPlayer4;
                break;
            case WHITE:
                buttonPlayer1 = cmdWhitePlayer1;
                buttonPlayer2 = cmdWhitePlayer2;
                buttonPlayer3 = cmdWhitePlayer3;
                buttonPlayer4 = cmdWhitePlayer4;
                break;
            default:
                buttonPlayer1 = null;
                buttonPlayer2 = null;
                buttonPlayer3 = null;
                buttonPlayer4 = null;
        }
        if(buttonPlayer1 != null && buttonPlayer2 != null) {
            ((GradientDrawable)buttonPlayer1.getBackground()).setColor(color);
            ((GradientDrawable)buttonPlayer2.getBackground()).setColor(color);
            if(playeramount == 4) {
                ((GradientDrawable)buttonPlayer3.getBackground()).setColor(color);
                ((GradientDrawable)buttonPlayer4.getBackground()).setColor(color);
            }
        }
    }



    @Override
    public void loadSettingsActivity() {
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void loadDicingActivity() {
        Intent intent = new Intent(getApplicationContext(), DicingActivity.class);
        startActivity(intent);
    }

    @Override
    public void loadAboutActivity() {
        Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
        startActivity(intent);
    }

    @Override
    public void loadCounterManagerActivity() {
        Intent intent = new Intent(getApplicationContext(), CounterActivity.class);
        startActivity(intent);
    }

    @Override
    public void setLayoutColor(PlayerID playerID, int color) {
        if(playerID.equals(PlayerID.ONE)) {
            layoutPlayer1.setBackgroundColor(color);
        } else if(playerID.equals(PlayerID.TWO)) {
            layoutPlayer2.setBackgroundColor(color);
        } else if(playerID.equals(PlayerID.THREE)) {
            layoutPlayer3.setBackgroundColor(color);
        } else if(playerID.equals(PlayerID.FOUR)) {
            layoutPlayer4.setBackgroundColor(color);
        }
    }

    //region Toggle Poison Controls
    @Override
    public void enablePoisonControls(boolean rearrangeLifepoints) {
        txtPoisonCountPlayer1.setVisibility(View.VISIBLE);
        txtPoisonCountPlayer2.setVisibility(View.VISIBLE);

        cmdPlusPoisonPlayer1.setVisibility(View.VISIBLE);
        cmdPlusPoisonPlayer2.setVisibility(View.VISIBLE);

        cmdMinusPoisonPlayer1.setVisibility(View.VISIBLE);
        cmdMinusPoisonPlayer2.setVisibility(View.VISIBLE);

        if(playeramount == 4) {
            txtPoisonCountPlayer3.setVisibility(View.VISIBLE);
            txtPoisonCountPlayer4.setVisibility(View.VISIBLE);

            cmdPlusPoisonPlayer3.setVisibility(View.VISIBLE);
            cmdPlusPoisonPlayer4.setVisibility(View.VISIBLE);

            cmdMinusPoisonPlayer3.setVisibility(View.VISIBLE);
            cmdMinusPoisonPlayer4.setVisibility(View.VISIBLE);
        }

        if(rearrangeLifepoints) {
            RelativeLayout.LayoutParams paramsLeft = (RelativeLayout.LayoutParams)txtLifeCountPlayer1.getLayoutParams();
            paramsLeft.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            paramsLeft.addRule(RelativeLayout.ALIGN_PARENT_END);

            RelativeLayout.LayoutParams paramsRight = (RelativeLayout.LayoutParams)txtLifeCountPlayer2.getLayoutParams();
            paramsRight.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            paramsRight.addRule(RelativeLayout.ALIGN_PARENT_START);

            txtLifeCountPlayer1.setLayoutParams(paramsLeft);
            txtLifeCountPlayer2.setLayoutParams(paramsRight);

            if(playeramount == 4) {
                txtLifeCountPlayer3.setLayoutParams(paramsLeft);
                txtLifeCountPlayer4.setLayoutParams(paramsRight);
            }
        }
    }

    @Override
    public void poisonButtonEnable() {
        cmdTogglePoison.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.icon_poison));
    }

    @Override
    public void disablePoisonControls(boolean rearrangeLifepoints) {
        txtPoisonCountPlayer1.setVisibility(View.INVISIBLE);
        txtPoisonCountPlayer2.setVisibility(View.INVISIBLE);

        cmdPlusPoisonPlayer1.setVisibility(View.INVISIBLE);
        cmdPlusPoisonPlayer2.setVisibility(View.INVISIBLE);

        cmdMinusPoisonPlayer1.setVisibility(View.INVISIBLE);
        cmdMinusPoisonPlayer2.setVisibility(View.INVISIBLE);

        if(playeramount == 4) {
            txtPoisonCountPlayer3.setVisibility(View.INVISIBLE);
            txtPoisonCountPlayer4.setVisibility(View.INVISIBLE);

            cmdPlusPoisonPlayer3.setVisibility(View.INVISIBLE);
            cmdPlusPoisonPlayer4.setVisibility(View.INVISIBLE);

            cmdMinusPoisonPlayer3.setVisibility(View.INVISIBLE);
            cmdMinusPoisonPlayer4.setVisibility(View.INVISIBLE);
        }

        if(rearrangeLifepoints) {
            RelativeLayout.LayoutParams paramsLeft = (RelativeLayout.LayoutParams)txtLifeCountPlayer1.getLayoutParams();
            paramsLeft.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
            paramsLeft.addRule(RelativeLayout.ALIGN_PARENT_END, 0);

            RelativeLayout.LayoutParams paramsRight = (RelativeLayout.LayoutParams)txtLifeCountPlayer2.getLayoutParams();
            paramsRight.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
            paramsRight.addRule(RelativeLayout.ALIGN_PARENT_START, 0);

            txtLifeCountPlayer1.setLayoutParams(paramsLeft);
            txtLifeCountPlayer2.setLayoutParams(paramsRight);

            if(playeramount == 4) {
                txtLifeCountPlayer3.setLayoutParams(paramsLeft);
                txtLifeCountPlayer4.setLayoutParams(paramsRight);
            }
        }
    }

    @Override
    public void poisonButtonDisable(){
        cmdTogglePoison.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.icon_poison_disabled));
    }
    //endregion

    //region Toggle Settings Controls
    @Override
    public void enableSettingsControls(boolean hideLifecountControls, boolean hidePoisonControls) {
        cmdBlackPlayer1.setVisibility(View.VISIBLE);
        cmdBlackPlayer2.setVisibility(View.VISIBLE);

        cmdBluePlayer1.setVisibility(View.VISIBLE);
        cmdBluePlayer2.setVisibility(View.VISIBLE);

        cmdGreenPlayer1.setVisibility(View.VISIBLE);
        cmdGreenPlayer2.setVisibility(View.VISIBLE);

        cmdRedPlayer1.setVisibility(View.VISIBLE);
        cmdRedPlayer2.setVisibility(View.VISIBLE);

        cmdWhitePlayer1.setVisibility(View.VISIBLE);
        cmdWhitePlayer2.setVisibility(View.VISIBLE);

        if(playeramount == 4) {
            cmdBlackPlayer3.setVisibility(View.VISIBLE);
            cmdBlackPlayer4.setVisibility(View.VISIBLE);

            cmdBluePlayer3.setVisibility(View.VISIBLE);
            cmdBluePlayer4.setVisibility(View.VISIBLE);

            cmdGreenPlayer3.setVisibility(View.VISIBLE);
            cmdGreenPlayer4.setVisibility(View.VISIBLE);

            cmdRedPlayer3.setVisibility(View.VISIBLE);
            cmdRedPlayer4.setVisibility(View.VISIBLE);

            cmdWhitePlayer3.setVisibility(View.VISIBLE);
            cmdWhitePlayer4.setVisibility(View.VISIBLE);
        }

        if(hideLifecountControls) {
            txtLifeCountPlayer1.setVisibility(View.INVISIBLE);
            txtLifeCountPlayer2.setVisibility(View.INVISIBLE);

            cmdPlusPlayer1.setVisibility(View.INVISIBLE);
            cmdPlusPlayer2.setVisibility(View.INVISIBLE);

            cmdMinusPlayer1.setVisibility(View.INVISIBLE);
            cmdMinusPlayer2.setVisibility(View.INVISIBLE);

            if(playeramount == 4) {
                txtLifeCountPlayer3.setVisibility(View.INVISIBLE);
                txtLifeCountPlayer4.setVisibility(View.INVISIBLE);

                cmdPlusPlayer3.setVisibility(View.INVISIBLE);
                cmdPlusPlayer4.setVisibility(View.INVISIBLE);

                cmdMinusPlayer3.setVisibility(View.INVISIBLE);
                cmdMinusPlayer4.setVisibility(View.INVISIBLE);
            }
        }

        if(hidePoisonControls) {

            cmdPlusPoisonPlayer1.setVisibility(View.INVISIBLE);
            cmdMinusPoisonPlayer1.setVisibility(View.INVISIBLE);
            txtPoisonCountPlayer1.setVisibility(View.INVISIBLE);

            cmdPlusPoisonPlayer2.setVisibility(View.INVISIBLE);
            cmdMinusPoisonPlayer2.setVisibility(View.INVISIBLE);
            txtPoisonCountPlayer2.setVisibility(View.INVISIBLE);

            if(playeramount == 4) {
                cmdPlusPoisonPlayer3.setVisibility(View.INVISIBLE);
                cmdMinusPoisonPlayer3.setVisibility(View.INVISIBLE);
                txtPoisonCountPlayer3.setVisibility(View.INVISIBLE);

                cmdPlusPoisonPlayer4.setVisibility(View.INVISIBLE);
                cmdMinusPoisonPlayer4.setVisibility(View.INVISIBLE);
                txtPoisonCountPlayer4.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void settingsButtonEnable() {
        cmdToggleColorSettings.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.icon_settings));
    }

    @Override
    public void disableSettingsControls(boolean showLifecountControls, boolean showPoisonControls) {
        cmdBlackPlayer1.setVisibility(View.INVISIBLE);
        cmdBlackPlayer2.setVisibility(View.INVISIBLE);

        cmdBluePlayer1.setVisibility(View.INVISIBLE);
        cmdBluePlayer2.setVisibility(View.INVISIBLE);

        cmdGreenPlayer1.setVisibility(View.INVISIBLE);
        cmdGreenPlayer2.setVisibility(View.INVISIBLE);

        cmdRedPlayer1.setVisibility(View.INVISIBLE);
        cmdRedPlayer2.setVisibility(View.INVISIBLE);

        cmdWhitePlayer1.setVisibility(View.INVISIBLE);
        cmdWhitePlayer2.setVisibility(View.INVISIBLE);

        if(playeramount == 4) {
            cmdBlackPlayer3.setVisibility(View.INVISIBLE);
            cmdBlackPlayer4.setVisibility(View.INVISIBLE);

            cmdBluePlayer3.setVisibility(View.INVISIBLE);
            cmdBluePlayer4.setVisibility(View.INVISIBLE);

            cmdGreenPlayer3.setVisibility(View.INVISIBLE);
            cmdGreenPlayer4.setVisibility(View.INVISIBLE);

            cmdRedPlayer3.setVisibility(View.INVISIBLE);
            cmdRedPlayer4.setVisibility(View.INVISIBLE);

            cmdWhitePlayer3.setVisibility(View.INVISIBLE);
            cmdWhitePlayer4.setVisibility(View.INVISIBLE);
        }

        if(showLifecountControls) {
            txtLifeCountPlayer1.setVisibility(View.VISIBLE);
            txtLifeCountPlayer2.setVisibility(View.VISIBLE);

            cmdPlusPlayer1.setVisibility(View.VISIBLE);
            cmdPlusPlayer2.setVisibility(View.VISIBLE);

            cmdMinusPlayer1.setVisibility(View.VISIBLE);
            cmdMinusPlayer2.setVisibility(View.VISIBLE);

            if(playeramount == 4) {
                txtLifeCountPlayer3.setVisibility(View.VISIBLE);
                txtLifeCountPlayer4.setVisibility(View.VISIBLE);

                cmdPlusPlayer3.setVisibility(View.VISIBLE);
                cmdPlusPlayer4.setVisibility(View.VISIBLE);

                cmdMinusPlayer3.setVisibility(View.VISIBLE);
                cmdMinusPlayer4.setVisibility(View.VISIBLE);
            }
        }

        if(showPoisonControls) {

            cmdPlusPoisonPlayer1.setVisibility(View.VISIBLE);
            cmdMinusPoisonPlayer1.setVisibility(View.VISIBLE);
            txtPoisonCountPlayer1.setVisibility(View.VISIBLE);

            cmdPlusPoisonPlayer2.setVisibility(View.VISIBLE);
            cmdMinusPoisonPlayer2.setVisibility(View.VISIBLE);
            txtPoisonCountPlayer2.setVisibility(View.VISIBLE);

            if(playeramount == 4) {
                cmdPlusPoisonPlayer3.setVisibility(View.VISIBLE);
                cmdMinusPoisonPlayer3.setVisibility(View.VISIBLE);
                txtPoisonCountPlayer3.setVisibility(View.VISIBLE);

                cmdPlusPoisonPlayer4.setVisibility(View.VISIBLE);
                cmdMinusPoisonPlayer4.setVisibility(View.VISIBLE);
                txtPoisonCountPlayer4.setVisibility(View.VISIBLE);
            }
        }
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
        layoutPlayer2.setBackgroundColor(powerSaveColor);
        if(playeramount == 4) {
            layoutPlayer3.setBackgroundColor(powerSaveColor);
            layoutPlayer4.setBackgroundColor(powerSaveColor);
        }

        txtLifeCountPlayer1.setTextColor(powerSaveTextColor);
        txtLifeCountPlayer2.setTextColor(powerSaveTextColor);
        if(playeramount == 4) {
            txtLifeCountPlayer3.setTextColor(powerSaveTextColor);
            txtLifeCountPlayer4.setTextColor(powerSaveTextColor);
        }

        txtPoisonCountPlayer1.setTextColor(powerSaveTextColor);
        txtPoisonCountPlayer2.setTextColor(powerSaveTextColor);
        if(playeramount == 4) {
            txtPoisonCountPlayer3.setTextColor(powerSaveTextColor);
            txtPoisonCountPlayer4.setTextColor(powerSaveTextColor);
        }
    }

    @Override
    public void disableEnergySaving(int defaultBlack, int regularTextColor) {
        layoutPlayer1.setBackgroundColor(defaultBlack);
        layoutPlayer2.setBackgroundColor(defaultBlack);
        if(playeramount == 4) {
            layoutPlayer3.setBackgroundColor(defaultBlack);
            layoutPlayer4.setBackgroundColor(defaultBlack);
        }

        txtLifeCountPlayer1.setTextColor(regularTextColor);
        txtLifeCountPlayer2.setTextColor(regularTextColor);
        if(playeramount == 4) {
            txtLifeCountPlayer3.setTextColor(regularTextColor);
            txtLifeCountPlayer4.setTextColor(regularTextColor);
        }

        txtPoisonCountPlayer1.setTextColor(regularTextColor);
        txtPoisonCountPlayer2.setTextColor(regularTextColor);
        if(playeramount == 4) {
            txtPoisonCountPlayer3.setTextColor(regularTextColor);
            txtPoisonCountPlayer4.setTextColor(regularTextColor);
        }
    }
    //endregion



    //region Set Life- and Poisonpoints
    @Override
    public void setLifepoints(PlayerID id, String points) {
        if(id.equals(PlayerID.ONE)) {
            txtLifeCountPlayer1.setText(points);
        } else if(id.equals(PlayerID.TWO)) {
            txtLifeCountPlayer2.setText(points);
        } else if(id.equals(PlayerID.THREE)) {
            txtLifeCountPlayer3.setText(points);
        } else if(id.equals(PlayerID.FOUR)) {
            txtLifeCountPlayer4.setText(points);
        }
    }

        @Override
        public void setPoisonpoints(PlayerID id, String points) {
            if(id.equals(PlayerID.ONE)) {
                txtPoisonCountPlayer1.setText(points);
            } else if(id.equals(PlayerID.TWO)) {
                txtPoisonCountPlayer2.setText(points);
            } else if(id.equals(PlayerID.THREE)) {
                txtPoisonCountPlayer3.setText(points);
            } else if(id.equals(PlayerID.FOUR)) {
                txtPoisonCountPlayer4.setText(points);
            }
        }
        //endregion

        //region Overrides for NavigationDrawer
/*
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
*/
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
        navigationView.getMenu().findItem(R.id.nav_energy_save_mode).setTitle(string);
        mainLayout.closeDrawer(Gravity.START);
    }
    //endregion

    @Override
    public int getPlayerAmount() {
        return playeramount;
    }

    @Override
    public void disableScreenTimeout() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public void enableScreenTimeout() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public int getScreenSize() {
        return getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
    }
}