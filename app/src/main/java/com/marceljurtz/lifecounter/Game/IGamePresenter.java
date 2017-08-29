package com.marceljurtz.lifecounter.Game;

import com.marceljurtz.lifecounter.Helper.BaseInterface.IPresenter;
import com.marceljurtz.lifecounter.Helper.ClickType;
import com.marceljurtz.lifecounter.Helper.MagicColor;
import com.marceljurtz.lifecounter.Helper.Operator;
import com.marceljurtz.lifecounter.Helper.PlayerID;

public interface IGamePresenter extends IPresenter {
    // Updating players life- and poisonpoints
    void onLifeUpdate(PlayerID playerID, ClickType clickType, Operator operator);
    void onPoisonUpdate(PlayerID playerID, ClickType clickType, Operator operator);

    // Change player background color
    void colorButtonClick(PlayerID playerID, MagicColor color, ClickType clickType);

    // Enable / Disable poison controls
    void poisonButtonClick();

    // Enable / Disable color controls
    void settingsButtonClick(ClickType clickType);

    // Hit reset button
    void resetButtonClick();
}
