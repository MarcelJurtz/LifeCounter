package com.marceljurtz.lifecounter.Dicing;

import android.content.SharedPreferences;

import com.marceljurtz.lifecounter.Helper.BaseInterface.IView;
import com.marceljurtz.lifecounter.Helper.Color;

public interface IDicingView extends IView {
    void setDicingText(String text);
    void startGameActivity();
    void startSettingsActivity();
    void startAboutActivity();
    void setBackgroundColor(Color color);
    void startCounterManagerActivity();
    SharedPreferences getPreferences();
}
