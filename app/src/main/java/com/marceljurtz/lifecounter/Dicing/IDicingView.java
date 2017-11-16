package com.marceljurtz.lifecounter.Dicing;

import android.content.SharedPreferences;

import com.marceljurtz.lifecounter.Helper.BaseInterface.IView;
import com.marceljurtz.lifecounter.Helper.Color;

public interface IDicingView extends IView {
    void SetDicingText(String text);
    void LoadGameActivity();
    void LoadSettingsActivity();
    void LoadAboutActivity();
    void LoadCounterManagerActivity();
    void SetBackgroundColor(Color color);
}
