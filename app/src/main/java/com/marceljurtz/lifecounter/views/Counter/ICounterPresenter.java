package com.marceljurtz.lifecounter.views.Counter;

import android.widget.LinearLayout;

import com.marceljurtz.lifecounter.models.Counter;
import com.marceljurtz.lifecounter.contracts.ICounterNavDrawerInteraction;
import com.marceljurtz.lifecounter.contracts.base.IPresenter;
import com.marceljurtz.lifecounter.enums.PlayerIdEnum;

public interface ICounterPresenter extends IPresenter, ICounterNavDrawerInteraction {
    void onFloatingActionButtonTap();
    void addCounter(PlayerIdEnum playerIdEnum, Counter counter);

    void onCounterTap(LinearLayout counterWrapper);
    void onCounterLongTap(LinearLayout counterWrapper);
    void onCounterEditCompleted(PlayerIdEnum playerIdEnum, String oldCounterIdentifier, Counter newCounter);

    void onPlayerIdentificationTap(PlayerIdEnum playerIdEnum);
    void onPlayerIdentificationLongTap(PlayerIdEnum playerIdEnum);
    void onPlayerIdentificationChangeConfirmed(PlayerIdEnum playerIdEnum, String newIdentification);
    void onPlayerDeletionConfirmed(PlayerIdEnum playerIdEnum); // TODO presenter logic
    void onCounterDeletionConfirmed(LinearLayout counterLayout); // TODO presenter logic

    void onMenuEntryTwoPlayerClick();
    void onMenuEntryFourPlayerClick();
    void onMenuEntryDicingClick();
    void onMenuEntrySettingsClick();
    void onMenuEntryAboutClick();

    String getPlayerIdentification(PlayerIdEnum playerIdEnum);
}
