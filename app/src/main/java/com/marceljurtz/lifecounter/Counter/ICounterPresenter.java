package com.marceljurtz.lifecounter.Counter;

import android.widget.LinearLayout;

import com.marceljurtz.lifecounter.Helper.Counter;
import com.marceljurtz.lifecounter.Helper.Player;
import com.marceljurtz.lifecounter.Helper.BaseInterface.ICounterNavDrawerInteraction;
import com.marceljurtz.lifecounter.Helper.BaseInterface.IPresenter;
import com.marceljurtz.lifecounter.Helper.PlayerID;

import java.util.List;

public interface ICounterPresenter extends IPresenter, ICounterNavDrawerInteraction {
    void onFloatingActionButtonTap();
    void addCounter(PlayerID playerId, Counter counter);

    void onCounterTap(String counterIdentifier);
    void onCounterLongTap(LinearLayout counterWrapper);
    void onCounterEditCompleted(PlayerID playerID, String oldCounterIdentifier, Counter newCounter);

    void onPlayerIdentificationTap(PlayerID playerID);
    void onPlayerIdentificationLongTap(PlayerID playerID);
    void onPlayerIdentificationChangeConfirmed(PlayerID playerId, String newIdentification);
    void onPlayerDeletionConfirmed(PlayerID playerID); // TODO presenter logic
    void onCounterDeletionConfirmed(LinearLayout counterLayout); // TODO presenter logic

    void onMenuEntryTwoPlayerClick();
    void onMenuEntryFourPlayerClick();
    void onMenuEntryDicingClick();
    void onMenuEntrySettingsClick();
    void onMenuEntryAboutClick();

    String getPlayerIdentification(PlayerID playerID);
}
