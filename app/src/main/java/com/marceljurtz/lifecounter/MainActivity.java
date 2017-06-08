package com.marceljurtz.lifecounter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.renderscript.ScriptGroup;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.CharacterPickerDialog;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    RelativeLayout mainLayout;
    LinearLayout layoutHome;
    LinearLayout layoutGuest;

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

        // Set color button background colors
        ((GradientDrawable)cmdWhiteGuest.getBackground()).setColor(SettingsService.getColor(getApplicationContext(), getString(R.string.shared_preferences_color_white), ColorService.getDefaultWhite()));
        ((GradientDrawable)cmdWhiteHome.getBackground()).setColor(SettingsService.getColor(getApplicationContext(), getString(R.string.shared_preferences_color_white), ColorService.getDefaultWhite()));

        ((GradientDrawable)cmdRedGuest.getBackground()).setColor(SettingsService.getColor(getApplicationContext(), getString(R.string.shared_preferences_color_red), ColorService.getDefaultRed()));
        ((GradientDrawable)cmdRedHome.getBackground()).setColor(SettingsService.getColor(getApplicationContext(), getString(R.string.shared_preferences_color_red), ColorService.getDefaultRed()));

        ((GradientDrawable)cmdGreenGuest.getBackground()).setColor(SettingsService.getColor(getApplicationContext(), getString(R.string.shared_preferences_color_green), ColorService.getDefaultGreen()));
        ((GradientDrawable)cmdGreenHome.getBackground()).setColor(SettingsService.getColor(getApplicationContext(), getString(R.string.shared_preferences_color_green), ColorService.getDefaultGreen()));

        ((GradientDrawable)cmdBlueGuest.getBackground()).setColor(SettingsService.getColor(getApplicationContext(), getString(R.string.shared_preferences_color_blue), ColorService.getDefaultBlue()));
        ((GradientDrawable)cmdBlueHome.getBackground()).setColor(SettingsService.getColor(getApplicationContext(), getString(R.string.shared_preferences_color_blue), ColorService.getDefaultBlue()));

        ((GradientDrawable)cmdBlackGuest.getBackground()).setColor(SettingsService.getColor(getApplicationContext(), getString(R.string.shared_preferences_color_black), ColorService.getDefaultBlack()));
        ((GradientDrawable)cmdBlackHome.getBackground()).setColor(SettingsService.getColor(getApplicationContext(), getString(R.string.shared_preferences_color_black), ColorService.getDefaultBlack()));

        resetGame();

        super.onStart();
    }

    // Reset
    // Beide Leben wieder auf 20 setzen
    // PoisonCounter deaktivieren
    private void resetGame() {
        // Lifepoints
        //LP_Guest = LP_Default;
        //LP_Home = LP_Default;
        LP_Default = SettingsService.getLifepoints(getApplicationContext());
        PP_Default = 0;

        player_home.setDefaults(LP_Default, PP_Default);
        player_home.reset(txtLifeCountHome, txtPoisonCountHome);
        player_guest.setDefaults(LP_Default, PP_Default);
        player_guest.reset(txtLifeCountGuest, txtPoisonCountGuest);

        // Settings
        poisonEnabled = false;
        togglePoison(poisonEnabled);

        colorSettingsEnabled = false;
        toggleColorButtonsVisibility(colorSettingsEnabled);

        setLayoutColor(SettingsService.getColor(getApplicationContext(), getString(R.string.shared_preferences_color_black), ColorService.getDefaultBlack()), layoutHome);
        setLayoutColor(SettingsService.getColor(getApplicationContext(),getString(R.string.shared_preferences_color_black),ColorService.getDefaultBlack()), layoutGuest);
    }

    // PoisonCounter umschalten
    // ändert Hintergrund
    private void togglePoison(boolean toggle) {
        if(!toggle) {
            cmdTogglePoison.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.icon_poison_disabled));
        }
        else {
            cmdTogglePoison.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.icon_poison));
        }
        togglePoisonViewsVisibility(toggle);
    }

    // PoisonCounter umschalten
    // ändert sichtbarkeit (da bei reset extra benötigt)
    private void togglePoisonViewsVisibility(boolean toggle) {
        if(toggle) {
            txtPoisonCountGuest.setVisibility(View.VISIBLE);
            txtPoisonCountHome.setVisibility(View.VISIBLE);
            cmdPlusPoisonGuest.setVisibility(View.VISIBLE);
            cmdPlusPoisonHome.setVisibility(View.VISIBLE);
            cmdMinusPoisonGuest.setVisibility(View.VISIBLE);
            cmdMinusPoisonHome.setVisibility(View.VISIBLE);
        }
        else {
            txtPoisonCountGuest.setVisibility(View.INVISIBLE);
            txtPoisonCountHome.setVisibility(View.INVISIBLE);
            cmdPlusPoisonGuest.setVisibility(View.INVISIBLE);
            cmdPlusPoisonHome.setVisibility(View.INVISIBLE);
            cmdMinusPoisonGuest.setVisibility(View.INVISIBLE);
            cmdMinusPoisonHome.setVisibility(View.INVISIBLE);
        }
    }

    private void toggleColorButtonsVisibility(boolean toggle) {
        if(!toggle) {
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
        else {
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
    }

    // Energiesparmodus für AMOLED-Displays
    public void toggleEnergySaveMode() {
        if(!powerSaveOn) {
            layoutHome.setBackgroundColor(ColorService.powerSave);
            layoutGuest.setBackgroundColor(ColorService.powerSave);
            txtLifeCountGuest.setTextColor(ColorService.powerSaveTextcolor);
            txtLifeCountHome.setTextColor(ColorService.powerSaveTextcolor);
            txtPoisonCountGuest.setTextColor(ColorService.powerSaveTextcolor);
            txtPoisonCountHome.setTextColor(ColorService.powerSaveTextcolor);
        } else {
            layoutHome.setBackgroundColor(SettingsService.getColor(getApplicationContext(), getString(R.string.shared_preferences_color_black), ColorService.getDefaultBlack()));
            layoutGuest.setBackgroundColor(SettingsService.getColor(getApplicationContext(), getString(R.string.shared_preferences_color_black), ColorService.getDefaultBlack()));
            txtLifeCountGuest.setTextColor(ColorService.regularTextcolor);
            txtLifeCountHome.setTextColor(ColorService.regularTextcolor);
            txtPoisonCountGuest.setTextColor(ColorService.regularTextcolor);
            txtPoisonCountHome.setTextColor(ColorService.regularTextcolor);
        }
        powerSaveOn = !powerSaveOn;
    }

    // Hintergrundfarbe setzen
    private void setLayoutColor(int color, LinearLayout layout) {
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
                setLayoutColor(SettingsService.getColor(getApplicationContext(),getString(R.string.shared_preferences_color_black),ColorService.getDefaultBlack()), layoutHome);
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
                setLayoutColor(SettingsService.getColor(getApplicationContext(),getString(R.string.shared_preferences_color_black),ColorService.getDefaultBlack()), layoutGuest);
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
                setLayoutColor(SettingsService.getColor(getApplicationContext(),getString(R.string.shared_preferences_color_blue),ColorService.getDefaultBlue()), layoutHome);
            }
        });

        cmdBlueGuest = (Button)findViewById(R.id.cmdBlueGuest);
        cmdBlueGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayoutColor(SettingsService.getColor(getApplicationContext(),getString(R.string.shared_preferences_color_blue),ColorService.getDefaultBlue()),layoutGuest);
            }
        });

        cmdGreenHome = (Button)findViewById(R.id.cmdGreenHome);
        cmdGreenHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayoutColor(SettingsService.getColor(getApplicationContext(),getString(R.string.shared_preferences_color_green),ColorService.getDefaultGreen()), layoutHome);
            }
        });

        cmdGreenGuest = (Button)findViewById(R.id.cmdGreenGuest);
        cmdGreenGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayoutColor(SettingsService.getColor(getApplicationContext(),getString(R.string.shared_preferences_color_green),ColorService.getDefaultGreen()), layoutGuest);
            }
        });

        cmdRedHome = (Button)findViewById(R.id.cmdRedHome);
        cmdRedHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayoutColor(SettingsService.getColor(getApplicationContext(),getString(R.string.shared_preferences_color_red),ColorService.getDefaultRed()), layoutHome);
            }
        });

        cmdRedGuest = (Button)findViewById(R.id.cmdRedGuest);
        cmdRedGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayoutColor(SettingsService.getColor(getApplicationContext(),getString(R.string.shared_preferences_color_red),ColorService.getDefaultRed()),layoutGuest);
            }
        });

        cmdWhiteHome = (Button)findViewById(R.id.cmdWhiteHome);
        cmdWhiteHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayoutColor(SettingsService.getColor(getApplicationContext(),getString(R.string.shared_preferences_color_white),ColorService.getDefaultWhite()), layoutHome);
            }
        });

        cmdWhiteGuest = (Button)findViewById(R.id.cmdWhiteGuest);
        cmdWhiteGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayoutColor(SettingsService.getColor(getApplicationContext(),getString(R.string.shared_preferences_color_white),ColorService.getDefaultWhite()),layoutGuest);
            }
        });
    }

    private void initGameControls() {

        // Layouts
        mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);
        layoutGuest = (LinearLayout)findViewById(R.id.layout_top);
        layoutHome = (LinearLayout)findViewById(R.id.layout_bottom);

        // Textviews for lifepoints and poisonpoints
        txtLifeCountGuest = (TextView)findViewById(R.id.txtLifeCountGuest);
        txtLifeCountHome = (TextView)findViewById(R.id.txtLifeCountHome);
        txtPoisonCountHome = (TextView)findViewById(R.id.txtPoisonCountHome);
        txtPoisonCountGuest = (TextView)findViewById(R.id.txtPoisonCountGuest);

        // Guest - Minus
        cmdMinusGuest = (ImageButton)findViewById(R.id.cmdMinusGuest);
        cmdMinusGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player_guest.updateLifepoints(-1, txtLifeCountGuest);
            }
        });
        cmdMinusGuest.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                player_guest.updateLifepoints(-5, txtLifeCountGuest);
                return true;
            }
        });

        // Home - Minus
        cmdMinusHome = (ImageButton)findViewById(R.id.cmdMinusHome);
        cmdMinusHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player_home.updateLifepoints(-1, txtLifeCountHome);
            }
        });
        cmdMinusHome.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                player_home.updateLifepoints(-5, txtLifeCountHome);
                return true;
            }
        });

        // Guest - Plus
        cmdPlusGuest = (ImageButton)findViewById(R.id.cmdPlusGuest);
        cmdPlusGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player_guest.updateLifepoints(1, txtLifeCountGuest);
            }
        });
        cmdPlusGuest.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                player_guest.updateLifepoints(5, txtLifeCountGuest);
                return true;
            }
        });

        // Home - Plus
        cmdPlusHome = (ImageButton)findViewById(R.id.cmdPlusHome);
        cmdPlusHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player_home.updateLifepoints(1, txtLifeCountHome);
            }
        });
        cmdPlusHome.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                player_home.updateLifepoints(5, txtLifeCountHome);
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

        // Poison Home Plus
        cmdPlusPoisonHome = (ImageButton)findViewById(R.id.cmdPlusPoisonHome);
        cmdPlusPoisonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player_home.updatePoisonPoints(1, txtPoisonCountHome);
            }
        });
        cmdPlusPoisonHome.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                player_home.updatePoisonPoints(5, txtPoisonCountHome);
                return true;
            }
        });

        // Poison Home Minus
        cmdMinusPoisonHome = (ImageButton)findViewById(R.id.cmdMinusPoisonHome);
        cmdMinusPoisonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player_home.updatePoisonPoints(-1, txtPoisonCountHome);
            }
        });
        cmdMinusPoisonHome.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                player_home.updatePoisonPoints(-5, txtPoisonCountHome);
                return true;
            }
        });

        // Poison Guest Plus
        cmdPlusPoisonGuest = (ImageButton)findViewById(R.id.cmdPlusPoisonGuest);
        cmdPlusPoisonGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player_guest.updatePoisonPoints(1, txtPoisonCountGuest);
            }
        });
        cmdPlusPoisonGuest.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                player_guest.updatePoisonPoints(5, txtPoisonCountGuest);
                return true;
            }
        });

        // Poison Guest Minus
        cmdMinusPoisonGuest = (ImageButton)findViewById(R.id.cmdMinusPoisonGuest);
        cmdMinusPoisonGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player_guest.updatePoisonPoints(-1, txtPoisonCountGuest);
            }
        });
        cmdMinusPoisonGuest.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                player_guest.updatePoisonPoints(-5, txtPoisonCountGuest);
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
}