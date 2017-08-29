package com.marceljurtz.lifecounter.Game;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marceljurtz.lifecounter.Helper.BaseInterface.IView;
import com.marceljurtz.lifecounter.Helper.ClickType;
import com.marceljurtz.lifecounter.Helper.MagicColor;
import com.marceljurtz.lifecounter.Helper.Operator;
import com.marceljurtz.lifecounter.Helper.PlayerID;
import com.marceljurtz.lifecounter.Helper.PreferenceManager;
import com.marceljurtz.lifecounter.R;
import com.marceljurtz.lifecounter.Settings.SettingsActivity;

public class GameActivity extends AppCompatActivity implements IGameView {

    private IGamePresenter presenter;

    RelativeLayout mainLayout;
    RelativeLayout layoutHome;
    RelativeLayout layoutGuest;

    ImageButton cmdPlusGuest;
    ImageButton cmdPlusHome;
    ImageButton cmdMinusGuest;
    ImageButton cmdMinusHome;
    ImageButton cmdResetLP;
    ImageButton cmdTogglePoison;
    ImageButton cmdPlusPoisonHome;
    ImageButton cmdPlusPoisonGuest;
    ImageButton cmdMinusPoisonHome;
    ImageButton cmdMinusPoisonGuest;
    ImageButton cmdToggleColorSettings;

    Button cmdBlackGuest;
    Button cmdBlueGuest;
    Button cmdGreenGuest;
    Button cmdRedGuest;
    Button cmdWhiteGuest;

    Button cmdBlackHome;
    Button cmdBlueHome;
    Button cmdGreenHome;
    Button cmdRedHome;
    Button cmdWhiteHome;

    TextView txtLifeCountGuest;
    TextView txtLifeCountHome;
    TextView txtPoisonCountGuest;
    TextView txtPoisonCountHome;

    int LP_Default;
    int PP_Default;

    // amount of points that will be added / substraced on long click
    // default: 5
    int longClickPoints;

    // Default settings
    boolean poisonEnabled = false;
    boolean colorSettingsEnabled = false;
    boolean powerSaveOn = false;

    Player player_home;
    Player player_guest;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide ActionBar
        final ActionBar ab = getSupportActionBar();
        ab.hide();

        // Disable screen timeout
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Init SharedPreferences
        preferences = getApplicationContext().getSharedPreferences(PreferenceManager.PREFS, Activity.MODE_PRIVATE);

        // Players
        player_home = new Player(PlayerID.ONE);
        player_guest = new Player(PlayerID.TWO);

        // Init GamePresenter
        presenter = new GamePresenter(this, preferences);

        //region Initialize Views

        //region Color Buttons

