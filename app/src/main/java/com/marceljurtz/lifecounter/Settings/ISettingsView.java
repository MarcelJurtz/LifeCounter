package com.marceljurtz.lifecounter.Settings;

import com.marceljurtz.lifecounter.Helper.BaseInterface.IView;
import com.marceljurtz.lifecounter.Helper.Color;
import com.marceljurtz.lifecounter.Helper.MagicColor;

public interface ISettingsView extends IView {
    // Lifepoints and Longclickpoints Getter & Setter
    void setLifepoints(String lifepointText);
    String getLifepoints();
    void setLongClickPoints(String longClickPointText);
    String getLongClickPoints();

    // Launch Game Activity
    void loadGameActivity();

    void loadResetConfirmationDialog();
    void loadColorPickerDialog(MagicColor color, int r, int g, int b);
    void updateColorButtonValue(Color color);
}
