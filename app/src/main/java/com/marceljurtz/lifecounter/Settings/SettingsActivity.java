package com.marceljurtz.lifecounter.Settings;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.marceljurtz.lifecounter.R;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

public class SettingsActivity extends Activity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Textviews for color description
        txtBlack = (TextView) findViewById(R.id.txtColorBlack);
        txtBlue = (TextView) findViewById(R.id.txtColorBlue);
        txtGreen = (TextView) findViewById(R.id.txtColorGreen);
        txtRed = (TextView) findViewById(R.id.txtColorRed);
        txtWhite = (TextView) findViewById(R.id.txtColorWhite);

        txtLifepoints = (EditText) findViewById(R.id.txtLiveSelection);
        txtLongClickPoints = (EditText) findViewById(R.id.txtLongClickPoints);

        // Get customized color values from settings
        selectedBlack = SettingsService.getColor(getApplicationContext(),getString(R.string.shared_preferences_color_black), ColorService.getDefaultBlack());
        selectedBlue = SettingsService.getColor(getApplicationContext(), getString(R.string.shared_preferences_color_blue), ColorService.getDefaultBlue());
        selectedGreen = SettingsService.getColor(getApplicationContext(),getString(R.string.shared_preferences_color_green), ColorService.getDefaultGreen());
        selectedRed = SettingsService.getColor(getApplicationContext(), getString(R.string.shared_preferences_color_red), ColorService.getDefaultRed());
        selectedWhite = SettingsService.getColor(getApplicationContext(), getString(R.string.shared_preferences_color_white), ColorService.getDefaultWhite());

        // Add hex value of colors to textviews
        txtBlack.setText(ColorService.getHexString(selectedBlack));
        txtBlue.setText(ColorService.getHexString(selectedBlue));
        txtGreen.setText(ColorService.getHexString(selectedGreen));
        txtRed.setText(ColorService.getHexString(selectedRed));
        txtWhite.setText(ColorService.getHexString(selectedWhite));

        txtLifepoints.setText(String.valueOf(SettingsService.getLifepoints(getApplicationContext())));
        txtLongClickPoints.setText(String.valueOf(SettingsService.getLongClickPoints(getApplicationContext())));


        /***************************************************/
        /*                     BUTTONS                     */
        /***************************************************/

        cmdSelectBlack = (Button) findViewById(R.id.cmdSelectBlack);
        updateColor(cmdSelectBlack, txtBlack, selectedBlack);
        cmdSelectBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int r = ColorService.getRGB(selectedBlack)[0];
                int g = ColorService.getRGB(selectedBlack)[1];
                int b = ColorService.getRGB(selectedBlack)[2];

                final ColorPicker cp = new ColorPicker(SettingsActivity.this, r, g, b);

                cp.show();

                Button okColor = (Button) cp.findViewById(R.id.okColorButton);
                okColor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedBlack = cp.getColor();
                        updateColor(cmdSelectBlack, txtBlack, selectedBlack);
                        cp.dismiss();
                    }
                });
            }
        });

        cmdSelectBlue = (Button) findViewById(R.id.cmdSelectBlue);
        updateColor(cmdSelectBlue, txtBlue, selectedBlue);
        cmdSelectBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int r = ColorService.getRGB(selectedBlue)[0];
                int g = ColorService.getRGB(selectedBlue)[1];
                int b = ColorService.getRGB(selectedBlue)[2];

                final ColorPicker cp = new ColorPicker(SettingsActivity.this, r, g, b);

                cp.show();

                Button okColor = (Button) cp.findViewById(R.id.okColorButton);
                okColor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedBlue = cp.getColor();
                        updateColor(cmdSelectBlue, txtBlue, selectedBlue);
                        cp.dismiss();
                    }
                });
            }
        });

        cmdSelectGreen = (Button) findViewById(R.id.cmdSelectGreen);
        updateColor(cmdSelectGreen, txtGreen, selectedGreen);
        cmdSelectGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int r = ColorService.getRGB(selectedGreen)[0];
                int g = ColorService.getRGB(selectedGreen)[1];
                int b = ColorService.getRGB(selectedGreen)[2];

                final ColorPicker cp = new ColorPicker(SettingsActivity.this, r, g, b);

                cp.show();

                Button okColor = (Button) cp.findViewById(R.id.okColorButton);
                okColor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedGreen = cp.getColor();
                        updateColor(cmdSelectGreen, txtGreen, selectedGreen);
                        cp.dismiss();
                    }
                });
            }
        });

        cmdSelectRed = (Button) findViewById(R.id.cmdSelectRed);
        updateColor(cmdSelectRed, txtRed, selectedRed);
        cmdSelectRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int r = ColorService.getRGB(selectedRed)[0];
                int g = ColorService.getRGB(selectedRed)[1];
                int b = ColorService.getRGB(selectedRed)[2];

                final ColorPicker cp = new ColorPicker(SettingsActivity.this, r, g, b);

                cp.show();

                Button okColor = (Button) cp.findViewById(R.id.okColorButton);
                okColor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedRed = cp.getColor();
                        updateColor(cmdSelectRed, txtRed, selectedRed);
                        cp.dismiss();
                    }
                });
            }
        });

        cmdSelectWhite = (Button) findViewById(R.id.cmdSelectWhite);
        updateColor(cmdSelectWhite, txtWhite, selectedWhite);
        cmdSelectWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int r = ColorService.getRGB(selectedWhite)[0];
                int g = ColorService.getRGB(selectedWhite)[1];
                int b = ColorService.getRGB(selectedWhite)[2];

                final ColorPicker cp = new ColorPicker(SettingsActivity.this, r, g, b);

                cp.show();

                Button okColor = (Button) cp.findViewById(R.id.okColorButton);
                okColor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedWhite = cp.getColor();
                        updateColor(cmdSelectWhite, txtWhite, selectedWhite);
                        cp.dismiss();
                    }
                });
            }
        });

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
                                selectedBlack = ColorService.getDefaultBlack();
                                selectedBlue = ColorService.getDefaultBlue();
                                selectedGreen = ColorService.getDefaultGreen();
                                selectedRed = ColorService.getDefaultRed();
                                selectedWhite = ColorService.getDefaultWhite();

                                updateColor(cmdSelectBlack, txtBlack, selectedBlack);
                                updateColor(cmdSelectBlue, txtBlue, selectedBlue);
                                updateColor(cmdSelectGreen, txtGreen, selectedGreen);
                                updateColor(cmdSelectRed, txtRed, selectedRed);
                                updateColor(cmdSelectWhite, txtWhite, selectedWhite);

                                SettingsService.resetLifepoints(getApplicationContext());
                                SettingsService.resetLongClickPoints(getApplicationContext());
                                txtLifepoints.setText(String.valueOf(SettingsService.getLifepoints(getApplicationContext())));
                                txtLongClickPoints.setText(String.valueOf(SettingsService.getLongClickPoints(getApplicationContext())));
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
    }

    public void SaveSettings() {
        SettingsService.saveColor(getApplicationContext(), getString(R.string.shared_preferences_color_black),selectedBlack);
        SettingsService.saveColor(getApplicationContext(), getString(R.string.shared_preferences_color_blue),selectedBlue);
        SettingsService.saveColor(getApplicationContext(), getString(R.string.shared_preferences_color_green),selectedGreen);
        SettingsService.saveColor(getApplicationContext(), getString(R.string.shared_preferences_color_red),selectedRed);
        SettingsService.saveColor(getApplicationContext(), getString(R.string.shared_preferences_color_white), selectedWhite);

        SettingsService.setLifepoints(getApplicationContext(), Integer.parseInt(txtLifepoints.getText().toString()));
        SettingsService.setLongClickPoints(getApplicationContext(), Integer.parseInt(txtLongClickPoints.getText().toString()));
    }

    // Set button background and textview text to match
    public void updateColor(Button button, TextView txt, int color) {
        ((GradientDrawable)button.getBackground()).setColor(color);
        txt.setText(ColorService.getHexString(color));
    }

    @Override
    public void onBackPressed(){
        SaveSettings();
        finish();
    }
}
