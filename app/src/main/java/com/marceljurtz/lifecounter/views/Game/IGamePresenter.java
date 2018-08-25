package com.marceljurtz.lifecounter.views.Game;

import com.marceljurtz.lifecounter.contracts.IGameNavDrawerInteraction;
import com.marceljurtz.lifecounter.contracts.base.IPresenter;
import com.marceljurtz.lifecounter.enums.ClickTypeEnum;
import com.marceljurtz.lifecounter.enums.MagicColorEnum;
import com.marceljurtz.lifecounter.enums.OperatorEnum;
import com.marceljurtz.lifecounter.enums.PlayerIdEnum;

public interface IGamePresenter extends IPresenter, IGameNavDrawerInteraction {
    // Updating players life- and poisonpoints
    void onLifeUpdate(PlayerIdEnum playerIdEnum, ClickTypeEnum clickTypeEnum, OperatorEnum operatorEnum);
    void onPoisonUpdate(PlayerIdEnum playerIdEnum, ClickTypeEnum clickTypeEnum, OperatorEnum operatorEnum);

    // Change player background color
    void onColorButtonClick(PlayerIdEnum playerIdEnum, MagicColorEnum color, ClickTypeEnum clickTypeEnum);

    // Enable / Disable poison controls
    void onPoisonButtonClick();

    // Enable / Disable color controls
    void onSettingsButtonClick(ClickTypeEnum clickTypeEnum);

    // Hit reset button
    void onResetButtonClick();
}