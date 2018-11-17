package com.marceljurtz.lifecounter.contracts.base;

import com.marceljurtz.lifecounter.contracts.INavDrawerInteraction;

public interface IPresenter extends INavDrawerInteraction {
    void onCreate();
    void onPause();
    void onResume();
    void onDestroy();
}
