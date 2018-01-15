package com.marceljurtz.lifecounter.Counter;

import android.widget.LinearLayout;

import com.marceljurtz.lifecounter.Helper.BaseInterface.IView;
import com.marceljurtz.lifecounter.Helper.Counter;
import com.marceljurtz.lifecounter.Helper.Player;
import com.marceljurtz.lifecounter.Helper.PlayerID;

import java.util.ArrayList;

public interface ICounterView extends IView {

    void SetPlayerIdentificationText(PlayerID playerId, String headerText);
    void AddCounterToPlayer(PlayerID playerId, Counter counter);

    void LoadGameActivity();
    void LoadDicingActivity();
    void LoadSettingsActivity();
    void LoadAboutActivity();

    void DeleteCounter(LinearLayout counterLayout, boolean deleteParent);
    void DeleteAllCounters();
    void DeleteAllCountersForPlayer(PlayerID playerID);

    void UpdateCounterView(Player player, Counter counter);

    void LoadCounterDeletionDialog(LinearLayout linearLayout);
    void LoadMultipleCounterDeletionDialog(PlayerID playerID);
    void LoadCounterAddDialog(ArrayList<Player> players);
    void LoadCounterEditDialog(Player player, Counter counter);
    void LoadPlayerIdentificationDialog(PlayerID playerID, String playername);
}
