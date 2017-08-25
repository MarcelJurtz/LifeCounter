package com.marceljurtz.lifecounter.Game;

import com.marceljurtz.lifecounter.Helper.ClickType;
import com.marceljurtz.lifecounter.Helper.MagicColor;
import com.marceljurtz.lifecounter.Helper.PlayerID;

public interface IPresenter {
    void onCreate(IView view);
    void onPause();
    void onResume();
    void onDestroy();

    // Update life- and poisonpoints
    // Use ClickType to differentiate between regular and long clicks
    void onLifeUpdate(Player player, ClickType clickType);
    void onPoisonUpdate(Player player, ClickType clickType);

    void colorButtonClick(PlayerID playerID, MagicColor color, ClickType clickType);
    void poisonButtonClick();
    void settingsButtonClick(ClickType clickType);
    void resetButtonClick();
}
