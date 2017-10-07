package com.marceljurtz.lifecounter.Dicing;

import android.content.SharedPreferences;

import com.marceljurtz.lifecounter.Helper.BaseInterface.IView;

public interface IDicingView extends IView {
    void setDicingText(String text);
    void startGameActivity();
    void startSettingsActivity();
    void startAboutActivity();
    SharedPreferences getPreferences();
}
