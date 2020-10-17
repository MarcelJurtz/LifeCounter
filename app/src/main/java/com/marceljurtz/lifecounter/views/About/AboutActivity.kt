package com.marceljurtz.lifecounter.views.About

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView

import com.google.android.material.navigation.NavigationView
import com.marceljurtz.lifecounter.models.AppDetails
import com.marceljurtz.lifecounter.models.PreferenceManager
import com.marceljurtz.lifecounter.R
import com.marceljurtz.lifecounter.views.Base.View

class AboutActivity : View(), IAboutView {

    internal var navigationView: NavigationView? = null

    internal var lblContactMail: TextView? = null

    private var lblLicenseColorPicker: TextView? = null
    private var lblLicenseAppIntro: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        initControls()

        disableMenuItem(navigationView, R.id.nav_energy_save_mode)
        disableMenuItem(navigationView, R.id.nav_about)

        _presenter = AboutPresenter(this,
                applicationContext.getSharedPreferences(PreferenceManager.PREFS, Activity.MODE_PRIVATE))

        _presenter.onCreate()

        navigationView!!.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dicing -> _presenter.onMenuEntryDicingTap()
                R.id.nav_game_2players -> _presenter.onMenuEntryTwoPlayerTap()
                R.id.nav_game_4players -> _presenter.onMenuEntryFourPlayerTap()
                R.id.nav_settings -> _presenter.onMenuEntrySettingsTap()
                R.id.nav_countermanager -> _presenter.onMenuEntryCounterManagerTap()
                else -> {
                }
            }
            true
        }
    }

    private fun initControls() {
        navigationView = findViewById<android.view.View>(R.id.navigationViewAbout) as NavigationView

        lblContactMail = findViewById(R.id.lblContactMail)

        lblLicenseColorPicker = findViewById(R.id.lblLicColorPicker)
        lblLicenseAppIntro = findViewById(R.id.lblLicAppIntro)

        lblContactMail!!.setOnClickListener { super@AboutActivity.startBrowserIntent(AppDetails.CONTACT_MAIL) }

        lblLicenseColorPicker!!.setOnClickListener { super@AboutActivity.startBrowserIntent(resources.getString(R.string.about_lic_colorpicker_link)) }

        lblLicenseAppIntro!!.setOnClickListener { super@AboutActivity.startBrowserIntent(resources.getString(R.string.about_lic_appintro_link)) }
    }
}