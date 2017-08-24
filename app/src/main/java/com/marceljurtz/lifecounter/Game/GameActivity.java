package com.marceljurtz.lifecounter.Game;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marceljurtz.lifecounter.Helper.PlayerID;
import com.marceljurtz.lifecounter.R;
import com.marceljurtz.lifecounter.Settings.SettingsActivity;
import com.marceljurtz.lifecounter.Settings.SettingsService;

public class GameActivity extends AppCompatActivity implements IView {

    private GameController controller;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar ab = getSupportActionBar();
        ab.hide();

        // Disable screen timeout
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Declare all controls
        initControls();

        player_home = new Player(txtLifeCountHome, txtPoisonCountHome);
        player_guest = new Player(txtLifeCountGuest, txtPoisonCountGuest);

        LP_Default = SettingsService.getLifepoints(getApplicationContext());
        PP_Default = SettingsService.getDefaultPoisonPoints();

        player_home.setDefaults(LP_Default, PP_Default);
        player_guest.setDefaults(LP_Default, PP_Default);

        resetGame();
    }

    @Override
    protected void onStart() {

        controller = new GameController(this, getApplicationContext());

        resetGameTemporary();

        super.onStart();
    }

    @Override
    public void initColorButtonBlack(int color) {
        ((GradientDrawable)cmdBlackGuest.getBackground()).setColor(color);
        ((GradientDrawable)cmdBlackHome.getBackground()).setColor(color);
    }

    @Override
    public void initColorButtonBlue(int color) {
        ((GradientDrawable)cmdBlueGuest.getBackground()).setColor(color);
        ((GradientDrawable)cmdBlueHome.getBackground()).setColor(color);
    }

    @Override
    public void initColorButtonGreen(int color) {
        ((GradientDrawable)cmdGreenGuest.getBackground()).setColor(color);
        ((GradientDrawable)cmdGreenHome.getBackground()).setColor(color);
    }

    @Override
    public void initColorButtonRed(int color) {
        ((GradientDrawable)cmdRedGuest.getBackground()).setColor(color);
        ((GradientDrawable)cmdRedHome.getBackground()).setColor(color);
    }

    @Override
    public void initColorButtonWhite(int color) {
        ((GradientDrawable)cmdWhiteGuest.getBackground()).setColor(color);
        ((GradientDrawable)cmdWhiteHome.getBackground()).setColor(color);
    }

    // Reset
    // Beide Leben wieder auf 20 setzen
    // PoisonCounter deaktivieren
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

    // Only resets gui without changing the lifepoints.
    // This is needed for changig between apps
    // otherwise, the game will reset by minimization
    private void resetGameTemporary() {
        longClickPoints = SettingsService.getLongClickPoints(getApplicationContext());

        // Settings
        poisonEnabled = false;
        togglePoison(poisonEnabled);

        colorSettingsEnabled = false;
        toggleColorButtonsVisibility(colorSettingsEnabled);
    }

    public void enablePoisonControls() {
        cmdTogglePoison.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.icon_poison_disabled));

        txtPoisonCountGuest.setVisibility(View.VISIBLE);
        txtPoisonCountHome.setVisibility(View.VISIBLE);
        cmdPlusPoisonGuest.setVisibility(View.VISIBLE);
        cmdPlusPoisonHome.setVisibility(View.VISIBLE);
        cmdMinusPoisonGuest.setVisibility(View.VISIBLE);
        cmdMinusPoisonHome.setVisibility(View.VISIBLE);
    }

    public void disablePoisonControls() {
        cmdTogglePoison.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.icon_poison));

        txtPoisonCountGuest.setVisibility(View.INVISIBLE);
        txtPoisonCountHome.setVisibility(View.INVISIBLE);
        cmdPlusPoisonGuest.setVisibility(View.INVISIBLE);
        cmdPlusPoisonHome.setVisibility(View.INVISIBLE);
        cmdMinusPoisonGuest.setVisibility(View.INVISIBLE);
        cmdMinusPoisonHome.setVisibility(View.INVISIBLE);
    }

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

    public void enableEnergySaving() {
        layoutHome.setBackgroundColor(ColorService.powerSave);
        layoutGuest.setBackgroundColor(ColorService.powerSave);
        txtLifeCountGuest.setTextColor(ColorService.powerSaveTextcolor);
        txtLifeCountHome.setTextColor(ColorService.powerSaveTextcolor);
        txtPoisonCountGuest.setTextColor(ColorService.powerSaveTextcolor);
        txtPoisonCountHome.setTextColor(ColorService.powerSaveTextcolor);
    }

    public void disableEnergySaving() {
        layoutHome.setBackgroundColor(SettingsService.getColor(getApplicationContext(), getString(R.string.shared_preferences_color_black), ColorService.getDefaultBlack()));
        layoutGuest.setBackgroundColor(SettingsService.getColor(getApplicationContext(), getString(R.string.shared_preferences_color_black), ColorService.getDefaultBlack()));
        txtLifeCountGuest.setTextColor(ColorService.regularTextcolor);
        txtLifeCountHome.setTextColor(ColorService.regularTextcolor);
        txtPoisonCountGuest.setTextColor(ColorService.regularTextcolor);
        txtPoisonCountHome.setTextColor(ColorService.regularTextcolor);
    }

    // Hintergrundfarbe setzen
    private void setLayoutColor(int color, RelativeLayout layout) {
        if(powerSaveOn) {
            toggleEnergySaveMode();
        }
        layout.setBackgroundColor(color);
    }

    private void initControls() {
        initColorButtons();
        initGameControls();
    }

    private void initColorButtons() {
        cmdBlackHome = (Button)findViewById(R.id.cmdBlackHome);
        cmdBlackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayoutColor(controller.getBlackInt(), layoutHome);
            }
        });
        cmdBlackHome.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                toggleEnergySaveMode();
                return true;
            }
        });

        cmdBlackGuest = (Button)findViewById(R.id.cmdBlackGuest);
        cmdBlackGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayoutColor(controller.getBlackInt(), layoutGuest);
            }
        });
        cmdBlackGuest.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                toggleEnergySaveMode();
                return true;
            }
        });

        cmdBlueHome = (Button)findViewById(R.id.cmdBlueHome);
        cmdBlueHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayoutColor(controller.getBlueInt(),  layoutHome);
            }
        });

        cmdBlueGuest = (Button)findViewById(R.id.cmdBlueGuest);
        cmdBlueGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayoutColor(controller.getBlueInt(), layoutGuest);
            }
        });

        cmdGreenHome = (Button)findViewById(R.id.cmdGreenHome);
        cmdGreenHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayoutColor(controller.getGreenInt(),  layoutHome);
            }
        });

        cmdGreenGuest = (Button)findViewById(R.id.cmdGreenGuest);
        cmdGreenGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayoutColor(controller.getGreenInt(), layoutGuest);
            }
        });

        cmdRedHome = (Button)findViewById(R.id.cmdRedHome);
        cmdRedHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayoutColor(controller.getRedInt(), layoutHome);
            }
        });

        cmdRedGuest = (Button)findViewById(R.id.cmdRedGuest);
        cmdRedGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayoutColor(controller.getRedInt(), layoutGuest);
            }
        });

        cmdWhiteHome = (Button)findViewById(R.id.cmdWhiteHome);
        cmdWhiteHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayoutColor(controller.getWhiteInt(), layoutHome);
            }
        });

        cmdWhiteGuest = (Button)findViewById(R.id.cmdWhiteGuest);
        cmdWhiteGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayoutColor(controller.getWhiteInt(),layoutGuest);
            }
        });
    }

    private void initGameControls() {

        // Layouts
        mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);
        layoutGuest = (RelativeLayout)findViewById(R.id.layout_top);
        layoutHome = (RelativeLayout)findViewById(R.id.layout_bottom);

        // Textviews for lifepoints and poisonpoints
        txtLifeCountGuest = (TextView)findViewById(R.id.txtLifeCountGuest);
        txtLifeCountHome = (TextView)findViewById(R.id.txtLifeCountHome);
        txtPoisonCountHome = (TextView)findViewById(R.id.txtPoisonCountHome);
        txtPoisonCountGuest = (TextView)findViewById(R.id.txtPoisonCountGuest);

        // ******************************************************* //
        //                    LIFEPOINTS HOME                      //
        // ******************************************************* //

        // Home - Plus
        cmdPlusHome = (ImageButton)findViewById(R.id.cmdPlusHome);
        cmdPlusHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.updateLifepoints(player_home.getPlayerID(), 1);
            }
        });
        cmdPlusHome.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                controller.updateLifepoints(player_home.getPlayerID(), SettingsService.getLongClickPoints());
                return true;
            }
        });

        // Home - Minus
        cmdMinusHome = (ImageButton)findViewById(R.id.cmdMinusHome);
        cmdMinusHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.updateLifepoints(player_home.getPlayerID(), -1);
            }
        });
        cmdMinusHome.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                controller.updateLifepoints(player_home.getPlayerID(), -SettingsService.getLongClickPoints());
                return true;
            }
        });

        // ******************************************************* //
        //                    LIFEPOINTS GUEST                     //
        // ******************************************************* //

        // Guest - Plus
        cmdPlusGuest = (ImageButton)findViewById(R.id.cmdPlusGuest);
        cmdPlusGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.updateLifepoints(player_guest.getPlayerID(), 1);
            }
        });
        cmdPlusGuest.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                controller.updateLifepoints(player_guest.getPlayerID(), SettingsService.getLongClickPoints());
                return true;
            }
        });

        // Guest - Minus
        cmdMinusGuest = (ImageButton)findViewById(R.id.cmdMinusGuest);
        cmdMinusGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.updateLifepoints(player_guest.getPlayerID(), -1);
            }
        });
        cmdMinusGuest.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                controller.updateLifepoints(player_guest.getPlayerID(), -SettingsService.getLongClickPoints());
                return true;
            }
        });



        // Reset
        cmdResetLP = (ImageButton)findViewById(R.id.cmdResetLP);
        cmdResetLP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

        cmdTogglePoison = (ImageButton)findViewById(R.id.cmdTogglePoison);
        cmdTogglePoison.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                poisonEnabled = !poisonEnabled;
                togglePoison(poisonEnabled);
            }
        });

        // ******************************************************* //
        //                       POISON HOME                       //
        // ******************************************************* //

        // Poison Home Plus
        cmdPlusPoisonHome = (ImageButton)findViewById(R.id.cmdPlusPoisonHome);
        cmdPlusPoisonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.updatePoisonpoints(player_home.getPlayerID(), 1);
            }
        });
        cmdPlusPoisonHome.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                controller.updatePoisonpoints(player_home.getPlayerID(), SettingsService.getLongClickPoints());
                return true;
            }
        });

        // Poison Home Minus
        cmdMinusPoisonHome = (ImageButton)findViewById(R.id.cmdMinusPoisonHome);
        cmdMinusPoisonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.updatePoisonpoints(player_home.getPlayerID(), -1);
            }
        });
        cmdMinusPoisonHome.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                controller.updatePoisonpoints(player_home.getPlayerID(), -SettingsService.getLongClickPoints());
                return true;
            }
        });

        // ******************************************************* //
        //                       POISON GUEST                      //
        // ******************************************************* //


        // Poison Guest Plus
        cmdPlusPoisonGuest = (ImageButton)findViewById(R.id.cmdPlusPoisonGuest);
        cmdPlusPoisonGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.updatePoisonpoints(player_guest.getPlayerID(), 1);
            }
        });
        cmdPlusPoisonGuest.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                controller.updatePoisonpoints(player_guest.getPlayerID(), SettingsService.getLongClickPoints());
                return true;
            }
        });

        // Poison Guest Minus
        cmdMinusPoisonGuest = (ImageButton)findViewById(R.id.cmdMinusPoisonGuest);
        cmdMinusPoisonGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.updatePoisonpoints(player_guest.getPlayerID(), -1);
            }
        });
        cmdMinusPoisonGuest.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                controller.updatePoisonpoints(player_guest.getPlayerID(), -SettingsService.getLongClickPoints());
                return true;
            }
        });

        cmdToggleColorSettings = (ImageButton)findViewById(R.id.cmdToggleColors);
        cmdToggleColorSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorSettingsEnabled = !colorSettingsEnabled;
                toggleColorButtonsVisibility(colorSettingsEnabled);
            }
        });
        cmdToggleColorSettings.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
                return true;
            }
        });
    }

    public void setLifepoints(PlayerID id, int points) {
        if(id.equals(PlayerID.ONE)) {
            txtLifeCountHome.setText(points + "");
        } else if(id.equals(PlayerID.TWO)) {
            txtLifeCountGuest.setText(points + "");
        }
    }

    public void setPoisonpoints(PlayerID id, int points) {
        if(id.equals(PlayerID.ONE)) {
            txtPoisonCountHome.setText(points + "");
        } else if(id.equals(PlayerID.TWO)) {
            txtPoisonCountGuest.setText(points + "");
        }
    }
}