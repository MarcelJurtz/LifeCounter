package com.marceljurtz.lifecounter.Dicing;

import com.marceljurtz.lifecounter.Helper.BaseInterface.IPresenter;

public interface IDicingPresenter extends IPresenter {
    void onScreenTap();

    void onMenuEntry2PlayerTap();
    void onMenuEntry4PlayerTap();
    void onMenuEntrySettingsTap();
    void onMenuEntryAboutTap();
}
