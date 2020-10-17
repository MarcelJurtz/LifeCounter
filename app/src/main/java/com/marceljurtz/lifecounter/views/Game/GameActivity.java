package com.marceljurtz.lifecounter.views.Game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.marceljurtz.lifecounter.enums.ClickTypeEnum;
import com.marceljurtz.lifecounter.enums.MagicColorEnum;
import com.marceljurtz.lifecounter.enums.OperatorEnum;
import com.marceljurtz.lifecounter.models.Player;
import com.marceljurtz.lifecounter.enums.PlayerIdEnum;
import com.marceljurtz.lifecounter.models.PreferenceManager;
import com.marceljurtz.lifecounter.R;
import com.marceljurtz.lifecounter.views.Intro.IntroActivity;

import org.jetbrains.annotations.NotNull;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

public class GameActivity extends com.marceljurtz.lifecounter.views.Base.View implements IGameView {

    int playeramount;

    Player player1;
    Player player2;
    Player player3;
    Player player4;

    SharedPreferences preferences;

    //region Controls

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

    NavigationView  navigationView;

    Toolbar toolbar;

    //endregion Controls

    boolean showResetConfirmation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = getApplicationContext().getSharedPreferences(PreferenceManager.INSTANCE.getPREFS(), Activity.MODE_PRIVATE);

        checkFirstLaunch();

        playeramount = PreferenceManager.INSTANCE.getPlayerAmount(preferences);

        if(playeramount == 4) {
            setContentView(R.layout.activity_main_4player);
        } else {
            setContentView(R.layout.activity_main_2player);
        }

        player1 = new Player(PlayerIdEnum.ONE);
        player2 = new Player(PlayerIdEnum.TWO);
        if(playeramount == 4) {
            player3 = new Player(PlayerIdEnum.THREE);
            player4 = new Player(PlayerIdEnum.FOUR);
        }

        initControls();

        if(playeramount == 4) {
            disableMenuItem(navigationView, R.id.nav_game_4players);
        } else {
            disableMenuItem(navigationView, R.id.nav_game_2players);
        }

