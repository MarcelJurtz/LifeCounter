package com.marceljurtz.lifecounter.views.Settings

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.app.AlertDialog
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Switch
import android.widget.TextView

import com.google.android.material.navigation.NavigationView
import com.marceljurtz.lifecounter.models.Color
import com.marceljurtz.lifecounter.enums.MagicColorEnum
import com.marceljurtz.lifecounter.models.PreferenceManager
import com.marceljurtz.lifecounter.R
import com.pes.androidmaterialcolorpickerdialog.ColorPicker

class SettingsActivity : com.marceljurtz.lifecounter.views.Base.View(), ISettingsView {

    //region Controls

    private var txtBlack: TextView? = null
    private var txtBlue: TextView? = null
    private var txtGreen: TextView? = null
    private var txtRed: TextView? = null
    private var txtWhite: TextView? = null

    private var txtLifepoints: EditText? = null
    private var txtLongClickPoints: EditText? = null

    private var cmdSelectBlack: Button? = null
    private var cmdSelectBlue: Button? = null
    private var cmdSelectGreen: Button? = null
    private var cmdSelectRed: Button? = null
    private var cmdSelectWhite: Button? = null

    private var cmdShowAppIntro: Button? = null
    private var cmdReset: Button? = null

    private var chkKeepScreenOn: Switch? = null
    private var chkConfirmGameReset: Switch? = null

    private var navigationView: NavigationView? = null

    //endregion Controls

    internal var preferences: SharedPreferences = getApplicationContext().getSharedPreferences(PreferenceManager.PREFS, Activity.MODE_PRIVATE)
    internal var presenter: ISettingsPresenter = SettingsPresenter( this, preferences)


    //region Get EditText and color values

    //endregion

    //region Set EditText values

    override var lifepoints: String
        get() = txtLifepoints!!.text.toString()
        set(lifepointText) = txtLifepoints!!.setText(lifepointText)

    override var longClickPoints: String
        get() = txtLongClickPoints!!.text.toString()
        set(longClickPointText) = txtLongClickPoints!!.setText(longClickPointText)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        preferences = applicationContext.getSharedPreferences(PreferenceManager.PREFS, Activity.MODE_PRIVATE)

        initControls()

        disableMenuItem(navigationView!!, R.id.nav_energy_save_mode)
        disableMenuItem(navigationView!!, R.id.nav_settings)

