package com.marceljurtz.lifecounter.Settings;

import com.marceljurtz.lifecounter.Helper.BaseInterface.IPresenter;
import com.marceljurtz.lifecounter.Helper.Color;
import com.marceljurtz.lifecounter.Helper.MagicColor;

public interface ISettingsPresenter extends IPresenter {
    void onBackButtonClick();
    void onCancelButtonClick();
    void onResetButtonClick();
    void onColorSelectButtonClick(MagicColor color);
    void onColorSelectValueUpdate(Color color);
}
