package com.marceljurtz.lifecounter.Game;

import com.marceljurtz.lifecounter.Helper.BaseInterface.IGameNavDrawerInteraction;
import com.marceljurtz.lifecounter.Helper.BaseInterface.IPresenter;
import com.marceljurtz.lifecounter.Helper.ClickType;
import com.marceljurtz.lifecounter.Helper.MagicColor;
import com.marceljurtz.lifecounter.Helper.Operator;
import com.marceljurtz.lifecounter.Helper.PlayerID;

public interface IGamePresenter extends IPresenter, IGameNavDrawerInteraction {
    // Updating players life- and poisonpoints
    void OnLifeUpdate(PlayerID playerID, ClickType clickType, Operator operator);
    void OnPoisonUpdate(PlayerID playerID, ClickType clickType, Operator operator);

    // Change player background color
    void OnColorButtonClick(PlayerID playerID, MagicColor color, ClickType clickType);

    // Enable / Disable poison controls
    void OnPoisonButtonClick();

    // Enable / Disable color controls
    void OnSettingsButtonClick(ClickType clickType);

    // Hit reset button
    void OnResetButtonClick();
}