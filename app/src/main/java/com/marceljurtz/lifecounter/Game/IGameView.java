package com.marceljurtz.lifecounter.Game;

import com.marceljurtz.lifecounter.Helper.BaseInterface.IView;
import com.marceljurtz.lifecounter.Helper.MagicColor;
import com.marceljurtz.lifecounter.Helper.PlayerID;

public interface IGameView extends IView {
    // Set color of button, identified by original magic color
    void initColorButton(MagicColor colorLocation, int color);

    // Set players background color TODO Validate possible duplicate
    void setLayoutColor(PlayerID playerID, int color);

    // Disable Screen timeout
    void disableScreenTimeout();
    void enableScreenTimeout();

    // Set life- / poisonpoints
    void setLifepoints(PlayerID id, String points);
    void setPoisonpoints(PlayerID id, String points);

    // Enable / Disable energy saving mode
    void enableEnergySaving(int powerSaveColor, int powerSaveTextColor);
    void disableEnergySaving(int defaultBlack, int regularTextColor);

    // Launch settings activity
    void loadActivity(Class c);

    // Enable / Disable color controls
    void settingsButtonEnable();
    void enableSettingsControls(boolean hideLifecountControls, boolean hidePoisonControls);
    void settingsButtonDisable();
    void disableSettingsControls(boolean showOtherControls, boolean showPoisonControls);

    // Enable / Disable poison controls
    void enablePoisonControls(boolean rearrangeLifepoints);
    void poisonButtonEnable();
    void disablePoisonControls(boolean rearrangeLifepoints);
    void poisonButtonDisable();

    // Drawer Layout Interaction
    void setDrawerTextPowerSaving(boolean shouldBeEnabled);
    void hideNavigationDrawer();

    int getPlayerAmount();

    void restartActivity();

    int getScreenSize();
}
