package com.marceljurtz.lifecounter.views.Dicing;

import com.marceljurtz.lifecounter.contracts.IDicingNavDrawerInteraction;
import com.marceljurtz.lifecounter.contracts.base.IPresenter;

public interface IDicingPresenter extends IPresenter, IDicingNavDrawerInteraction{
    void onScreenTap();
}