        // BLACK - PLAYER 1
        cmdBlackHome = (Button)findViewById(R.id.cmdBlackHome);
        cmdBlackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.colorButtonClick(PlayerID.ONE, MagicColor.BLACK, ClickType.SHORT);
            }
        });
        cmdBlackHome.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // toggleEnergySaveMode();
                presenter.colorButtonClick(PlayerID.ONE, MagicColor.BLACK, ClickType.LONG);
                return true;
            }
        });

        // BLACK - PLAYER 2
        cmdBlackGuest = (Button)findViewById(R.id.cmdBlackGuest);
        cmdBlackGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.colorButtonClick(PlayerID.TWO, MagicColor.BLACK, ClickType.SHORT);
            }
        });
        cmdBlackGuest.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //toggleEnergySaveMode();
                presenter.colorButtonClick(PlayerID.TWO, MagicColor.BLACK, ClickType.LONG);
                return true;
            }
        });



        // BLUE - PLAYER 1
        cmdBlueHome = (Button)findViewById(R.id.cmdBlueHome);
        cmdBlueHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.colorButtonClick(PlayerID.ONE, MagicColor.BLUE, ClickType.SHORT);
            }
        });

        // BLUE - PLAYER 2
        cmdBlueGuest = (Button)findViewById(R.id.cmdBlueGuest);
        cmdBlueGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.colorButtonClick(PlayerID.TWO, MagicColor.BLUE, ClickType.SHORT);
            }
        });



        // GREEN - PLAYER 1
        cmdGreenHome = (Button)findViewById(R.id.cmdGreenHome);
        cmdGreenHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.colorButtonClick(PlayerID.ONE, MagicColor.GREEN, ClickType.SHORT);
            }
        });

        // GREEN - PLAYER 2
        cmdGreenGuest = (Button)findViewById(R.id.cmdGreenGuest);
        cmdGreenGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.colorButtonClick(PlayerID.TWO, MagicColor.GREEN, ClickType.SHORT);
            }
        });



        // RED - PLAYER 1
        cmdRedHome = (Button)findViewById(R.id.cmdRedHome);
        cmdRedHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.colorButtonClick(PlayerID.ONE, MagicColor.RED, ClickType.SHORT);
            }
        });

        // RED - PLAYER 2
        cmdRedGuest = (Button)findViewById(R.id.cmdRedGuest);
        cmdRedGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.colorButtonClick(PlayerID.TWO, MagicColor.RED, ClickType.SHORT);
            }
        });



        // WHITE - PLAYER 1
        cmdWhiteHome = (Button)findViewById(R.id.cmdWhiteHome);
        cmdWhiteHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.colorButtonClick(PlayerID.ONE, MagicColor.WHITE, ClickType.SHORT);
            }
        });

        // WHITE - PLAYER 2
        cmdWhiteGuest = (Button)findViewById(R.id.cmdWhiteGuest);
        cmdWhiteGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.colorButtonClick(PlayerID.TWO, MagicColor.WHITE, ClickType.SHORT);
            }
        });

        //endregion

        //region Layouts and TextViews

        // Layouts
        mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);
        layoutGuest = (RelativeLayout)findViewById(R.id.layout_top);
        layoutHome = (RelativeLayout)findViewById(R.id.layout_bottom);

        // Textviews for Lifepoints and Poisonpoints
        txtLifeCountGuest = (TextView)findViewById(R.id.txtLifeCountGuest);
        txtLifeCountHome = (TextView)findViewById(R.id.txtLifeCountHome);
        txtPoisonCountHome = (TextView)findViewById(R.id.txtPoisonCountHome);
        txtPoisonCountGuest = (TextView)findViewById(R.id.txtPoisonCountGuest);

        //endregion

        //region Lifepoints Home

        // Home - Plus
        cmdPlusHome = (ImageButton)findViewById(R.id.cmdPlusHome);
        cmdPlusHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onLifeUpdate(player_home.getPlayerID(), ClickType.SHORT, Operator.ADD);
            }
        });
        cmdPlusHome.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                presenter.onLifeUpdate(player_home.getPlayerID(), ClickType.LONG, Operator.ADD);
                return true;
            }
        });

        // Home - Minus
        cmdMinusHome = (ImageButton)findViewById(R.id.cmdMinusHome);
        cmdMinusHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onLifeUpdate(player_home.getPlayerID(), ClickType.SHORT, Operator.SUBSTRACT);
            }
        });
        cmdMinusHome.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                presenter.onLifeUpdate(player_home.getPlayerID(), ClickType.LONG, Operator.SUBSTRACT);
                return true;
            }
        });

        //endregion

        //region Lifepoints Guest

        // Guest - Plus
        cmdPlusGuest = (ImageButton)findViewById(R.id.cmdPlusGuest);
        cmdPlusGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onLifeUpdate(player_guest.getPlayerID(), ClickType.SHORT, Operator.ADD);
            }
        });
        cmdPlusGuest.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                presenter.onLifeUpdate(player_guest.getPlayerID(), ClickType.LONG, Operator.ADD);
                return true;
            }
        });

        // Guest - Minus
        cmdMinusGuest = (ImageButton)findViewById(R.id.cmdMinusGuest);
        cmdMinusGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onLifeUpdate(player_guest.getPlayerID(), ClickType.SHORT, Operator.SUBSTRACT);
            }
        });
        cmdMinusGuest.setOnLongClickListener(new View.OnLongClickListener() {
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
                presenter.resetButtonClick();
            }
        });

        // endregion

        //region Button Toggle Poison

        cmdTogglePoison = (ImageButton)findViewById(R.id.cmdTogglePoison);
        cmdTogglePoison.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.poisonButtonClick();
            }
        });

        //endregion

        //region Poison Buttons Home

        // Poison Home Plus
        cmdPlusPoisonHome = (ImageButton)findViewById(R.id.cmdPlusPoisonHome);
        cmdPlusPoisonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onPoisonUpdate(player_home.getPlayerID(), ClickType.SHORT, Operator.ADD);
            }
        });
        cmdPlusPoisonHome.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                presenter.onPoisonUpdate(player_home.getPlayerID(), ClickType.LONG, Operator.ADD);
                return true;
            }
        });

        // Poison Home Minus
        cmdMinusPoisonHome = (ImageButton)findViewById(R.id.cmdMinusPoisonHome);
        cmdMinusPoisonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onPoisonUpdate(player_home.getPlayerID(), ClickType.SHORT, Operator.SUBSTRACT);
            }
        });
        cmdMinusPoisonHome.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                presenter.onPoisonUpdate(player_home.getPlayerID(), ClickType.LONG, Operator.SUBSTRACT);
                return true;
            }
        });

        //endregion

        //region Poison Buttons Guest

        // Poison Guest Plus
        cmdPlusPoisonGuest = (ImageButton)findViewById(R.id.cmdPlusPoisonGuest);
        cmdPlusPoisonGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onPoisonUpdate(player_guest.getPlayerID(), ClickType.SHORT, Operator.ADD);
            }
        });
        cmdPlusPoisonGuest.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                presenter.onPoisonUpdate(player_guest.getPlayerID(), ClickType.LONG, Operator.ADD);
                return true;
            }
        });

        // Poison Guest Minus
        cmdMinusPoisonGuest = (ImageButton)findViewById(R.id.cmdMinusPoisonGuest);
        cmdMinusPoisonGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onPoisonUpdate(player_guest.getPlayerID(), ClickType.SHORT, Operator.SUBSTRACT);
            }
        });
        cmdMinusPoisonGuest.setOnLongClickListener(new View.OnLongClickListener() {
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
                presenter.settingsButtonClick(ClickType.SHORT);
            }
        });
        cmdToggleColorSettings.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                presenter.settingsButtonClick(ClickType.LONG);
                return true;
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
                buttonGuest = cmdBlackGuest;
                break;
            case BLUE:
                buttonHome = cmdBlueHome;
                buttonGuest = cmdBlueGuest;
                break;
            case GREEN:
                buttonHome = cmdGreenHome;
                buttonGuest = cmdGreenGuest;
                break;
            case RED:
                buttonHome = cmdRedHome;
                buttonGuest = cmdRedGuest;
                break;
            case WHITE:
                buttonHome = cmdWhiteHome;
                buttonGuest = cmdWhiteGuest;
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
    public void setPlayerLayoutBackgroundColor(PlayerID playerID, int color) {
        switch(playerID) {
            case ONE:
                layoutHome.setBackgroundColor(color);
                break;
            case TWO:
                layoutGuest.setBackgroundColor(color);
                break;
        }
    }

    @Override
    public void setLayoutColor(PlayerID playerID, int color) {
        if(playerID.equals(PlayerID.ONE)) {
            layoutHome.setBackgroundColor(color);
        } else if(playerID.equals(PlayerID.TWO)) {
            layoutGuest.setBackgroundColor(color);
        }
    }

    //region TODO: REMOVE! TEMPORARY DEPRECATED RUBBISH
    // Reset
    // Beide Leben wieder auf 20 setzen
    // PoisonCounter deaktivieren
    /*
    private void resetGame() {
        // Lifepoints
        LP_Default = SettingsService.getLifepoints(getApplicationContext());
        PP_Default = 0;

        player_home.setDefaults(LP_Default, PP_Default);
        player_home.reset(txtLifeCountHome, txtPoisonCountHome);
        player_guest.setDefaults(LP_Default, PP_Default);
        player_guest.reset(txtLifeCountGuest, txtPoisonCountGuest);

        setLayoutColor(SettingsService.getColor(getApplicationContext(), getString(R.string.shared_preferences_color_black), ColorService.getDefaultBlack()), layoutHome);
        setLayoutColor(SettingsService.getColor(getApplicationContext(), getString(R.string.shared_preferences_color_black), ColorService.getDefaultBlack()), layoutGuest);

        resetGameTemporary();
    }
    */
    // Only resets gui without changing the lifepoints.
    // This is needed for changig between apps
    // otherwise, the game will reset by minimization

    /*
    private void resetGameTemporary() {
        longClickPoints = SettingsService.getLongClickPoints(getApplicationContext());

        // Settings
        poisonEnabled = false;
        togglePoison(poisonEnabled);

        colorSettingsEnabled = false;
        toggleColorButtonsVisibility(colorSettingsEnabled);
    }
    */
    //endregion

    //region Toggle Poison Controls
    @Override
    public void enablePoisonControls() {
        txtPoisonCountGuest.setVisibility(View.VISIBLE);
        txtPoisonCountHome.setVisibility(View.VISIBLE);
        cmdPlusPoisonGuest.setVisibility(View.VISIBLE);
        cmdPlusPoisonHome.setVisibility(View.VISIBLE);
        cmdMinusPoisonGuest.setVisibility(View.VISIBLE);
        cmdMinusPoisonHome.setVisibility(View.VISIBLE);
    }

    @Override
    public void poisonButtonEnable() {
        cmdTogglePoison.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.icon_poison));
    }

    @Override
    public void disablePoisonControls() {
        txtPoisonCountGuest.setVisibility(View.INVISIBLE);
        txtPoisonCountHome.setVisibility(View.INVISIBLE);
        cmdPlusPoisonGuest.setVisibility(View.INVISIBLE);
        cmdPlusPoisonHome.setVisibility(View.INVISIBLE);
        cmdMinusPoisonGuest.setVisibility(View.INVISIBLE);
        cmdMinusPoisonHome.setVisibility(View.INVISIBLE);
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
        cmdBlackGuest.setVisibility(View.VISIBLE);
        cmdBlueHome.setVisibility(View.VISIBLE);
        cmdBlueGuest.setVisibility(View.VISIBLE);
        cmdGreenHome.setVisibility(View.VISIBLE);
        cmdGreenGuest.setVisibility(View.VISIBLE);
        cmdRedHome.setVisibility(View.VISIBLE);
        cmdRedGuest.setVisibility(View.VISIBLE);
        cmdWhiteHome.setVisibility(View.VISIBLE);
        cmdWhiteGuest.setVisibility(View.VISIBLE);
    }

    @Override
    public void settingsButtonEnable() {
        cmdToggleColorSettings.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.icon_settings));
    }

    @Override
    public void disableSettingsControls() {
        cmdToggleColorSettings.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.icon_settings_disabled));
        cmdBlackHome.setVisibility(View.INVISIBLE);
        cmdBlackGuest.setVisibility(View.INVISIBLE);
        cmdBlueHome.setVisibility(View.INVISIBLE);
        cmdBlueGuest.setVisibility(View.INVISIBLE);
        cmdGreenHome.setVisibility(View.INVISIBLE);
        cmdGreenGuest.setVisibility(View.INVISIBLE);
        cmdRedHome.setVisibility(View.INVISIBLE);
        cmdRedGuest.setVisibility(View.INVISIBLE);
        cmdWhiteHome.setVisibility(View.INVISIBLE);
        cmdWhiteGuest.setVisibility(View.INVISIBLE);
    }

    @Override
    public void settingsButtonDisable() {
        cmdToggleColorSettings.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.icon_settings_disabled));
    }


    //endregion

    //region Toggle Energy Saving Option
    @Override
    public void enableEnergySaving(int powerSaveColor, int powerSaveTextColor) {
        layoutHome.setBackgroundColor(powerSaveColor);
        layoutGuest.setBackgroundColor(powerSaveColor);
        txtLifeCountGuest.setTextColor(powerSaveTextColor);
        txtLifeCountHome.setTextColor(powerSaveTextColor);
        txtPoisonCountGuest.setTextColor(powerSaveTextColor);
        txtPoisonCountHome.setTextColor(powerSaveTextColor);
    }

    @Override
    public void disableEnergySaving(int defaultBlack, int regularTextColor) {
        layoutHome.setBackgroundColor(defaultBlack);
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
}