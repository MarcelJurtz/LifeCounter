package com.marceljurtz.lifecounter.Counter;

import com.marceljurtz.lifecounter.Helper.BaseInterface.IView;
import com.marceljurtz.lifecounter.Helper.Counter;
import com.marceljurtz.lifecounter.Helper.Player;
import com.marceljurtz.lifecounter.Helper.PlayerID;

import java.util.ArrayList;

public interface ICounterView extends IView {
    void SetPlayerIdentificationText(PlayerID playerId, String headerText);
    void DisplayNewCounterEntryToPlayer(PlayerID playerId, Counter counter);

    void LoadGameActivity();
    void LoadDicingActivity();
    void LoadSettingsActivity();
    void LoadAboutActivity();

    void LoadCounterDeletionDialog();
    void LoadCounterAddDialog(ArrayList<Player> players);
    void LoadPlayerIdentificationDialog(PlayerID playerID, String playername);
    void DeleteAllCounters();
}