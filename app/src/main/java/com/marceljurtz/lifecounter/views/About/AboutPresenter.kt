package com.marceljurtz.lifecounter.views.About

import android.content.SharedPreferences

import com.marceljurtz.lifecounter.views.Base.Presenter
import com.marceljurtz.lifecounter.views.Counter.CounterActivity
import com.marceljurtz.lifecounter.views.Dicing.DicingActivity
import com.marceljurtz.lifecounter.views.Game.GameActivity
import com.marceljurtz.lifecounter.models.PreferenceManager
import com.marceljurtz.lifecounter.views.Settings.SettingsActivity

class AboutPresenter(view: IAboutView, preferences: SharedPreferences) : Presenter(view, preferences), IAboutPresenter
