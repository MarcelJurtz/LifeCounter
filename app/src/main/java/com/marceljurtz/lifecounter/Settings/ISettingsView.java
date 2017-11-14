package com.marceljurtz.lifecounter.Settings;

import com.marceljurtz.lifecounter.Helper.BaseInterface.IView;
import com.marceljurtz.lifecounter.Helper.Color;
import com.marceljurtz.lifecounter.Helper.MagicColor;

public interface ISettingsView extends IView {
    // Lifepoints and Longclickpoints Getter & Setter
    void SetLifepoints(String lifepointText);
    String GetLifepoints();
    void SetLongClickPoints(String longClickPointText);
    String GetLongClickPoints();

    // Launch Game Activity
    void LoadGameActivity();

    void LoadResetConfirmationDialog();
    void LoadColorPickerDialog(MagicColor color, int r, int g, int b);
    void UpdateColorButtonValue(Color color);

    void SetKeepScreenOnCheckbox(boolean checked);
}
