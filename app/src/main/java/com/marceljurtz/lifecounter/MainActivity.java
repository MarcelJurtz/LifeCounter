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

    int LP_Home;
    int LP_Guest;
    int LP_Default;

    int PP_Home;
    int PP_Guest;
    int PP_Default;

    final int PLAYER_HOME = 0;
    final int PLAYER_GUEST = 1;

    final int black = Color.parseColor("#CCC2C0");
    final int blue = Color.parseColor("#AAE0FA");
    final int green = Color.parseColor("#9BD3AE");
    final int red = Color.parseColor("#FAAA8F");
    final int white = Color.parseColor("#FFFCD6");

    final int MAX_POISON = 25;
    final int MIN_POISON = 0;
    final int MAX_LIFE = 1000;
    final int MIN_LIFE = -100;

    final String PREF_NAME = "JURTZ_LIFECOUNTER_PREFS";
    final String PREF_DEFAULT_LP = "JURTZ_LIFECOUNTER_DEFAULT_LP";

    boolean poisonEnabled = false;
    boolean colorSettingsEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.hide();
        // Screen-Timeout verhindern
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);
        layoutGuest = (LinearLayout)findViewById(R.id.layout_top);
        layoutHome = (LinearLayout)findViewById(R.id.layout_bottom);

        // Lade Standard Lebensanzahl aus Einstellungen
        // Falls nicht gesetzt: 20
        SharedPreferences sp =  getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        LP_Default = sp.getInt(PREF_DEFAULT_LP,20);
        PP_Default = 0;

        /* ### TextViews ### */
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
        /* ### ImageButtons ### */

        // Guest - Minus
        cmdMinusGuest = (ImageButton)findViewById(R.id.cmdMinusGuest);
        cmdMinusGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LP_Guest--;
                if(LP_Guest < MIN_LIFE) {
                    LP_Guest = MIN_LIFE;
                }
                update(LP_Guest, txtLifeCountGuest);
            }
        });
        cmdMinusGuest.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                LP_Guest -= 5;
                if(LP_Guest < MIN_LIFE) {
                    LP_Guest = MIN_LIFE;
                }
                update(LP_Guest,txtLifeCountGuest);
                return true;
            }
        });

        // Home - Minus
        cmdMinusHome = (ImageButton)findViewById(R.id.cmdMinusHome);
        cmdMinusHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LP_Home--;
                if(LP_Home < MIN_LIFE) {
                    LP_Home = MIN_LIFE;
                }
                update(LP_Home,txtLifeCountHome);
            }
        });
        cmdMinusHome.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LP_Home -= 5;
                if(LP_Home < MIN_LIFE) {
                    LP_Home = MIN_LIFE;
                }
                update(LP_Home,txtLifeCountHome);
                return true;
            }
        });

        // Guest - Plus
        cmdPlusGuest = (ImageButton)findViewById(R.id.cmdPlusGuest);
        cmdPlusGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LP_Guest++;
                if(LP_Guest > MAX_LIFE) {
                    LP_Guest = MAX_LIFE;
                }
                update(LP_Guest, txtLifeCountGuest);
            }
        });
        cmdPlusGuest.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LP_Guest += 5;
                if(LP_Guest > MAX_LIFE) {
                    LP_Guest = MAX_LIFE;
                }
                update(LP_Guest,txtLifeCountGuest);
                return true;
            }
        });

        // Home - Plus
        cmdPlusHome = (ImageButton)findViewById(R.id.cmdPlusHome);
        cmdPlusHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LP_Home++;
                if(LP_Home > MAX_LIFE) {
                    LP_Home = MAX_LIFE;
                }
                update(LP_Home, txtLifeCountHome);
            }
        });
        cmdPlusHome.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LP_Home += 5;
                if(LP_Home > MAX_LIFE) {
                    LP_Home = MAX_LIFE;
                }
                update(LP_Home,txtLifeCountHome);
                return true;
            }
        });

        // Reset
        cmdResetLP = (ImageButton)findViewById(R.id.cmdResetLP);
        cmdResetLP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
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

        // PoisonCount Home
        txtPoisonCountHome = (TextView)findViewById(R.id.txtPoisonCountHome);

        // PoisonCount Guest
        txtPoisonCountGuest = (TextView)findViewById(R.id.txtPoisonCountGuest);

        // Poison Home Plus
        cmdPlusPoisonHome = (ImageButton)findViewById(R.id.cmdPlusPoisonHome);
        cmdPlusPoisonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PP_Home++;
                if(PP_Home > MAX_POISON) {
                    PP_Home = MAX_POISON;
                }
                    update(PP_Home,txtPoisonCountHome);
            }
        });
        cmdPlusPoisonHome.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PP_Home += 5;
                if(PP_Home > MAX_POISON) {
                    PP_Home = MAX_POISON;
                }
                update(PP_Home,txtPoisonCountHome);
                return true;
            }
        });

        // Poison Home Minus
        cmdMinusPoisonHome = (ImageButton)findViewById(R.id.cmdMinusPoisonHome);
        cmdMinusPoisonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PP_Home--;
                if (PP_Home < MIN_POISON) {
                    PP_Home = MIN_POISON;
                }
                update(PP_Home, txtPoisonCountHome);
            }
        });
        cmdMinusPoisonHome.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PP_Home -= 5;
                if(PP_Home < MIN_POISON) {
                    PP_Home = MIN_POISON;
                }
                update(PP_Home,txtPoisonCountHome);
                return true;
            }
        });

        // Poison Guest Plus
        cmdPlusPoisonGuest = (ImageButton)findViewById(R.id.cmdPlusPoisonGuest);
        cmdPlusPoisonGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PP_Guest++;
                if(PP_Guest > MAX_POISON) {
                    PP_Guest = MAX_POISON;
                }
                update(PP_Guest, txtPoisonCountGuest);
            }
        });
        cmdPlusPoisonGuest.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PP_Guest += 5;
                if (PP_Guest > MAX_POISON) {
                    PP_Guest = MAX_POISON;
                }
                update(PP_Guest, txtPoisonCountGuest);
                return true;
            }
        });

        // Poison Guest Minus
        cmdMinusPoisonGuest = (ImageButton)findViewById(R.id.cmdMinusPoisonGuest);
        cmdMinusPoisonGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PP_Guest--;
                if(PP_Guest < MIN_POISON) {
                    PP_Guest = MIN_POISON;
                }
                update(PP_Guest, txtPoisonCountGuest);
            }
        });
        cmdMinusPoisonGuest.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PP_Guest -= 5;
                if(PP_Guest < MIN_POISON) {
                    PP_Guest = MIN_POISON;
                }
                update(PP_Guest,txtPoisonCountGuest);
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


        // ########################### BUTTONS FARBEINSTELLUNUGEN ########################### //
        cmdBlackHome = (ImageButton)findViewById(R.id.cmdBlackHome);
        cmdBlackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayoutColor(black,layoutHome);
            }
        });
        cmdBlackGuest = (ImageButton)findViewById(R.id.cmdBlackGuest);
        cmdBlackGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayoutColor(black,layoutGuest);
            }
        });
        cmdBlueHome = (ImageButton)findViewById(R.id.cmdBlueHome);
        cmdBlueHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayoutColor(blue,layoutHome);
            }
        });
        cmdBlueGuest = (ImageButton)findViewById(R.id.cmdBlueGuest);
        cmdBlueGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayoutColor(blue,layoutGuest);
            }
        });
        cmdGreenHome = (ImageButton)findViewById(R.id.cmdGreenHome);
        cmdGreenHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayoutColor(green,layoutHome);
            }
        });
        cmdGreenGuest = (ImageButton)findViewById(R.id.cmdGreenGuest);
        cmdGreenGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayoutColor(green, layoutGuest);
            }
        });
        cmdRedHome = (ImageButton)findViewById(R.id.cmdRedHome);
        cmdRedHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayoutColor(red,layoutHome);
            }
        });
        cmdRedGuest = (ImageButton)findViewById(R.id.cmdRedGuest);
        cmdRedGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayoutColor(red,layoutGuest);
            }
        });
        cmdWhiteHome = (ImageButton)findViewById(R.id.cmdWhiteHome);
        cmdWhiteHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayoutColor(white,layoutHome);
            }
        });
        cmdWhiteGuest = (ImageButton)findViewById(R.id.cmdWhiteGuest);
        cmdWhiteGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayoutColor(white,layoutGuest);
            }
        });

        // ########################### BUTTONS FARBAUSWAHL ########################### //

        reset();
    }

    // Update TextViews (Punktanzahl)
    private void update(int points, TextView txtPoints) {
            txtPoints.setText(points+"");
    }

    // Reset
    // Beide Leben wieder auf 20 setzen
    // PoisonCounter deaktivieren
    private void reset() {
        // Lifepoints
        LP_Guest = LP_Default;
        LP_Home = LP_Default;
        txtLifeCountGuest.setText(LP_Guest+"");
        txtLifeCountHome.setText(LP_Home+"");

        // Poisonpoints
        PP_Guest = PP_Default;
        PP_Home = PP_Default;
        txtPoisonCountGuest.setText(PP_Guest+"");
        txtPoisonCountHome.setText(PP_Home+"");


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
            cmdTogglePoison.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.button_trigger_poison_disabled));
        }
        else {
            cmdTogglePoison.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.button_trigger_poison_enabled));
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
            cmdToggleColorSettings.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.settings_disabled));
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
            cmdToggleColorSettings.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.settings_enabled));
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

    // Hintergrundfarbe setzen
    private void setLayoutColor(int color, LinearLayout layout) {
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
                    if(inputValue > MAX_LIFE) {
                        inputValue = MAX_LIFE;
                    }
                    SharedPreferences sp = getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt(PREF_DEFAULT_LP, inputValue);
                    editor.commit();
                    LP_Default = inputValue;
                    reset();
                } catch (Exception ex) {
                    // Fehler bei Parsing
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(input,InputMethodManager.SHOW_IMPLICIT);
            }
        });

        dialog.show();
    }
}
