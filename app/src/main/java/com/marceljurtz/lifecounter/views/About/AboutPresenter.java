package com.marceljurtz.lifecounter.views.About;

import android.content.SharedPreferences;

import com.marceljurtz.lifecounter.views.Base.Presenter;
import com.marceljurtz.lifecounter.views.Counter.CounterActivity;
import com.marceljurtz.lifecounter.views.Dicing.DicingActivity;
import com.marceljurtz.lifecounter.views.Game.GameActivity;
import com.marceljurtz.lifecounter.models.PreferenceManager;
import com.marceljurtz.lifecounter.views.Settings.SettingsActivity;

public class AboutPresenter extends Presenter implements IAboutPresenter {

    public AboutPresenter(IAboutView view, SharedPreferences preferences) {
        super(view, preferences);
    }
}
