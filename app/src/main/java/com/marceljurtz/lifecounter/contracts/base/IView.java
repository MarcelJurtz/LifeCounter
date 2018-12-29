package com.marceljurtz.lifecounter.contracts.base;

public interface IView {
    void loadActivity(Class c);
    void startBrowserIntent(String url);
}
