package com.marceljurtz.lifecounter.Settings;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.marceljurtz.lifecounter.Helper.Color;
import com.marceljurtz.lifecounter.Helper.MagicColor;
import com.marceljurtz.lifecounter.Helper.PreferenceManager;
import com.marceljurtz.lifecounter.R;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

public class SettingsActivity extends Activity implements ISettingsView {

    //region View declarations

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

    Switch chkKeepScreenOn;

    //endregion

    SharedPreferences preferences;
    ISettingsPresenter presenter;

    //region Activity Lifecycle Functions

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        preferences = getApplicationContext().getSharedPreferences(PreferenceManager.PREFS, Activity.MODE_PRIVATE);

        //region View Initialization
        txtBlack = (TextView) findViewById(R.id.txtColorBlack);
        txtBlue = (TextView) findViewById(R.id.txtColorBlue);
        txtGreen = (TextView) findViewById(R.id.txtColorGreen);
        txtRed = (TextView) findViewById(R.id.txtColorRed);
        txtWhite = (TextView) findViewById(R.id.txtColorWhite);

        txtLifepoints = (EditText) findViewById(R.id.txtLiveSelection);
        txtLongClickPoints = (EditText) findViewById(R.id.txtLongClickPoints);

        /*
        chkKeepScreenOn = (CheckBox)findViewById(R.id.chkKeepScreenOn);
        chkKeepScreenOn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                presenter.OnKeepScreenOnCheckboxClick(isChecked);
            }
        });
        */
        chkKeepScreenOn = (Switch) findViewById(R.id.chkKeepScreenOn);
        chkKeepScreenOn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                presenter.OnKeepScreenOnCheckboxClick(isChecked);
            }
        });
        //endregion

        //region Color Selection Buttons
        cmdSelectBlack = (Button) findViewById(R.id.cmdSelectBlack);
        cmdSelectBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.OnColorSelectButtonClick(MagicColor.BLACK);
            }
        });

        cmdSelectBlue = (Button) findViewById(R.id.cmdSelectBlue);
        cmdSelectBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.OnColorSelectButtonClick(MagicColor.BLUE);
            }
        });

        cmdSelectGreen = (Button) findViewById(R.id.cmdSelectGreen);
        cmdSelectGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.OnColorSelectButtonClick(MagicColor.GREEN);
            }
        });

        cmdSelectRed = (Button) findViewById(R.id.cmdSelectRed);
        cmdSelectRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.OnColorSelectButtonClick(MagicColor.RED);
            }
        });

        cmdSelectWhite = (Button) findViewById(R.id.cmdSelectWhite);
        cmdSelectWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.OnColorSelectButtonClick(MagicColor.WHITE);
            }
        });
        //endregion

        //region Cancel Button Click

        /*
        cmdDiscardChanges = (Button) findViewById(R.id.cmdDiscardChanges);
        cmdDiscardChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.OnCancelButtonClick();
            }
        });
        */

        //endregion

        //region Reset Button Click

        cmdReset = (Button) findViewById(R.id.cmdResetSettings);
        cmdReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.OnResetButtonClick();
            }
        });

        //endregion

        //region Back Button Click

        cmdBack = (ImageButton)findViewById(R.id.cmdBack);
        cmdBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //endregion

        presenter = new SettingsPresenter(this, preferences);
        presenter.OnCreate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.OnResume();
    }

    @Override
    protected void onPause() {
        presenter.OnPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        presenter.OnDestroy();
        super.onDestroy();
    }

    //endregion

    //region Get EditText and color values

    @Override
    public String GetLifepoints() {
        return txtLifepoints.getText().toString();
    }

    @Override
    public String GetLongClickPoints() {
        return txtLongClickPoints.getText().toString();
    }

    //endregion

    //region Set EditText values

    @Override
    public void SetLifepoints(String lifepointText) {
        txtLifepoints.setText(lifepointText);
    }

    @Override
    public void SetLongClickPoints(String longClickPointText) {
        txtLongClickPoints.setText(longClickPointText);
    }

    //endregion

    @Override
    public void onBackPressed(){
        presenter.OnBackButtonClick();
    }

    @Override
    public void LoadGameActivity() {
        finish();
    }

    @Override
    public void LoadColorPickerDialog(MagicColor color, int r, int g, int b) {
        final ColorPicker cp = new ColorPicker(SettingsActivity.this, r, g, b);
        final MagicColor baseColor = color;

        cp.show();

        Button okColor = (Button) cp.findViewById(R.id.okColorButton);
        okColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newColor = cp.getColor();
                //updateColor(cmdSelectWhite, txtWhite, selectedWhite);
                presenter.OnColorSelectValueUpdate(new Color(baseColor, newColor));
                cp.dismiss();
            }
        });
    }

    @Override
    public void UpdateColorButtonValue(Color color) {
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
    public void LoadResetConfirmationDialog() {
        new AlertDialog.Builder(SettingsActivity.this)
                .setMessage(R.string.settings_confirm_reset)
                .setCancelable(false)
                .setPositiveButton(R.string.settings_confirm_reset_true, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        presenter.OnResetButtonConfirm();
                    }
                })
                .setNegativeButton(R.string.settings_confirm_reset_false, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.OnResetButtonCancel();
                    }
                })
                .show();
    }

    @Override
    public void SetKeepScreenOnCheckbox(boolean checked) {
        chkKeepScreenOn.setChecked(checked);
    }
}
