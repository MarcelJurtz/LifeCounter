package com.marceljurtz.lifecounter.About;

import com.marceljurtz.lifecounter.Helper.BaseInterface.IView;

public interface IAboutView extends IView {
    void loadAboutPage(String url);
    void start2PlayerGame();
    void start4PlayerGame();
    void startSettingsActivity();
    void startDicingActivity();
}
