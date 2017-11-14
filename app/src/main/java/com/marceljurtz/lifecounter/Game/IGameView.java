package com.marceljurtz.lifecounter.Game;

import com.marceljurtz.lifecounter.Helper.BaseInterface.IView;
import com.marceljurtz.lifecounter.Helper.MagicColor;
import com.marceljurtz.lifecounter.Helper.PlayerID;

public interface IGameView extends IView {
    // Set color of button, identified by original magic color
    void InitColorButton(MagicColor colorLocation, int color);

    // Set players background color TODO Validate possible duplicate
    void SetLayoutColor(PlayerID playerID, int color);

    // Disable Screen timeout
    void DisableScreenTimeout();
    void EnableScreenTimeout();

    // Set life- / poisonpoints
    void SetLifepoints(PlayerID id, String points);
    void SetPoisonpoints(PlayerID id, String points);

    // Enable / Disable energy saving mode
    void EnableEnergySaving(int powerSaveColor, int powerSaveTextColor);
    void DisableEnergySaving(int defaultBlack, int regularTextColor);

    // Launch settings activity
    void LoadSettingsActivity();
    void LoadDicingActivity();
    void LoadAboutActivity();
    void LoadCounterManagerActivity();

    // Enable / Disable color controls
    void SettingsButtonEnable();
    void EnableSettingsControls(boolean hideLifecountControls, boolean hidePoisonControls);
    void SettingsButtonDisable();
    void DisableSettingsControls(boolean showOtherControls, boolean showPoisonControls);

    // Enable / Disable poison controls
    void EnablePoisonControls(boolean rearrangeLifepoints);
    void PoisonButtonEnable();
    void DisablePoisonControls(boolean rearrangeLifepoints);
    void PoisonButtonDisable();

    // Drawer Layout Interaction
    void SetDrawerTextPowerSaving(boolean shouldBeEnabled);
    void HideNavigationDrawer();

    int GetPlayerAmount();

    void RestartActivity();

    int GetScreenSize();
}
