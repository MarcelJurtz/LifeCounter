package com.marceljurtz.lifecounter.views.Game;

import android.content.Context;

import com.marceljurtz.lifecounter.contracts.base.IView;
import com.marceljurtz.lifecounter.enums.MagicColorEnum;
import com.marceljurtz.lifecounter.enums.PlayerIdEnum;

public interface IGameView extends IView {
    // Set color of button, identified by original magic color
    void initColorButton(MagicColorEnum colorLocation, int color);

    // Set players background color TODO Validate possible duplicate
    void setLayoutColor(PlayerIdEnum playerIdEnum, int color);

    // Disable Screen timeout
    void disableScreenTimeout();
    void enableScreenTimeout();

    // Set life- / poisonpoints
    void setLifepoints(PlayerIdEnum id, String points);
    void setPoisonpoints(PlayerIdEnum id, String points);

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

    String getVersionName();

    void runFirstLaunchDialog();
    void runUpdateDialog();
}
