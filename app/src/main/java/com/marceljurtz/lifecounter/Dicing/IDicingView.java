package com.marceljurtz.lifecounter.Dicing;

import com.marceljurtz.lifecounter.Helper.BaseInterface.IView;

public interface IDicingView extends IView {
    void setDicingText(String text);
    void start2PlayerGame();
    void start4PlayerGame();
    void startSettingsActivity();
}
