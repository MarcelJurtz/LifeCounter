package com.marceljurtz.lifecounter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

public class SettingsActivity extends Activity {

    EditText txtBlack;
    EditText txtBlue;
    EditText txtGreen;
    EditText txtRed;
    EditText txtWhite;

    Button cmdSelectBlack;
    Button cmdSelectBlue;
    Button cmdSelectGreen;
    Button cmdSelectRed;
    Button cmdSelectWhite;

    Button cmdSaveChanges;
    Button cmdDiscardChanges;

    static int selectedBlack;
    static int selectedBlue;
    static int selectedGreen;
    static int selectedRed;
    static int selectedWhite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        txtBlack = (EditText) findViewById(R.id.txtColorBlack);
        txtBlue = (EditText) findViewById(R.id.txtColorBlue);
        txtGreen = (EditText) findViewById(R.id.txtColorGreen);
        txtRed = (EditText) findViewById(R.id.txtColorRed);
        txtWhite = (EditText) findViewById(R.id.txtColorWhite);

        selectedBlack = SettingsService.getColor(getApplicationContext(),getString(R.string.shared_preferences_color_black), ColorService.getDefaultBlack());
        selectedBlue = SettingsService.getColor(getApplicationContext(), getString(R.string.shared_preferences_color_blue), ColorService.getDefaultBlue());
        selectedGreen = SettingsService.getColor(getApplicationContext(),getString(R.string.shared_preferences_color_green), ColorService.getDefaultGreen());
        selectedRed = SettingsService.getColor(getApplicationContext(), getString(R.string.shared_preferences_color_red), ColorService.getDefaultRed());
        selectedWhite = SettingsService.getColor(getApplicationContext(), getString(R.string.shared_preferences_color_white), ColorService.getDefaultWhite());

        txtBlack.setText(String.format("#%06X", 0xFFFFFF & selectedBlack));
        txtBlue.setText(String.format("#%06X", 0xFFFFFF & selectedBlue));
        txtGreen.setText(String.format("#%06X", 0xFFFFFF & selectedGreen));
        txtRed.setText(String.format("#%06X", 0xFFFFFF & selectedRed));
        txtWhite.setText(String.format("#%06X", 0xFFFFFF & selectedWhite));

        cmdSelectBlack = (Button) findViewById(R.id.cmdSelectBlack);
        updateColor(cmdSelectBlack, selectedBlack);
        cmdSelectBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int r = getRGB(selectedBlack)[0];
                int g = getRGB(selectedBlack)[1];
                int b = getRGB(selectedBlack)[2];

                final ColorPicker cp = new ColorPicker(SettingsActivity.this, r, g, b);

                cp.show();

                Button okColor = (Button) cp.findViewById(R.id.okColorButton);
                okColor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedBlack = cp.getColor();
                        updateColor(cmdSelectBlack, selectedBlack);
                        cp.dismiss();
                    }
                });
            }
        });

        cmdSelectBlue = (Button) findViewById(R.id.cmdSelectBlue);
        updateColor(cmdSelectBlue,selectedBlue);
        cmdSelectBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int r = getRGB(selectedBlue)[0];
                int g = getRGB(selectedBlue)[1];
                int b = getRGB(selectedBlue)[2];

                final ColorPicker cp = new ColorPicker(SettingsActivity.this, r, g, b);

                cp.show();

                Button okColor = (Button) cp.findViewById(R.id.okColorButton);
                okColor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedBlue = cp.getColor();
                        updateColor(cmdSelectBlue, selectedBlue);
                        cp.dismiss();
                    }
                });
            }
        });

        cmdSelectGreen = (Button) findViewById(R.id.cmdSelectGreen);
        updateColor(cmdSelectGreen,selectedGreen);
        cmdSelectGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int r = getRGB(selectedGreen)[0];
                int g = getRGB(selectedGreen)[1];
                int b = getRGB(selectedGreen)[2];

                final ColorPicker cp = new ColorPicker(SettingsActivity.this, r, g, b);

                cp.show();

                Button okColor = (Button) cp.findViewById(R.id.okColorButton);
                okColor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedGreen = cp.getColor();
                        updateColor(cmdSelectGreen, selectedGreen);
                        cp.dismiss();
                    }
                });
            }
        });

        cmdSelectRed = (Button) findViewById(R.id.cmdSelectRed);
        updateColor(cmdSelectRed,selectedRed);
        cmdSelectRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int r = getRGB(selectedRed)[0];
                int g = getRGB(selectedRed)[1];
                int b = getRGB(selectedRed)[2];

                final ColorPicker cp = new ColorPicker(SettingsActivity.this, r, g, b);

                cp.show();

                Button okColor = (Button) cp.findViewById(R.id.okColorButton);
                okColor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedRed = cp.getColor();
                        updateColor(cmdSelectRed, selectedRed);
                        cp.dismiss();
                    }
                });
            }
        });

        cmdSelectWhite = (Button) findViewById(R.id.cmdSelectWhite);
        updateColor(cmdSelectWhite,selectedWhite);
        cmdSelectWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int r = getRGB(selectedWhite)[0];
                int g = getRGB(selectedWhite)[1];
                int b = getRGB(selectedWhite)[2];

                final ColorPicker cp = new ColorPicker(SettingsActivity.this, r, g, b);

                cp.show();

                Button okColor = (Button) cp.findViewById(R.id.okColorButton);
                okColor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedWhite = cp.getColor();
                        updateColor(cmdSelectWhite, selectedWhite);
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

    }

    public void updateColor(Button button, int color) {
        ((GradientDrawable)button.getBackground()).setColor(color);
    }

    public static int[] getRGB(int color) {
        int[] rgb = new int[3];
        rgb[0] = (color >> 16) & 0xFF;
        rgb[1] = (color >> 8) & 0xFF;
        rgb[2] = (color >> 0) & 0xFF;
        return rgb;
    }
}
