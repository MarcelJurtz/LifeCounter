package com.marceljurtz.lifecounter.views.Dicing

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView

import com.google.android.material.navigation.NavigationView
import com.marceljurtz.lifecounter.models.Color
import com.marceljurtz.lifecounter.models.PreferenceManager
import com.marceljurtz.lifecounter.R

class DicingActivity : com.marceljurtz.lifecounter.views.Base.View(), IDicingView {

    private var lblDicing: TextView? = null
    private var rlDicing: RelativeLayout? = null
    private var navigationView: NavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dicing)

        lblDicing = findViewById<View>(R.id.lblDicing) as TextView
        rlDicing = findViewById<View>(R.id.rlDicing) as RelativeLayout
        navigationView = findViewById<View>(R.id.navigationViewDicing) as NavigationView

        disableMenuItem(navigationView!!, R.id.nav_dicing)
        disableMenuItem(navigationView!!, R.id.nav_energy_save_mode)

        _presenter = DicingPresenter(this, applicationContext.getSharedPreferences(PreferenceManager.PREFS, Activity.MODE_PRIVATE))
        _presenter!!.onCreate()

        rlDicing!!.setOnClickListener { (_presenter as IDicingPresenter).onScreenTap() }

        navigationView!!.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_game_2players -> _presenter!!.onMenuEntryTwoPlayerTap()
                R.id.nav_game_4players -> _presenter!!.onMenuEntryFourPlayerTap()
                R.id.nav_settings -> _presenter!!.onMenuEntrySettingsTap()
                R.id.nav_about -> _presenter!!.onMenuEntryAboutTap()
                R.id.nav_countermanager -> _presenter!!.onMenuEntryCounterManagerTap()
                else -> {
                }
            }
            true
        }

        // Initial dice throw when activity is launching
        (_presenter as IDicingPresenter).onScreenTap()
    }

    override fun setDicingText(text: String) {
        lblDicing!!.text = text
    }

    override fun setBackgroundColor(color: Color) {
        rlDicing!!.setBackgroundColor(color.intValue)
    }
}
