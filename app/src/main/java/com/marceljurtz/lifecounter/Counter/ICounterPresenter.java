package com.marceljurtz.lifecounter.Counter;

import android.widget.LinearLayout;

import com.marceljurtz.lifecounter.Helper.Counter;
import com.marceljurtz.lifecounter.Helper.Player;
import com.marceljurtz.lifecounter.Helper.BaseInterface.ICounterNavDrawerInteraction;
import com.marceljurtz.lifecounter.Helper.BaseInterface.IPresenter;
import com.marceljurtz.lifecounter.Helper.PlayerID;

import java.util.List;

public interface ICounterPresenter extends IPresenter, ICounterNavDrawerInteraction {
    void OnFloatingActionButtonTap();
    void AddCounter(PlayerID playerId, Counter counter);

    void OnCounterTap(String counterIdentifier);
    void OnCounterLongTap(LinearLayout counterWrapper);
    void OnCounterEditCompleted(PlayerID playerID, String oldCounterIdentifier, Counter newCounter);

    void OnPlayerIdentificationTap(PlayerID playerID);
    void OnPlayerIdentificationLongTap(PlayerID playerID);
    void OnPlayerIdentificationChangeConfirmed(PlayerID playerId, String newIdentification);
    void OnPlayerDeletionConfirmed(PlayerID playerID); // TODO presenter logic
    void OnCounterDeletionConfirmed(LinearLayout counterLayout); // TODO presenter logic

    void OnMenuEntryTwoPlayerClick();
    void OnMenuEntryFourPlayerClick();
    void OnMenuEntryDicingClick();
    void OnMenuEntrySettingsClick();
    void OnMenuEntryAboutClick();

    String GetPlayerIdentification(PlayerID playerID);
}
