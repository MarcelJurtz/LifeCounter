package com.marceljurtz.lifecounter.views.Settings;

import com.marceljurtz.lifecounter.contracts.base.IView;
import com.marceljurtz.lifecounter.models.Color;
import com.marceljurtz.lifecounter.enums.MagicColorEnum;

public interface ISettingsView extends IView {
    // Lifepoints and Longclickpoints Getter & Setter
    void setLifepoints(String lifepointText);
    String getLifepoints();
    void setLongClickPoints(String longClickPointText);
    String getLongClickPoints();

    void returnToPrevActivity();
    void loadActivity(Class c);

    void loadResetConfirmationDialog();
    void loadColorPickerDialog(MagicColorEnum color, int r, int g, int b);
    void updateColorButtonValue(Color color);

    void setKeepScreenOnCheckbox(boolean checked);
    void setConfirmGameResetCheckbox(boolean checked);
}
