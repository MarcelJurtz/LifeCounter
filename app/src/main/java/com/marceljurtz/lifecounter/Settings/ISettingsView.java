package com.marceljurtz.lifecounter.Settings;

import com.marceljurtz.lifecounter.Helper.BaseInterface.IView;
import com.marceljurtz.lifecounter.Helper.MagicColor;

public interface ISettingsView extends IView {
    // Get customized settings
    int getSelectedColor(MagicColor magicColor);
    int getSelectedLifepoints();
    int getSelectedLongClickPoints();

    // Launch Game Activity
    void loadSettingsActivity();
}
