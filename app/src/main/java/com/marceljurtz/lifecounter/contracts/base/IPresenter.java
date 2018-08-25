package com.marceljurtz.lifecounter.contracts.base;

public interface IPresenter {
    void onCreate();
    void onPause();
    void onResume();
    void onDestroy();
}
