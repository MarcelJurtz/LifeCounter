package com.marceljurtz.lifecounter.About;

import android.content.SharedPreferences;

import com.marceljurtz.lifecounter.Helper.BaseInterface.IView;

public interface IAboutView extends IView {
    void LoadAboutPage(String url);
    void LoadGameActivity();
    void LoadSettingsActivity();
    void LoadDicingActivity();
    void LoadCounterManagerActivity();
}
