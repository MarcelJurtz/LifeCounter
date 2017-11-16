package com.marceljurtz.lifecounter.Counter;

import com.marceljurtz.lifecounter.Helper.BaseInterface.IView;
import com.marceljurtz.lifecounter.Helper.Counter;
import com.marceljurtz.lifecounter.Helper.Player;

public interface ICounterView extends IView {
    void SetPlayerIdentificationText(Player player, String headerText);
    void DisplayNewCounterEntryToPlayer(Player player, Counter counter);

    void LoadGameActivity();
    void LoadDicingActivity();
    void LoadSettingsActivity();
    void LoadAboutActivity();

    void LoadCounterDeletionDialog();
    void LoadCounterAddDialog();
    void LoadPlayerIdentificationDialog(Player player);

    void GoBackToPreviousActivity();
}
