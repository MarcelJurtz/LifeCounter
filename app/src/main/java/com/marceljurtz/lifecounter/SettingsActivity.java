package com.marceljurtz.lifecounter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

public class SettingsActivity extends Activity {

    TextView txtBlack;
    TextView txtBlue;
    TextView txtGreen;
    TextView txtRed;
    TextView txtWhite;

    Button cmdSelectBlack;
    Button cmdSelectBlue;
    Button cmdSelectGreen;
    Button cmdSelectRed;
    Button cmdSelectWhite;

    Button cmdSaveChanges;
    Button cmdDiscardChanges;
    Button cmdReset;

    static int selectedBlack;
    static int selectedBlue;
    static int selectedGreen;
    static int selectedRed;
    static int selectedWhite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        txtBlack = (TextView) findViewById(R.id.txtColorBlack);
        txtBlue = (TextView) findViewById(R.id.txtColorBlue);
        txtGreen = (TextView) findViewById(R.id.txtColorGreen);
        txtRed = (TextView) findViewById(R.id.txtColorRed);
        txtWhite = (TextView) findViewById(R.id.txtColorWhite);

        selectedBlack = SettingsService.getColor(getApplicationContext(),getString(R.string.shared_preferences_color_black), ColorService.getDefaultBlack());
        selectedBlue = SettingsService.getColor(getApplicationContext(), getString(R.string.shared_preferences_color_blue), ColorService.getDefaultBlue());
        selectedGreen = SettingsService.getColor(getApplicationContext(),getString(R.string.shared_preferences_color_green), ColorService.getDefaultGreen());
        selectedRed = SettingsService.getColor(getApplicationContext(), getString(R.string.shared_preferences_color_red), ColorService.getDefaultRed());
        selectedWhite = SettingsService.getColor(getApplicationContext(), getString(R.string.shared_preferences_color_white), ColorService.getDefaultWhite());

        txtBlack.setText(ColorService.getHexString(selectedBlack));
        txtBlue.setText(ColorService.getHexString(selectedBlue));
        txtGreen.setText(ColorService.getHexString(selectedGreen));
        txtRed.setText(ColorService.getHexString(selectedRed));
        txtWhite.setText(ColorService.getHexString(selectedWhite));

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
        updateColor(cmdSelectGreen,txtGreen, selectedGreen);
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

        cmdSaveChanges = (Button) findViewById(R.id.cmdSaveChanges);
        cmdSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsService.saveColor(getApplicationContext(), getString(R.string.shared_preferences_color_black),selectedBlack);
                SettingsService.saveColor(getApplicationContext(), getString(R.string.shared_preferences_color_blue),selectedBlue);
                SettingsService.saveColor(getApplicationContext(), getString(R.string.shared_preferences_color_green),selectedGreen);
                SettingsService.saveColor(getApplicationContext(), getString(R.string.shared_preferences_color_red),selectedRed);
                SettingsService.saveColor(getApplicationContext(), getString(R.string.shared_preferences_color_white),selectedWhite);
                finish();
            }
        });

        cmdDiscardChanges = (Button) findViewById(R.id.cmdDiscardChanges);
        cmdDiscardChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cmdReset = (Button) findViewById(R.id.cmdResetSettings);
        cmdReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });

    }

    public void updateColor(Button button, TextView txt, int color) {
        ((GradientDrawable)button.getBackground()).setColor(color);
        txt.setText(ColorService.getHexString(color));
    }
}