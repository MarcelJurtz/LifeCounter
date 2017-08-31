package com.marceljurtz.lifecounter.Settings;

import com.marceljurtz.lifecounter.Helper.BaseInterface.IView;
import com.marceljurtz.lifecounter.Helper.Color;
import com.marceljurtz.lifecounter.Helper.MagicColor;

public interface ISettingsView extends IView {
    // Get customized settings
    Color getSelectedColor(MagicColor magicColor);
    int getSelectedLifepoints();
    int getSelectedLongClickPoints();

    // Launch Game Activity
    void loadGameActivity();

    void loadColorPickerDialog(MagicColor color, int r, int g, int b);
    void updateColorButtonValue(Color color);
}
