package com.marceljurtz.lifecounter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.CharacterPickerDialog;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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
    ImageButton cmdBlackHome;
    ImageButton cmdBlackGuest;
    ImageButton cmdBlueHome;
    ImageButton cmdBlueGuest;
    ImageButton cmdGreenHome;
    ImageButton cmdGreenGuest;
    ImageButton cmdRedHome;
    ImageButton cmdRedGuest;
    ImageButton cmdWhiteHome;
    ImageButton cmdWhiteGuest;

    TextView txtLifeCountGuest;
    TextView txtLifeCountHome;
    TextView txtPoisonCountGuest;
    TextView txtPoisonCountHome;

    int LP_Default;
    int PP_Default;

    // Shared Preferences
    final String PREF_NAME = "JURTZ_LIFECOUNTER_PREFS";
    final String PREF_DEFAULT_LP = "JURTZ_LIFECOUNTER_DEFAULT_LP";

    // Defaultsettings
    boolean poisonEnabled = false;
    boolean colorSettingsEnabled = false;
    boolean powerSaveOn = false;

    Player player1;
    Player player2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.hide();
        // Screen-Timeout verhindern
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        player1 = new Player();
        player2 = new Player();

        mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);
        layoutGuest = (LinearLayout)findViewById(R.id.layout_top);
        layoutHome = (LinearLayout)findViewById(R.id.layout_bottom);

        // Lade Standard Lebensanzahl aus Einstellungen
        // Falls nicht gesetzt: 20
        SharedPreferences sp =  getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        LP_Default = sp.getInt(PREF_DEFAULT_LP,20);
        PP_Default = 0;
        player1.setDefaults(LP_Default,PP_Default);
        player2.setDefaults(LP_Default,PP_Default);

        txtLifeCountGuest = (TextView)findViewById(R.id.txtLifeCountGuest);
        txtLifeCountGuest.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setDefaultLifepoints();
                return true;
            }
        });
        txtLifeCountHome = (TextView)findViewById(R.id.txtLifeCountHome);
        txtLifeCountHome.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setDefaultLifepoints();
                return true;
            }
        });

        // PoisonCount Home
        txtPoisonCountHome = (TextView)findViewById(R.id.txtPoisonCountHome);

        // PoisonCount Guest
        txtPoisonCountGuest = (TextView)findViewById(R.id.txtPoisonCountGuest);

        // Guest - Minus
        cmdMinusGuest = (ImageButton)findViewById(R.id.cmdMinusGuest);
        cmdMinusGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player2.updateLifepoints(-1,txtLifeCountGuest);
            }
        });
        cmdMinusGuest.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                player2.updateLifepoints(-5,txtLifeCountGuest);
                return true;
            }
        });

        // Home - Minus
        cmdMinusHome = (ImageButton)findViewById(R.id.cmdMinusHome);
        cmdMinusHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player1.updateLifepoints(-1, txtLifeCountHome);
            }
        });
        cmdMinusHome.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                player1.updateLifepoints(-5,txtLifeCountHome);
                return true;
            }
        });

        // Guest - Plus
        cmdPlusGuest = (ImageButton)findViewById(R.id.cmdPlusGuest);
        cmdPlusGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player2.updateLifepoints(1, txtLifeCountGuest);
            }
        });
        cmdPlusGuest.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                player2.updateLifepoints(5,txtLifeCountGuest);
                return true;
            }
        });

        // Home - Plus
        cmdPlusHome = (ImageButton)findViewById(R.id.cmdPlusHome);
        cmdPlusHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player1.updateLifepoints(1, txtLifeCountHome);
            }
        });
        cmdPlusHome.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                player1.updateLifepoints(5, txtLifeCountHome);
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
                player1.updatePoisonPoints(1, txtPoisonCountHome);
            }
        });
        cmdPlusPoisonHome.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                player1.updatePoisonPoints(5, txtPoisonCountHome);
                return true;
            }
        });

        // Poison Home Minus
        cmdMinusPoisonHome = (ImageButton)findViewById(R.id.cmdMinusPoisonHome);
        cmdMinusPoisonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player1.updatePoisonPoints(-1, txtPoisonCountHome);
            }
        });
        cmdMinusPoisonHome.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                player1.updatePoisonPoints(-5,txtPoisonCountHome);
                return true;
            }
        });

        // Poison Guest Plus
        cmdPlusPoisonGuest = (ImageButton)findViewById(R.id.cmdPlusPoisonGuest);
        cmdPlusPoisonGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player2.updatePoisonPoints(1, txtPoisonCountGuest);
            }
        });
        cmdPlusPoisonGuest.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                player2.updatePoisonPoints(5,txtPoisonCountGuest);
                return true;
            }
        });

        // Poison Guest Minus
        cmdMinusPoisonGuest = (ImageButton)findViewById(R.id.cmdMinusPoisonGuest);
        cmdMinusPoisonGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player2.updatePoisonPoints(-1, txtPoisonCountGuest);
            }
        });
        cmdMinusPoisonGuest.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                player2.updatePoisonPoints(-5,txtPoisonCountGuest);
                return true;
            }
        });

        cmdToggleColorSettings = (ImageButton)findViewById(R.id.cmdToggleColors);
        cmdToggleColorSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorSettingsEnabled = !colorSettingsEnabled;
                toggleColorSettings(colorSettingsEnabled);
            }
        });

        cmdBlackHome = (ImageButton)findViewById(R.id.cmdBlackHome);
        cmdBlackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayoutColor(ColorService.black, layoutHome);
            }
        });
        cmdBlackHome.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                toggleEnergySaveMode();
                return true;
            }
        });

        cmdBlackGuest = (ImageButton)findViewById(R.id.cmdBlackGuest);
        cmdBlackGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayoutColor(ColorService.black,layoutGuest);
            }
        });
        cmdBlackGuest.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                toggleEnergySaveMode();
                return true;
            }
        });

        cmdBlueHome = (ImageButton)findViewById(R.id.cmdBlueHome);
        cmdBlueHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayoutColor(ColorService.blue,layoutHome);
            }
        });

        cmdBlueGuest = (ImageButton)findViewById(R.id.cmdBlueGuest);
        cmdBlueGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayoutColor(ColorService.blue,layoutGuest);
            }
        });

        cmdGreenHome = (ImageButton)findViewById(R.id.cmdGreenHome);
        cmdGreenHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayoutColor(ColorService.green,layoutHome);
            }
        });

        cmdGreenGuest = (ImageButton)findViewById(R.id.cmdGreenGuest);
        cmdGreenGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayoutColor(ColorService.green, layoutGuest);
            }
        });

        cmdRedHome = (ImageButton)findViewById(R.id.cmdRedHome);
        cmdRedHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayoutColor(ColorService.red,layoutHome);
            }
        });

        cmdRedGuest = (ImageButton)findViewById(R.id.cmdRedGuest);
        cmdRedGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayoutColor(ColorService.red,layoutGuest);
            }
        });

        cmdWhiteHome = (ImageButton)findViewById(R.id.cmdWhiteHome);
        cmdWhiteHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayoutColor(ColorService.white,layoutHome);
            }
        });

        cmdWhiteGuest = (ImageButton)findViewById(R.id.cmdWhiteGuest);

        cmdWhiteGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayoutColor(ColorService.white,layoutGuest);
            }
        });

        resetGame();
    }


    // Reset
    // Beide Leben wieder auf 20 setzen
    // PoisonCounter deaktivieren
    private void resetGame() {
        // Lifepoints
        //LP_Guest = LP_Default;
        //LP_Home = LP_Default;
        player1.setDefaults(LP_Default, PP_Default);
        player1.reset(txtLifeCountHome,txtPoisonCountHome);
        player2.setDefaults(LP_Default, PP_Default);
        player2.reset(txtLifeCountGuest, txtPoisonCountGuest);

        // Settings
        poisonEnabled = false;
        togglePoison(poisonEnabled);

        colorSettingsEnabled = false;
        toggleColorSettings(colorSettingsEnabled);
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
        togglePoisonViews(toggle);
    }

    // PoisonCounter umschalten
    // ändert sichtbarkeit (da bei reset extra benötigt)
    private void togglePoisonViews(boolean toggle) {
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

    // ColorSettings umschalten
    private void toggleColorSettings(boolean toggle) {
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
            layoutHome.setBackgroundColor(ColorService.powerSafe);
            layoutGuest.setBackgroundColor(ColorService.powerSafe);
            txtLifeCountGuest.setTextColor(ColorService.powerSafeTextcolor);
            txtLifeCountHome.setTextColor(ColorService.powerSafeTextcolor);
            txtPoisonCountGuest.setTextColor(ColorService.powerSafeTextcolor);
            txtPoisonCountHome.setTextColor(ColorService.powerSafeTextcolor);
        } else {
            layoutHome.setBackgroundColor(ColorService.black);
            layoutGuest.setBackgroundColor(ColorService.black);
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

    // Dialog anzeigen zum Setzen der Standardanzahl an Lebenspunkten
    // Speichern in Shared Preferences
    private void setDefaultLifepoints() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final EditText input = new EditText(this);
        input.setText(LP_Default + "");
        input.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 32);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setSelection(input.getText().toString().length());

        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    String inputText = "";

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        inputText = input.getText().toString();
                        int inputValue;
                        try {
                            inputValue = Integer.parseInt(inputText);
                            if (inputValue > ValueService.getMaxLife()) {
                                inputValue = ValueService.getMaxLife();
                            }
                            SharedPreferences sp = getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putInt(PREF_DEFAULT_LP, inputValue);
                            editor.commit();
                            LP_Default = inputValue;
                        } catch (Exception ex) {
                            // Fehler bei Parsing
                        } finally {
                            resetGame();
                        }
            }
        }

            );
            AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener()

                                 {
                                     @Override
                                     public void onShow(DialogInterface dialog) {
                                         InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                         inputMethodManager.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT);
            }
                                 }

            );

            dialog.show();
        }
    }
