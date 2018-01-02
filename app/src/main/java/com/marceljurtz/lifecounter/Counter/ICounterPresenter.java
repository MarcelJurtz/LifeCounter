package com.marceljurtz.lifecounter.Counter;

import com.marceljurtz.lifecounter.Helper.Counter;
import com.marceljurtz.lifecounter.Helper.Player;
import com.marceljurtz.lifecounter.Helper.BaseInterface.ICounterNavDrawerInteraction;
import com.marceljurtz.lifecounter.Helper.BaseInterface.IPresenter;
import com.marceljurtz.lifecounter.Helper.PlayerID;

import java.util.List;

public interface ICounterPresenter extends IPresenter, ICounterNavDrawerInteraction {
    void OnCreateNewCounterButtonTap();
    void AddCounterToPlayer(PlayerID playerId, Counter counter);

    void OnPlayerIdentificationTap(PlayerID playerID);
    void OnPlayerIdentificationChanged(PlayerID playerId, String newIdentification);

    void OnMenuEntryTwoPlayerClick();
    void OnMenuEntryFourPlayerClick();
    void OnMenuEntryDicingClick();
    void OnMenuEntrySettingsClick();
    void OnMenuEntryAboutClick();

    String GetPlayerIdentification(PlayerID playerID);
}