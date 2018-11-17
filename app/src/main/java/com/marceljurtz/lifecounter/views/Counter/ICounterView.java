package com.marceljurtz.lifecounter.views.Counter;

import android.widget.LinearLayout;

import com.marceljurtz.lifecounter.contracts.base.IView;
import com.marceljurtz.lifecounter.models.Color;
import com.marceljurtz.lifecounter.models.Counter;
import com.marceljurtz.lifecounter.models.Player;
import com.marceljurtz.lifecounter.enums.PlayerIdEnum;

import java.util.ArrayList;

public interface ICounterView extends IView {
    void setBackgroundColor(Color color);
    void setPlayerIdentificationText(PlayerIdEnum playerIdEnum, String headerText);
    void addCounter(PlayerIdEnum playerIdEnum, Counter counter);

    void deleteCounter(LinearLayout counterLayout, boolean deleteParent);
    void deleteAllCounters();
    void deleteAllCountersForPlayer(PlayerIdEnum playerIdEnum);

    void updateCounterView(Player player, Counter counter);

    void loadPlayerIdentificationDialog(PlayerIdEnum playerIdEnum, String playername);
    void loadPlayerDeletionDialog(PlayerIdEnum playerIdEnum);
    void loadCounterAddDialog(ArrayList<Player> players);
    void loadCounterEditDialog(LinearLayout counterLayout, Player player, Counter counter);
    void loadCounterDeletionDialog(LinearLayout linearLayout);
}