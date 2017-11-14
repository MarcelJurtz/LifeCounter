package com.marceljurtz.lifecounter.Settings;

import com.marceljurtz.lifecounter.Helper.BaseInterface.IPresenter;
import com.marceljurtz.lifecounter.Helper.Color;
import com.marceljurtz.lifecounter.Helper.MagicColor;

public interface ISettingsPresenter extends IPresenter {
    void OnBackButtonClick();
    void OnCancelButtonClick();
    void OnResetButtonClick();
    void OnColorSelectButtonClick(MagicColor color);
    void OnColorSelectValueUpdate(Color color);
    void OnKeepScreenOnCheckboxClick(boolean checked);

    void OnResetButtonConfirm();
    void OnResetButtonCancel();
}