        set_presenter(new GamePresenter(this, preferences));
    }

    private void checkFirstLaunch() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean isFirstStart = preferences.getBoolean("firstStart", true);
                if (isFirstStart) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("firstStart", false);
                    editor.apply();
                    final Intent i = new Intent(GameActivity.this, IntroActivity.class);
                    runOnUiThread(new Runnable() {
                        @Override public void run() {
                            startActivity(i);
                        }
                    });
                }
            }
        });

        t.start();
    }

    @Override
    public void setConfirmGameReset(boolean confirmGameReset) {
        showResetConfirmation = confirmGameReset;
    }

    @Override
    public void restartActivity() {
        recreate();
    }

    @Override
    public void hideNavigationDrawer() {
        mainLayout.closeDrawer(Gravity.START);
    }

    @Override
    public void initColorButton(MagicColorEnum colorLocation, int color) {

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
    public void loadActivity(Class c) {
        Intent intent = new Intent(getApplicationContext(), c);
        startActivity(intent);
    }

    @Override
    public void setLayoutColor(PlayerIdEnum playerIdEnum, int color) {
        if(playerIdEnum.equals(PlayerIdEnum.ONE)) {
            layoutPlayer1.setBackgroundColor(color);
        } else if(playerIdEnum.equals(PlayerIdEnum.TWO)) {
            layoutPlayer2.setBackgroundColor(color);
        } else if(playerIdEnum.equals(PlayerIdEnum.THREE)) {
            layoutPlayer3.setBackgroundColor(color);
        } else if(playerIdEnum.equals(PlayerIdEnum.FOUR)) {
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
    public void setLifepoints(PlayerIdEnum id, String points) {
        if(id.equals(PlayerIdEnum.ONE)) {
            txtLifeCountPlayer1.setText(points);
        } else if(id.equals(PlayerIdEnum.TWO)) {
            txtLifeCountPlayer2.setText(points);
        } else if(id.equals(PlayerIdEnum.THREE)) {
            txtLifeCountPlayer3.setText(points);
        } else if(id.equals(PlayerIdEnum.FOUR)) {
            txtLifeCountPlayer4.setText(points);
        }
    }

        @Override
        public void setPoisonpoints(PlayerIdEnum id, String points) {
            if(id.equals(PlayerIdEnum.ONE)) {
                txtPoisonCountPlayer1.setText(points);
            } else if(id.equals(PlayerIdEnum.TWO)) {
                txtPoisonCountPlayer2.setText(points);
            } else if(id.equals(PlayerIdEnum.THREE)) {
                txtPoisonCountPlayer3.setText(points);
            } else if(id.equals(PlayerIdEnum.FOUR)) {
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
        String string = getResources().getString(R.string.cmdPowerSaveEnable);
        if(!shouldBeEnabled) {
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



    private void initControls() {

        mainLayout = findViewById(R.id.mainLayout);

        toolbar = findViewById(R.id.tbMain);
        setSupportActionBar(toolbar);

        if (playeramount == 4) {
            layoutPlayer1 = findViewById(R.id.rl4Player1);
            layoutPlayer2 = findViewById(R.id.rl4Player2);
            layoutPlayer3 = findViewById(R.id.rl4Player3);
            layoutPlayer4 = findViewById(R.id.rl4Player4);

            txtLifeCountPlayer1 = findViewById(R.id.txtLifeCount4p1);
            txtLifeCountPlayer2 = findViewById(R.id.txtLifeCount4p2);
            txtLifeCountPlayer3 = findViewById(R.id.txtLifeCount4p3);
            txtLifeCountPlayer4 = findViewById(R.id.txtLifeCount4p4);

            cmdPlusPlayer1 = findViewById(R.id.cmdPlus4p1);
            cmdPlusPlayer2 = findViewById(R.id.cmdPlus4p2);
            cmdPlusPlayer3 = findViewById(R.id.cmdPlus4p3);
            cmdPlusPlayer4 = findViewById(R.id.cmdPlus4p4);

            cmdMinusPlayer1 = findViewById(R.id.cmdMinus4p1);
            cmdMinusPlayer2 = findViewById(R.id.cmdMinus4p2);
            cmdMinusPlayer3 = findViewById(R.id.cmdMinus4p3);
            cmdMinusPlayer4 = findViewById(R.id.cmdMinus4p4);

            txtPoisonCountPlayer1 = findViewById(R.id.txtPoisonCount4p1);
            txtPoisonCountPlayer2 = findViewById(R.id.txtPoisonCount4p2);
            txtPoisonCountPlayer3 = findViewById(R.id.txtPoisonCount4p3);
            txtPoisonCountPlayer4 = findViewById(R.id.txtPoisonCount4p4);

            cmdPlusPoisonPlayer1 = findViewById(R.id.cmdPlusPoison4p1);
            cmdPlusPoisonPlayer2 = findViewById(R.id.cmdPlusPoison4p2);
            cmdPlusPoisonPlayer3 = findViewById(R.id.cmdPlusPoison4p3);
            cmdPlusPoisonPlayer4 = findViewById(R.id.cmdPlusPoison4p4);

            cmdMinusPoisonPlayer1 = findViewById(R.id.cmdMinusPoison4p1);
            cmdMinusPoisonPlayer2 = findViewById(R.id.cmdMinusPoison4p2);
            cmdMinusPoisonPlayer3 = findViewById(R.id.cmdMinusPoison4p3);
            cmdMinusPoisonPlayer4 = findViewById(R.id.cmdMinusPoison4p4);

            cmdBlackPlayer1 = findViewById(R.id.cmdBlack4p1);
            cmdBlackPlayer2 = findViewById(R.id.cmdBlack4p2);
            cmdBlackPlayer3 = findViewById(R.id.cmdBlack4p3);
            cmdBlackPlayer4 = findViewById(R.id.cmdBlack4p4);
            cmdBluePlayer1 = findViewById(R.id.cmdBlue4p1);
            cmdBluePlayer2 = findViewById(R.id.cmdBlue4p2);
            cmdBluePlayer3 = findViewById(R.id.cmdBlue4p3);
            cmdBluePlayer4 = findViewById(R.id.cmdBlue4p4);
            cmdGreenPlayer1 = findViewById(R.id.cmdGreen4p1);
            cmdGreenPlayer2 = findViewById(R.id.cmdGreen4p2);
            cmdGreenPlayer3 = findViewById(R.id.cmdGreen4p3);
            cmdGreenPlayer4 = findViewById(R.id.cmdGreen4p4);
            cmdRedPlayer1 = findViewById(R.id.cmdRed4p1);
            cmdRedPlayer2 = findViewById(R.id.cmdRed4p2);
            cmdRedPlayer3 = findViewById(R.id.cmdRed4p3);
            cmdRedPlayer4 = findViewById(R.id.cmdRed4p4);
            cmdWhitePlayer1 = findViewById(R.id.cmdWhite4p1);
            cmdWhitePlayer2 = findViewById(R.id.cmdWhite4p2);
            cmdWhitePlayer3 = findViewById(R.id.cmdWhite4p3);
            cmdWhitePlayer4 = findViewById(R.id.cmdWhite4p4);

            cmdToggleColorSettings = findViewById(R.id.cmdToggleColors4p);
            cmdTogglePoison = findViewById(R.id.cmdTogglePoison4p);
            cmdResetLP = findViewById(R.id.cmdResetLP4p);

            /*
            cmdDrawerTogglePowerSaving = (Button)findViewById(R.id.nav_energy_save_mode);
            cmdSettings = (Button)findViewById(R.id.nav_settings);
            lblVersionInfo = (TextView)findViewById(R.id.lblVersionInfo);
            */
            navigationView = findViewById(R.id.navigationView4players);
        } else {
            layoutPlayer1 = findViewById(R.id.rl2Player1);
            layoutPlayer2 = findViewById(R.id.rl2Player2);

            txtLifeCountPlayer1 = findViewById(R.id.txtLifeCount2p1);
            txtLifeCountPlayer2 = findViewById(R.id.txtLifeCount2p2);

            txtPoisonCountPlayer1 = findViewById(R.id.txtPoisonCount2p1);
            txtPoisonCountPlayer2 = findViewById(R.id.txtPoisonCount2p2);

            cmdBlackPlayer1 = findViewById(R.id.cmdBlack2p1);
            cmdBlackPlayer2 = findViewById(R.id.cmdBlack2p2);
            cmdBluePlayer1 = findViewById(R.id.cmdBlue2p1);
            cmdBluePlayer2 = findViewById(R.id.cmdBlue2p2);
            cmdGreenPlayer1 = findViewById(R.id.cmdGreen2p1);
            cmdGreenPlayer2 = findViewById(R.id.cmdGreen2p2);
            cmdRedPlayer1 = findViewById(R.id.cmdRed2p1);
            cmdRedPlayer2 = findViewById(R.id.cmdRed2p2);
            cmdWhitePlayer1 = findViewById(R.id.cmdWhite2p1);
            cmdWhitePlayer2 = findViewById(R.id.cmdWhite2p2);

            cmdPlusPlayer1 = findViewById(R.id.cmdPlusGuest);
            cmdPlusPlayer2 = findViewById(R.id.cmdPlusHome);
            cmdMinusPlayer1 = findViewById(R.id.cmdMinusGuest);
            cmdMinusPlayer2 = findViewById(R.id.cmdMinusHome);

            cmdPlusPoisonPlayer1 = findViewById(R.id.cmdPlusPoisonGuest);
            cmdPlusPoisonPlayer2 = findViewById(R.id.cmdPlusPoisonHome);
            cmdMinusPoisonPlayer1 = findViewById(R.id.cmdMinusPoisonGuest);
            cmdMinusPoisonPlayer2 = findViewById(R.id.cmdMinusPoisonHome);

            cmdToggleColorSettings = findViewById(R.id.cmdToggleColors);
            cmdTogglePoison = findViewById(R.id.cmdTogglePoison);
            cmdResetLP = findViewById(R.id.cmdResetLP);

            /*
            cmdDrawerTogglePowerSaving = (Button)findViewById(R.id.cmdTogglePowerSave2p);
            cmdSettings = (Button)findViewById(R.id.cmdSettings2p);
            lblVersionInfo = (TextView)findViewById(R.id.lblAppVersion2p);
            */

            navigationView = findViewById(R.id.navigationView2players);
        }

        //region Button Black

        cmdBlackPlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IGamePresenter) get_presenter()).onColorButtonClick(PlayerIdEnum.ONE, MagicColorEnum.BLACK, ClickTypeEnum.SHORT);
            }
        });
        cmdBlackPlayer1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // toggleEnergySaveMode();
                ((IGamePresenter) get_presenter()).onColorButtonClick(PlayerIdEnum.ONE, MagicColorEnum.BLACK, ClickTypeEnum.LONG);
                return true;
            }
        });

        cmdBlackPlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IGamePresenter) get_presenter()).onColorButtonClick(PlayerIdEnum.TWO, MagicColorEnum.BLACK, ClickTypeEnum.SHORT);
            }
        });
        cmdBlackPlayer2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // toggleEnergySaveMode();
                ((IGamePresenter) get_presenter()).onColorButtonClick(PlayerIdEnum.TWO, MagicColorEnum.BLACK, ClickTypeEnum.LONG);
                return true;
            }
        });

        if(playeramount == 4) {
            cmdBlackPlayer3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((IGamePresenter) get_presenter()).onColorButtonClick(PlayerIdEnum.THREE, MagicColorEnum.BLACK, ClickTypeEnum.SHORT);
                }
            });
            cmdBlackPlayer3.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // toggleEnergySaveMode();
                    ((IGamePresenter) get_presenter()).onColorButtonClick(PlayerIdEnum.THREE, MagicColorEnum.BLACK, ClickTypeEnum.LONG);
                    return true;
                }
            });

            cmdBlackPlayer4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((IGamePresenter) get_presenter()).onColorButtonClick(PlayerIdEnum.FOUR, MagicColorEnum.BLACK, ClickTypeEnum.SHORT);
                }
            });
            cmdBlackPlayer4.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // toggleEnergySaveMode();
                    ((IGamePresenter) get_presenter()).onColorButtonClick(PlayerIdEnum.FOUR, MagicColorEnum.BLACK, ClickTypeEnum.LONG);
                    return true;
                }
            });
        }

        //endregion

        //region Button Blue

        cmdBluePlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IGamePresenter) get_presenter()).onColorButtonClick(PlayerIdEnum.ONE, MagicColorEnum.BLUE, ClickTypeEnum.SHORT);
            }
        });

        cmdBluePlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IGamePresenter) get_presenter()).onColorButtonClick(PlayerIdEnum.TWO, MagicColorEnum.BLUE, ClickTypeEnum.SHORT);
            }
        });

        if(playeramount == 4) {
            cmdBluePlayer3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((IGamePresenter) get_presenter()).onColorButtonClick(PlayerIdEnum.THREE, MagicColorEnum.BLUE, ClickTypeEnum.SHORT);
                }
            });

            cmdBluePlayer4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((IGamePresenter) get_presenter()).onColorButtonClick(PlayerIdEnum.FOUR, MagicColorEnum.BLUE, ClickTypeEnum.SHORT);
                }
            });
        }

        //endregion

        //region Button Green
        cmdGreenPlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IGamePresenter) get_presenter()).onColorButtonClick(PlayerIdEnum.ONE, MagicColorEnum.GREEN, ClickTypeEnum.SHORT);
            }
        });

        cmdGreenPlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IGamePresenter) get_presenter()).onColorButtonClick(PlayerIdEnum.TWO, MagicColorEnum.GREEN, ClickTypeEnum.SHORT);
            }
        });

        if(playeramount == 4) {
            cmdGreenPlayer3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((IGamePresenter) get_presenter()).onColorButtonClick(PlayerIdEnum.THREE, MagicColorEnum.GREEN, ClickTypeEnum.SHORT);
                }
            });

            cmdGreenPlayer4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((IGamePresenter) get_presenter()).onColorButtonClick(PlayerIdEnum.FOUR, MagicColorEnum.GREEN, ClickTypeEnum.SHORT);
                }
            });
        }
        //endregion

        //region Button Red
        cmdRedPlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IGamePresenter) get_presenter()).onColorButtonClick(PlayerIdEnum.ONE, MagicColorEnum.RED, ClickTypeEnum.SHORT);
            }
        });

        cmdRedPlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IGamePresenter) get_presenter()).onColorButtonClick(PlayerIdEnum.TWO, MagicColorEnum.RED, ClickTypeEnum.SHORT);
            }
        });

        if(playeramount == 4) {
            cmdRedPlayer3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((IGamePresenter) get_presenter()).onColorButtonClick(PlayerIdEnum.THREE, MagicColorEnum.RED, ClickTypeEnum.SHORT);
                }
            });

            cmdRedPlayer4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((IGamePresenter) get_presenter()).onColorButtonClick(PlayerIdEnum.FOUR, MagicColorEnum.RED, ClickTypeEnum.SHORT);
                }
            });
        }
        //endregion

        //region Button White
        cmdWhitePlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IGamePresenter) get_presenter()).onColorButtonClick(PlayerIdEnum.ONE, MagicColorEnum.WHITE, ClickTypeEnum.SHORT);
            }
        });

        cmdWhitePlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IGamePresenter) get_presenter()).onColorButtonClick(PlayerIdEnum.TWO, MagicColorEnum.WHITE, ClickTypeEnum.SHORT);
            }
        });

        if(playeramount == 4) {
            cmdWhitePlayer3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((IGamePresenter) get_presenter()).onColorButtonClick(PlayerIdEnum.THREE, MagicColorEnum.WHITE, ClickTypeEnum.SHORT);
                }
            });

            cmdWhitePlayer4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((IGamePresenter) get_presenter()).onColorButtonClick(PlayerIdEnum.FOUR, MagicColorEnum.WHITE, ClickTypeEnum.SHORT);
                }
            });
        }
        //endregion


        cmdPlusPlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IGamePresenter) get_presenter()).onLifeUpdate(player1.getPlayerIdEnum(), ClickTypeEnum.SHORT, OperatorEnum.ADD);
            }
        });
        cmdPlusPlayer1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ((IGamePresenter) get_presenter()).onLifeUpdate(player1.getPlayerIdEnum(), ClickTypeEnum.LONG, OperatorEnum.ADD);
                return true;
            }
        });

        cmdMinusPlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IGamePresenter) get_presenter()).onLifeUpdate(player1.getPlayerIdEnum(), ClickTypeEnum.SHORT, OperatorEnum.SUBSTRACT);
            }
        });
        cmdMinusPlayer1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ((IGamePresenter) get_presenter()).onLifeUpdate(player1.getPlayerIdEnum(), ClickTypeEnum.LONG, OperatorEnum.SUBSTRACT);
                return true;
            }
        });

        //endregion

        //region Lifepoints Guest

        cmdPlusPlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IGamePresenter) get_presenter()).onLifeUpdate(player2.getPlayerIdEnum(), ClickTypeEnum.SHORT, OperatorEnum.ADD);
            }
        });
        cmdPlusPlayer2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ((IGamePresenter) get_presenter()).onLifeUpdate(player2.getPlayerIdEnum(), ClickTypeEnum.LONG, OperatorEnum.ADD);
                return true;
            }
        });

        cmdMinusPlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IGamePresenter) get_presenter()).onLifeUpdate(player2.getPlayerIdEnum(), ClickTypeEnum.SHORT, OperatorEnum.SUBSTRACT);
            }
        });
        cmdMinusPlayer2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ((IGamePresenter) get_presenter()).onLifeUpdate(player2.getPlayerIdEnum(), ClickTypeEnum.LONG, OperatorEnum.SUBSTRACT);
                return true;
            }
        });

        if(playeramount == 4) {
            cmdPlusPlayer3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((IGamePresenter) get_presenter()).onLifeUpdate(player3.getPlayerIdEnum(), ClickTypeEnum.SHORT, OperatorEnum.ADD);
                }
            });
            cmdPlusPlayer3.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ((IGamePresenter) get_presenter()).onLifeUpdate(player3.getPlayerIdEnum(), ClickTypeEnum.LONG, OperatorEnum.ADD);
                    return true;
                }
            });

            cmdMinusPlayer3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((IGamePresenter) get_presenter()).onLifeUpdate(player3.getPlayerIdEnum(), ClickTypeEnum.SHORT, OperatorEnum.SUBSTRACT);
                }
            });
            cmdMinusPlayer3.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ((IGamePresenter) get_presenter()).onLifeUpdate(player3.getPlayerIdEnum(), ClickTypeEnum.LONG, OperatorEnum.SUBSTRACT);
                    return true;
                }
            });

            cmdPlusPlayer4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((IGamePresenter) get_presenter()).onLifeUpdate(player4.getPlayerIdEnum(), ClickTypeEnum.SHORT, OperatorEnum.ADD);
                }
            });
            cmdPlusPlayer4.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ((IGamePresenter) get_presenter()).onLifeUpdate(player4.getPlayerIdEnum(), ClickTypeEnum.LONG, OperatorEnum.ADD);
                    return true;
                }
            });

            cmdMinusPlayer4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((IGamePresenter) get_presenter()).onLifeUpdate(player4.getPlayerIdEnum(), ClickTypeEnum.SHORT, OperatorEnum.SUBSTRACT);
                }
            });
            cmdMinusPlayer4.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ((IGamePresenter) get_presenter()).onLifeUpdate(player4.getPlayerIdEnum(), ClickTypeEnum.LONG, OperatorEnum.SUBSTRACT);
                    return true;
                }
            });
        }

        //endregion

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NotNull MenuItem item) {
                int id = item.getItemId();
                switch(id) {
                    case R.id.nav_settings:
                        //presenter.onSettingsButtonClick(ClickTypeEnum.LONG);
                        get_presenter().onMenuEntrySettingsTap();
                        break;
                    case R.id.nav_about:
                        get_presenter().onMenuEntryAboutTap();
                        break;
                    case R.id.nav_energy_save_mode:
                        get_presenter().onMenuEntryEnergySaveTap();
                        break;
                    case R.id.nav_game_2players:
                        get_presenter().onMenuEntryTogglePlayerTap();
                        break;
                    case R.id.nav_game_4players:
                        get_presenter().onMenuEntryTogglePlayerTap();
                        break;
                    case R.id.nav_dicing:
                        get_presenter().onMenuEntryDicingTap();
                        break;
                    case R.id.nav_countermanager:
                        get_presenter().onMenuEntryCounterManagerTap();
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

                if(!showResetConfirmation) {
                    ((IGamePresenter) get_presenter()).onResetButtonClick();
                } else {

                    boolean confirmed = false;

                    AlertDialog.Builder alert = new AlertDialog.Builder(GameActivity.this);
                    alert.setTitle(getResources().getString(R.string.dialog_game_reset_confirm_title));
                    alert.setMessage(getResources().getString(R.string.dialog_game_reset_confirm_message));
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            ((IGamePresenter) get_presenter()).onResetButtonClick();
                        }
                    });

                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    alert.show();
                }
            }
        });

        // endregion

        //region Button Toggle Poison

        cmdTogglePoison.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IGamePresenter) get_presenter()).onPoisonButtonClick();
            }
        });

        //endregion

        //region Poison Buttons Home

        // Poison Home Plus
        cmdPlusPoisonPlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IGamePresenter) get_presenter()).onPoisonUpdate(player1.getPlayerIdEnum(), ClickTypeEnum.SHORT, OperatorEnum.ADD);
            }
        });
        cmdPlusPoisonPlayer1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ((IGamePresenter) get_presenter()).onPoisonUpdate(player1.getPlayerIdEnum(), ClickTypeEnum.LONG, OperatorEnum.ADD);
                return true;
            }
        });

        // Poison Home Minus
        cmdMinusPoisonPlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IGamePresenter) get_presenter()).onPoisonUpdate(player1.getPlayerIdEnum(), ClickTypeEnum.SHORT, OperatorEnum.SUBSTRACT);
            }
        });
        cmdMinusPoisonPlayer1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ((IGamePresenter) get_presenter()).onPoisonUpdate(player1.getPlayerIdEnum(), ClickTypeEnum.LONG, OperatorEnum.SUBSTRACT);
                return true;
            }
        });

        //endregion

        //region Poison Buttons Guest

        // Poison Guest Plus
        cmdPlusPoisonPlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IGamePresenter) get_presenter()).onPoisonUpdate(player2.getPlayerIdEnum(), ClickTypeEnum.SHORT, OperatorEnum.ADD);
            }
        });
        cmdPlusPoisonPlayer2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ((IGamePresenter) get_presenter()).onPoisonUpdate(player2.getPlayerIdEnum(), ClickTypeEnum.LONG, OperatorEnum.ADD);
                return true;
            }
        });

        // Poison Guest Minus
        cmdMinusPoisonPlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IGamePresenter) get_presenter()).onPoisonUpdate(player2.getPlayerIdEnum(), ClickTypeEnum.SHORT, OperatorEnum.SUBSTRACT);
            }
        });
        cmdMinusPoisonPlayer2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ((IGamePresenter) get_presenter()).onPoisonUpdate(player2.getPlayerIdEnum(), ClickTypeEnum.LONG, OperatorEnum.SUBSTRACT);
                return true;
            }
        });

        //endregion

        if(playeramount == 4) {
            cmdPlusPoisonPlayer3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((IGamePresenter) get_presenter()).onPoisonUpdate(player3.getPlayerIdEnum(), ClickTypeEnum.SHORT, OperatorEnum.ADD);
                }
            });
            cmdPlusPoisonPlayer3.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ((IGamePresenter) get_presenter()).onPoisonUpdate(player3.getPlayerIdEnum(), ClickTypeEnum.LONG, OperatorEnum.ADD);
                    return true;
                }
            });

            cmdMinusPoisonPlayer3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((IGamePresenter) get_presenter()).onPoisonUpdate(player3.getPlayerIdEnum(), ClickTypeEnum.SHORT, OperatorEnum.SUBSTRACT);
                }
            });
            cmdMinusPoisonPlayer3.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ((IGamePresenter) get_presenter()).onPoisonUpdate(player3.getPlayerIdEnum(), ClickTypeEnum.LONG, OperatorEnum.SUBSTRACT);
                    return true;
                }
            });


            cmdPlusPoisonPlayer4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((IGamePresenter) get_presenter()).onPoisonUpdate(player4.getPlayerIdEnum(), ClickTypeEnum.SHORT, OperatorEnum.ADD);
                }
            });
            cmdPlusPoisonPlayer4.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ((IGamePresenter) get_presenter()).onPoisonUpdate(player4.getPlayerIdEnum(), ClickTypeEnum.LONG, OperatorEnum.ADD);
                    return true;
                }
            });

            cmdMinusPoisonPlayer4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((IGamePresenter) get_presenter()).onPoisonUpdate(player4.getPlayerIdEnum(), ClickTypeEnum.SHORT, OperatorEnum.SUBSTRACT);
                }
            });
            cmdMinusPoisonPlayer4.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ((IGamePresenter) get_presenter()).onPoisonUpdate(player4.getPlayerIdEnum(), ClickTypeEnum.LONG, OperatorEnum.SUBSTRACT);
                    return true;
                }
            });
        }

        //region Settings Button
        cmdToggleColorSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IGamePresenter) get_presenter()).onSettingsButtonClick(ClickTypeEnum.SHORT);
            }
        });
        cmdToggleColorSettings.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ((IGamePresenter) get_presenter()).onSettingsButtonClick(ClickTypeEnum.LONG);
                return true;
            }
        });
    }
}