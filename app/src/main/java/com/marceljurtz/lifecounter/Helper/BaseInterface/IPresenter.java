package com.marceljurtz.lifecounter.Helper.BaseInterface;

public interface IPresenter {
    void onCreate(IView view);
    void onPause();
    void onResume();
    void onDestroy();
}
