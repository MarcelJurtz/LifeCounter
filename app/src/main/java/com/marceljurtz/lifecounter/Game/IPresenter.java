package com.marceljurtz.lifecounter.Game;

import com.marceljurtz.lifecounter.Helper.ClickType;

public interface IPresenter {
    void onCreate(IView view);
    void onPause();
    void onResume();
    void onDestroy();

    // Update life- and poisonpoints
    // Use ClickType to differentiate between regular and long clicks
    void onLifeUpdate(Player player, ClickType clickType);
    void onPoisonUpdate(Player player, ClickType clickType);
}
