package com.marceljurtz.lifecounter.Settings;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.marceljurtz.lifecounter.Helper.Color;
import com.marceljurtz.lifecounter.Helper.MagicColor;
import com.marceljurtz.lifecounter.Helper.PreferenceManager;
import com.marceljurtz.lifecounter.R;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

public class SettingsActivity extends Activity implements ISettingsView {

    TextView txtBlack;
    TextView txtBlue;
    TextView txtGreen;
    TextView txtRed;
    TextView txtWhite;

    EditText txtLifepoints;
    EditText txtLongClickPoints;

    Button cmdSelectBlack;
    Button cmdSelectBlue;
    Button cmdSelectGreen;
    Button cmdSelectRed;
    Button cmdSelectWhite;

    Button cmdDiscardChanges;
    Button cmdReset;

    ImageButton cmdBack;

    static int selectedBlack;
    static int selectedBlue;
    static int selectedGreen;
    static int selectedRed;
    static int selectedWhite;

    // Controlling the reset confirmation
    boolean resetConfirmed = false;

    SharedPreferences preferences;

    ISettingsPresenter presenter;

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onPause() {
        presenter.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        preferences = getApplicationContext().getSharedPreferences(PreferenceManager.PREFS, Activity.MODE_PRIVATE);

        // Textviews for color description
        txtBlack = (TextView) findViewById(R.id.txtColorBlack);
        txtBlue = (TextView) findViewById(R.id.txtColorBlue);
        txtGreen = (TextView) findViewById(R.id.txtColorGreen);
        txtRed = (TextView) findViewById(R.id.txtColorRed);
        txtWhite = (TextView) findViewById(R.id.txtColorWhite);

        txtLifepoints = (EditText) findViewById(R.id.txtLiveSelection);
        txtLongClickPoints = (EditText) findViewById(R.id.txtLongClickPoints);

        txtLifepoints.setText(String.valueOf(PreferenceManager.getDefaultLifepoints(preferences)));
        txtLongClickPoints.setText(String.valueOf(PreferenceManager.getLongclickPoints(preferences)));



        //region Color Selection Buttons
        cmdSelectBlack = (Button) findViewById(R.id.cmdSelectBlack);
        cmdSelectBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onColorSelectButtonClick(MagicColor.BLACK);
            }
        });

        cmdSelectBlue = (Button) findViewById(R.id.cmdSelectBlue);
        cmdSelectBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onColorSelectButtonClick(MagicColor.BLUE);
            }
        });

        cmdSelectGreen = (Button) findViewById(R.id.cmdSelectGreen);
        cmdSelectGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onColorSelectButtonClick(MagicColor.GREEN);
            }
        });

        cmdSelectRed = (Button) findViewById(R.id.cmdSelectRed);
        cmdSelectRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onColorSelectButtonClick(MagicColor.RED);
            }
        });

        cmdSelectWhite = (Button) findViewById(R.id.cmdSelectWhite);
        cmdSelectWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onColorSelectButtonClick(MagicColor.WHITE);
            }
        });
        //endregion

        cmdDiscardChanges = (Button) findViewById(R.id.cmdDiscardChanges);
        cmdDiscardChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // Reset button with confirmation dialog
        cmdReset = (Button) findViewById(R.id.cmdResetSettings);
        cmdReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(SettingsActivity.this)
                        .setMessage(R.string.settings_confirm_reset)
                        .setCancelable(false)
                        .setPositiveButton(R.string.settings_confirm_reset_true, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Reset colors
                                selectedBlack = PreferenceManager.getDefaultBlack(preferences);
                                selectedBlue = PreferenceManager.getDefaultBlue(preferences);
                                selectedGreen = PreferenceManager.getDefaultGreen(preferences);
                                selectedRed = PreferenceManager.getDefaultRed(preferences);
                                selectedWhite = PreferenceManager.getDefaultWhite(preferences);

                                /*
                                updateColor(cmdSelectBlack, txtBlack, selectedBlack);
                                updateColor(cmdSelectBlue, txtBlue, selectedBlue);
                                updateColor(cmdSelectGreen, txtGreen, selectedGreen);
                                updateColor(cmdSelectRed, txtRed, selectedRed);
                                updateColor(cmdSelectWhite, txtWhite, selectedWhite);
                                */

                                PreferenceManager.resetLifepoints(preferences);
                                PreferenceManager.resetLongClickPoints(preferences);
                                txtLifepoints.setText(String.valueOf(PreferenceManager.getDefaultLifepoints(preferences)));
                                txtLongClickPoints.setText(String.valueOf(PreferenceManager.getLongclickPoints(preferences)));
                            }
                        })
                        .setNegativeButton(R.string.settings_confirm_reset_false, null)
                        .show();
            }
        });

        cmdBack = (ImageButton)findViewById(R.id.cmdBack);
        cmdBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveSettings();
                finish();
            }
        });

        presenter = new SettingsPresenter(this, preferences);
        presenter.onCreate();
    }

    public void SaveSettings() {
        /* TODO: Save Changes
        SettingsService.saveColor(getApplicationContext(), getString(R.string.shared_preferences_color_black),selectedBlack);
        SettingsService.saveColor(getApplicationContext(), getString(R.string.shared_preferences_color_blue),selectedBlue);
        SettingsService.saveColor(getApplicationContext(), getString(R.string.shared_preferences_color_green),selectedGreen);
        SettingsService.saveColor(getApplicationContext(), getString(R.string.shared_preferences_color_red),selectedRed);
        SettingsService.saveColor(getApplicationContext(), getString(R.string.shared_preferences_color_white), selectedWhite);

        SettingsService.setLifepoints(getApplicationContext(), Integer.parseInt(txtLifepoints.getText().toString()));
        SettingsService.setLongClickPoints(getApplicationContext(), Integer.parseInt(txtLongClickPoints.getText().toString()));
        */
    }

    @Override
    public void onBackPressed(){
        presenter.onBackButtonClick();
    }

    @Override
    public Color getSelectedColor(MagicColor magicColor) {
        return new Color(MagicColor.BLACK, 0);
    }

    @Override
    public int getSelectedLifepoints() {
        return Integer.parseInt(txtLifepoints.getText().toString());
    }

    @Override
    public int getSelectedLongClickPoints() {
        return Integer.parseInt(txtLongClickPoints.getText().toString());
    }

    @Override
    public void loadGameActivity() {
        finish();
    }

    @Override
    public void loadColorPickerDialog(MagicColor color, int r, int g, int b) {
        final ColorPicker cp = new ColorPicker(SettingsActivity.this, r, g, b);
        final MagicColor baseColor = color;

        cp.show();

        Button okColor = (Button) cp.findViewById(R.id.okColorButton);
        okColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newColor = cp.getColor();
                //updateColor(cmdSelectWhite, txtWhite, selectedWhite);
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
}
