package com.marceljurtz.lifecounter.views.Settings;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.marceljurtz.lifecounter.models.Color;
import com.marceljurtz.lifecounter.enums.MagicColorEnum;
import com.marceljurtz.lifecounter.models.PreferenceManager;
import com.marceljurtz.lifecounter.R;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

public class SettingsActivity extends com.marceljurtz.lifecounter.views.Base.View implements ISettingsView {

    //region Controls

    private TextView txtBlack;
    private TextView txtBlue;
    private TextView txtGreen;
    private TextView txtRed;
    private TextView txtWhite;

    private EditText txtLifepoints;
    private EditText txtLongClickPoints;

    private Button cmdSelectBlack;
    private Button cmdSelectBlue;
    private Button cmdSelectGreen;
    private Button cmdSelectRed;
    private Button cmdSelectWhite;

    private Button cmdShowAppIntro;
    private Button cmdReset;

    private Switch chkKeepScreenOn;
    private Switch chkConfirmGameReset;

    private NavigationView navigationView;

    //endregion Controls

    SharedPreferences preferences;
    ISettingsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        preferences = getApplicationContext().getSharedPreferences(PreferenceManager.INSTANCE.getPREFS(), Activity.MODE_PRIVATE);

        initControls();

        disableMenuItem(navigationView, R.id.nav_energy_save_mode);
        disableMenuItem(navigationView, R.id.nav_settings);

        presenter = new SettingsPresenter(this, preferences);
        presenter.onCreate();
    }


    //region Get EditText and color values

    @Override
    public String getLifepoints() {
        return txtLifepoints.getText().toString();
    }

    @Override
    public String getLongClickPoints() {
        return txtLongClickPoints.getText().toString();
    }

    //endregion

    //region Set EditText values

    @Override
    public void setLifepoints(String lifepointText) {
        txtLifepoints.setText(lifepointText);
    }

    @Override
    public void setLongClickPoints(String longClickPointText) {
        txtLongClickPoints.setText(longClickPointText);
    }

    //endregion

    @Override
    public void onBackPressed(){
        presenter.onBackButtonClick();
    }

    @Override
    public void returnToPrevActivity() {
        finish();
    }

    @Override
    public void loadActivity(Class c) {
        Intent intent = new Intent(getApplicationContext(), c);
        startActivity(intent);
    }

    @Override
    public void loadColorPickerDialog(MagicColorEnum color, int r, int g, int b) {
        final ColorPicker cp = new ColorPicker(SettingsActivity.this, r, g, b);
        final MagicColorEnum baseColor = color;

        cp.show();

        Button okColor = (Button) cp.findViewById(R.id.okColorButton);
        okColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newColor = cp.getColor();
                presenter.onColorSelectValueUpdate(new Color(baseColor, newColor));
                cp.dismiss();
            }
        });
    }

    @Override
    public void updateColorButtonValue(Color color) {
        switch(color.getBasecolor()) {
            case BLUE:
                ((GradientDrawable)cmdSelectBlue.getBackground()).setColor(color.getIntValue());
                txtBlue.setText(color.getHexString());
                break;
            case GREEN:
                ((GradientDrawable)cmdSelectGreen.getBackground()).setColor(color.getIntValue());
                txtGreen.setText(color.getHexString());
                break;
            case RED:
                ((GradientDrawable)cmdSelectRed.getBackground()).setColor(color.getIntValue());
                txtRed.setText(color.getHexString());
                break;
            case WHITE:
                ((GradientDrawable)cmdSelectWhite.getBackground()).setColor(color.getIntValue());
                txtWhite.setText(color.getHexString());
                break;
            default:
                ((GradientDrawable)cmdSelectBlack.getBackground()).setColor(color.getIntValue());
                txtBlack.setText(color.getHexString());
                break;
        }
    }

    @Override
    public void loadResetConfirmationDialog() {
        new AlertDialog.Builder(SettingsActivity.this)
                .setMessage(R.string.settings_confirm_reset)
                .setCancelable(false)
                .setPositiveButton(R.string.settings_confirm_reset_true, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        presenter.onResetButtonConfirm();
                    }
                })
                .setNegativeButton(R.string.settings_confirm_reset_false, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.onResetButtonCancel();
                    }
                })
                .show();
    }

    @Override
    public void setKeepScreenOnCheckbox(boolean checked) {
        chkKeepScreenOn.setChecked(checked);
    }

    @Override
    public void setConfirmGameResetCheckbox(boolean checked) {
        chkConfirmGameReset.setChecked(checked);
    }

    private void initControls() {

        txtBlack = (TextView) findViewById(R.id.txtColorBlack);
        txtBlue = (TextView) findViewById(R.id.txtColorBlue);
        txtGreen = (TextView) findViewById(R.id.txtColorGreen);
        txtRed = (TextView) findViewById(R.id.txtColorRed);
        txtWhite = (TextView) findViewById(R.id.txtColorWhite);

        txtLifepoints = (EditText) findViewById(R.id.txtLiveSelection);
        txtLongClickPoints = (EditText) findViewById(R.id.txtLongClickPoints);

        chkKeepScreenOn = (Switch) findViewById(R.id.chkKeepScreenOn);
        chkKeepScreenOn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                presenter.onKeepScreenOnCheckboxClick(isChecked);
            }
        });

        chkConfirmGameReset = (Switch) findViewById(R.id.chkConfirmReset);
        chkConfirmGameReset.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                presenter.onConfirmGameResetCheckboxClick(isChecked);
            }
        });

        cmdSelectBlack = (Button) findViewById(R.id.cmdSelectBlack);
        cmdSelectBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onColorSelectButtonClick(MagicColorEnum.BLACK);
            }
        });

        cmdSelectBlue = (Button) findViewById(R.id.cmdSelectBlue);
        cmdSelectBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onColorSelectButtonClick(MagicColorEnum.BLUE);
            }
        });

        cmdSelectGreen = (Button) findViewById(R.id.cmdSelectGreen);
        cmdSelectGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onColorSelectButtonClick(MagicColorEnum.GREEN);
            }
        });

        cmdSelectRed = (Button) findViewById(R.id.cmdSelectRed);
        cmdSelectRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onColorSelectButtonClick(MagicColorEnum.RED);
            }
        });

        cmdSelectWhite = (Button) findViewById(R.id.cmdSelectWhite);
        cmdSelectWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onColorSelectButtonClick(MagicColorEnum.WHITE);
            }
        });

        cmdReset = (Button) findViewById(R.id.cmdResetSettings);
        cmdReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onResetButtonClick();
            }
        });

        cmdShowAppIntro = (Button)findViewById(R.id.cmdShowIntro);
        cmdShowAppIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onShowAppIntroClick();
            }
        });

        navigationView = (NavigationView)findViewById(R.id.navigationViewSettings);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.nav_about:
                        presenter.onMenuEntryAboutTap();
                        break;
                    case R.id.nav_game_2players:
                        presenter.onMenuEntryTwoPlayerTap();
                        break;
                    case R.id.nav_game_4players:
                        presenter.onMenuEntryFourPlayerTap();
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
    }
}