package com.marceljurtz.lifecounter.Dicing;

import com.marceljurtz.lifecounter.Helper.BaseInterface.IDicingNavDrawerInteraction;
import com.marceljurtz.lifecounter.Helper.BaseInterface.IPresenter;

public interface IDicingPresenter extends IPresenter, IDicingNavDrawerInteraction{
    void OnScreenTap();
}
