package com.marceljurtz.lifecounter.Counter;

import android.widget.LinearLayout;

import com.marceljurtz.lifecounter.Helper.BaseInterface.IView;
import com.marceljurtz.lifecounter.Helper.Color;
import com.marceljurtz.lifecounter.Helper.Counter;
import com.marceljurtz.lifecounter.Helper.Player;
import com.marceljurtz.lifecounter.Helper.PlayerID;

import java.util.ArrayList;

public interface ICounterView extends IView {
    void setBackgroundColor(Color color);
    void setPlayerIdentificationText(PlayerID playerId, String headerText);
    void addCounter(PlayerID playerId, Counter counter);

    void loadGameActivity();
    void loadDicingActivity();
    void loadSettingsActivity();
    void loadAboutActivity();

    void deleteCounter(LinearLayout counterLayout, boolean deleteParent);
    void deleteAllCounters();
    void deleteAllCountersForPlayer(PlayerID playerID);

    void updateCounterView(Player player, Counter counter);

    void loadPlayerIdentificationDialog(PlayerID playerID, String playername);
    void loadPlayerDeletionDialog(PlayerID playerID);
    void loadCounterAddDialog(ArrayList<Player> players);
    void loadCounterEditDialog(Player player, Counter counter);
    void loadCounterDeletionDialog(LinearLayout linearLayout);
}