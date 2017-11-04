package com.marceljurtz.lifecounter.Counter;

import com.marceljurtz.lifecounter.Helper.Player;
import com.marceljurtz.lifecounter.Helper.BaseInterface.ICounterNavDrawerInteraction;
import com.marceljurtz.lifecounter.Helper.BaseInterface.IPresenter;

public interface ICounterPresenter extends IPresenter, ICounterNavDrawerInteraction {
    void OnPlayerIdentificationClick(Player player);
}
