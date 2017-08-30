package com.marceljurtz.lifecounter.Settings;

import com.marceljurtz.lifecounter.Helper.BaseInterface.IPresenter;

public interface ISettingsPresenter extends IPresenter {
    void onBackButtonClick();
    void onCancelButtonClick();
    void onResetButtonClick();
}
