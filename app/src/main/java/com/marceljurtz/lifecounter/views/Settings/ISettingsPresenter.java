package com.marceljurtz.lifecounter.views.Settings;

import com.marceljurtz.lifecounter.contracts.base.IPresenter;
import com.marceljurtz.lifecounter.models.Color;
import com.marceljurtz.lifecounter.enums.MagicColorEnum;

public interface ISettingsPresenter extends IPresenter {
    void onBackButtonClick();
    void onCancelButtonClick();
    void onResetButtonClick();
    void onColorSelectButtonClick(MagicColorEnum color);
    void onColorSelectValueUpdate(Color color);
    void onKeepScreenOnCheckboxClick(boolean checked);

    void onResetButtonConfirm();
    void onResetButtonCancel();

    void onMenuEntryTwoPlayerTap();
    void onMenuEntryFourPlayerTap();
    void onMenuEntryCounterManagerTap();
    void onMenuEntryDicingTap();
    void onMenuEntryAboutTap();
}
