package com.marceljurtz.lifecounter.Counter;

import com.marceljurtz.lifecounter.Helper.Counter;
import com.marceljurtz.lifecounter.Helper.Player;
import com.marceljurtz.lifecounter.Helper.BaseInterface.ICounterNavDrawerInteraction;
import com.marceljurtz.lifecounter.Helper.BaseInterface.IPresenter;

import java.util.List;

public interface ICounterPresenter extends IPresenter, ICounterNavDrawerInteraction {
    void OnCreateNewCounterButtonTap();
    void AddCounterToPlayer(Player player, Counter counter);
    void OnPlayerIdentificationChanged(Player player, String newIdentification);

    void OnMenuEntryTwoPlayerClick();
    void OnMenuEntryFourPlayerClick();
    void OnMenuEntryDicingClick();
    void OnMenuEntrySettingsClick();
    void OnMenuEntryAboutClick();
}