        presenter = SettingsPresenter(this, preferences)
        presenter.onCreate()
    }

    //endregion

    override fun onBackPressed() {
        presenter.onBackButtonClick()
    }

    override fun returnToPrevActivity() {
        finish()
    }

    override fun loadActivity(c: Class<*>) {
        val intent = Intent(applicationContext, c)
        startActivity(intent)
    }

    override fun loadColorPickerDialog(color: MagicColorEnum, r: Int, g: Int, b: Int) {
        val cp = ColorPicker(this@SettingsActivity, r, g, b)

        cp.show()

        val okColor = cp.findViewById<View>(R.id.okColorButton) as Button
        okColor.setOnClickListener {
            val newColor = cp.color
            presenter.onColorSelectValueUpdate(Color(color, newColor))
            cp.dismiss()
        }
    }

    override fun updateColorButtonValue(color: Color) {
        when (color.basecolor) {
            MagicColorEnum.BLUE -> {
                (cmdSelectBlue!!.background as GradientDrawable).setColor(color.intValue)
                txtBlue!!.text = color.hexString
            }
            MagicColorEnum.GREEN -> {
                (cmdSelectGreen!!.background as GradientDrawable).setColor(color.intValue)
                txtGreen!!.text = color.hexString
            }
            MagicColorEnum.RED -> {
                (cmdSelectRed!!.background as GradientDrawable).setColor(color.intValue)
                txtRed!!.text = color.hexString
            }
            MagicColorEnum.WHITE -> {
                (cmdSelectWhite!!.background as GradientDrawable).setColor(color.intValue)
                txtWhite!!.text = color.hexString
            }
            else -> {
                (cmdSelectBlack!!.background as GradientDrawable).setColor(color.intValue)
                txtBlack!!.text = color.hexString
            }
        }
    }

    override fun loadResetConfirmationDialog() {
        AlertDialog.Builder(this@SettingsActivity)
                .setMessage(R.string.settings_confirm_reset)
                .setCancelable(false)
                .setPositiveButton(R.string.settings_confirm_reset_true) { dialog, id -> presenter.onResetButtonConfirm() }
                .setNegativeButton(R.string.settings_confirm_reset_false) { dialog, which -> presenter.onResetButtonCancel() }
                .show()
    }

    override fun setKeepScreenOnCheckbox(checked: Boolean) {
        chkKeepScreenOn!!.isChecked = checked
    }

    override fun setConfirmGameResetCheckbox(checked: Boolean) {
        chkConfirmGameReset!!.isChecked = checked
    }

    private fun initControls() {

        txtBlack = findViewById<View>(R.id.txtColorBlack) as TextView
        txtBlue = findViewById<View>(R.id.txtColorBlue) as TextView
        txtGreen = findViewById<View>(R.id.txtColorGreen) as TextView
        txtRed = findViewById<View>(R.id.txtColorRed) as TextView
        txtWhite = findViewById<View>(R.id.txtColorWhite) as TextView

        txtLifepoints = findViewById<View>(R.id.txtLiveSelection) as EditText
        txtLongClickPoints = findViewById<View>(R.id.txtLongClickPoints) as EditText

        chkKeepScreenOn = findViewById<View>(R.id.chkKeepScreenOn) as Switch
        chkKeepScreenOn!!.setOnCheckedChangeListener { buttonView, isChecked -> presenter.onKeepScreenOnCheckboxClick(isChecked) }

        chkConfirmGameReset = findViewById<View>(R.id.chkConfirmReset) as Switch
        chkConfirmGameReset!!.setOnCheckedChangeListener { compoundButton, isChecked -> presenter.onConfirmGameResetCheckboxClick(isChecked) }

        cmdSelectBlack = findViewById<View>(R.id.cmdSelectBlack) as Button
        cmdSelectBlack!!.setOnClickListener { presenter.onColorSelectButtonClick(MagicColorEnum.BLACK) }

        cmdSelectBlue = findViewById<View>(R.id.cmdSelectBlue) as Button
        cmdSelectBlue!!.setOnClickListener { presenter.onColorSelectButtonClick(MagicColorEnum.BLUE) }

        cmdSelectGreen = findViewById<View>(R.id.cmdSelectGreen) as Button
        cmdSelectGreen!!.setOnClickListener { presenter.onColorSelectButtonClick(MagicColorEnum.GREEN) }

        cmdSelectRed = findViewById<View>(R.id.cmdSelectRed) as Button
        cmdSelectRed!!.setOnClickListener { presenter.onColorSelectButtonClick(MagicColorEnum.RED) }

        cmdSelectWhite = findViewById<View>(R.id.cmdSelectWhite) as Button
        cmdSelectWhite!!.setOnClickListener { presenter.onColorSelectButtonClick(MagicColorEnum.WHITE) }

        cmdReset = findViewById<View>(R.id.cmdResetSettings) as Button
        cmdReset!!.setOnClickListener { presenter.onResetButtonClick() }

        cmdShowAppIntro = findViewById<View>(R.id.cmdShowIntro) as Button
        cmdShowAppIntro!!.setOnClickListener { presenter.onShowAppIntroClick() }

        navigationView = findViewById<View>(R.id.navigationViewSettings) as NavigationView
        navigationView!!.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_about -> presenter.onMenuEntryAboutTap()
                R.id.nav_game_2players -> presenter.onMenuEntryTwoPlayerTap()
                R.id.nav_game_4players -> presenter.onMenuEntryFourPlayerTap()
                R.id.nav_dicing -> presenter.onMenuEntryDicingTap()
                R.id.nav_countermanager -> presenter.onMenuEntryCounterManagerTap()
                else -> {
                }
            }
            true
        }
    }
}