package com.marceljurtz.lifecounter.Counter;

import com.marceljurtz.lifecounter.Helper.BaseInterface.IView;
import com.marceljurtz.lifecounter.Helper.Counter;
import com.marceljurtz.lifecounter.Helper.Player;

public interface ICounterView extends IView {
    void SetPlayerLabelHeader(Player player, String headerText);
    void LoadPlayerDescriptionDialog(Player player);
    void LoadNewCounterDialog();
    void DisplayNewCounterEntryToPlayer(Player player, Counter counter);
}
