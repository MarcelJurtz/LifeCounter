package com.marceljurtz.lifecounter.views.Game

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView

import com.google.android.material.navigation.NavigationView
import com.marceljurtz.lifecounter.enums.ClickTypeEnum
import com.marceljurtz.lifecounter.enums.MagicColorEnum
import com.marceljurtz.lifecounter.enums.OperatorEnum
import com.marceljurtz.lifecounter.models.Player
import com.marceljurtz.lifecounter.enums.PlayerIdEnum
import com.marceljurtz.lifecounter.models.PreferenceManager
import com.marceljurtz.lifecounter.R
import com.marceljurtz.lifecounter.views.Intro.IntroActivity

import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout

@Suppress("DEPRECATION")
class GameActivity : com.marceljurtz.lifecounter.views.Base.View(), IGameView {

    //endregion
    private lateinit var preferences: SharedPreferences
    override var playerAmount: Int = 0

    private var player1: Player? = null
    private var player2: Player? = null
    private var player3: Player? = null
    private var player4: Player? = null

//    private val preferences: SharedPreferences = this.getSharedPreferences(PreferenceManager.PREFS, Activity.MODE_PRIVATE)

    //region Controls

    private var mainLayout: DrawerLayout? = null;
    private var layoutPlayer1: RelativeLayout? = null;
    private var layoutPlayer2: RelativeLayout? = null;
    private var layoutPlayer3: RelativeLayout? = null;
    private var layoutPlayer4: RelativeLayout? = null;

    private var cmdPlusPlayer1: ImageButton? = null;
    private var cmdPlusPlayer2: ImageButton? = null;
    private var cmdPlusPlayer3: ImageButton? = null;
    private var cmdPlusPlayer4: ImageButton? = null;

    private var cmdMinusPlayer1: ImageButton? = null;
    private var cmdMinusPlayer2: ImageButton? = null;
    private var cmdMinusPlayer3: ImageButton? = null;
    private var cmdMinusPlayer4: ImageButton? = null;

    private var cmdResetLP: ImageButton? = null;
    private var cmdTogglePoison: ImageButton? = null;

    private var cmdPlusPoisonPlayer1: ImageButton? = null
    private var cmdPlusPoisonPlayer2: ImageButton? = null
    private var cmdPlusPoisonPlayer3: ImageButton? = null
    private var cmdPlusPoisonPlayer4: ImageButton? = null

    private var cmdMinusPoisonPlayer1: ImageButton? = null
    private var cmdMinusPoisonPlayer2: ImageButton? = null
    private var cmdMinusPoisonPlayer3: ImageButton? = null
    private var cmdMinusPoisonPlayer4: ImageButton? = null

    private var cmdToggleColorSettings: ImageButton? = null

    private var cmdBlackPlayer1: Button? = null
    private var cmdBlackPlayer2: Button? = null
    private var cmdBlackPlayer3: Button? = null
    private var cmdBlackPlayer4: Button? = null

    private var cmdBluePlayer1: Button? = null
    private var cmdBluePlayer2: Button? = null
    private var cmdBluePlayer3: Button? = null
    private var cmdBluePlayer4: Button? = null

    private var cmdGreenPlayer1: Button? = null
    private var cmdGreenPlayer2: Button? = null
    private var cmdGreenPlayer3: Button? = null
    private var cmdGreenPlayer4: Button? = null

    private var cmdRedPlayer1: Button? = null
    private var cmdRedPlayer2: Button? = null
    private var cmdRedPlayer3: Button? = null
    private var cmdRedPlayer4: Button? = null

    private var cmdWhitePlayer1: Button? = null
    private var cmdWhitePlayer2: Button? = null
    private var cmdWhitePlayer3: Button? = null
    private var cmdWhitePlayer4: Button? = null

    private var txtLifeCountPlayer1: TextView? = null
    private var txtLifeCountPlayer2: TextView? = null
    private var txtLifeCountPlayer3: TextView? = null
    private var txtLifeCountPlayer4: TextView? = null

    private var txtPoisonCountPlayer1: TextView? = null
    private var txtPoisonCountPlayer2: TextView? = null
    private var txtPoisonCountPlayer3: TextView? = null
    private var txtPoisonCountPlayer4: TextView? = null

    private var navigationView: NavigationView? = null

    private var toolbar: Toolbar? = null


    //endregion Controls

    private var showResetConfirmation: Boolean = false

    override val screenSize: Int
        get() = resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferences = this.getSharedPreferences(PreferenceManager.PREFS, Activity.MODE_PRIVATE)

        checkFirstLaunch()
        playerAmount = PreferenceManager.getPlayerAmount(preferences)

        if (playerAmount == 4) {
            setContentView(R.layout.activity_main_4player)
        } else {
            setContentView(R.layout.activity_main_2player)
        }

        player1 = Player(PlayerIdEnum.ONE)
        player2 = Player(PlayerIdEnum.TWO)
        if (playerAmount == 4) {
            player3 = Player(PlayerIdEnum.THREE)
            player4 = Player(PlayerIdEnum.FOUR)
        }

        initControls()

        if (playerAmount == 4) {
            disableMenuItem(navigationView!!, R.id.nav_game_4players)
        } else {
            disableMenuItem(navigationView!!, R.id.nav_game_2players)
        }

        _presenter = GamePresenter(this, preferences)
    }

    private fun checkFirstLaunch() {
        val t = Thread(Runnable {
            val isFirstStart = preferences.getBoolean("firstStart", true)
            if (isFirstStart) {
                val editor = preferences.edit()
                editor.putBoolean("firstStart", false)
                editor.apply()
                val i = Intent(this@GameActivity, IntroActivity::class.java)
                runOnUiThread { startActivity(i) }
            }
        })

        t.start()
    }

    override fun setConfirmGameReset(confirmGameReset: Boolean) {
        showResetConfirmation = confirmGameReset
    }

    override fun restartActivity() {
        recreate()
    }

    override fun hideNavigationDrawer() {
        mainLayout!!.closeDrawer(Gravity.LEFT)
    }

    override fun initColorButton(colorLocation: MagicColorEnum, color: Int) {

        val buttonPlayer1: Button?
        val buttonPlayer2: Button?
        val buttonPlayer3: Button?
        val buttonPlayer4: Button?

        when (colorLocation) {
            MagicColorEnum.BLACK -> {
                buttonPlayer1 = cmdBlackPlayer1
                buttonPlayer2 = cmdBlackPlayer2
                buttonPlayer3 = cmdBlackPlayer3
                buttonPlayer4 = cmdBlackPlayer4
            }
            MagicColorEnum.BLUE -> {
                buttonPlayer1 = cmdBluePlayer1
                buttonPlayer2 = cmdBluePlayer2
                buttonPlayer3 = cmdBluePlayer3
                buttonPlayer4 = cmdBluePlayer4
            }
            MagicColorEnum.GREEN -> {
                buttonPlayer1 = cmdGreenPlayer1
                buttonPlayer2 = cmdGreenPlayer2
                buttonPlayer3 = cmdGreenPlayer3
                buttonPlayer4 = cmdGreenPlayer4
            }
            MagicColorEnum.RED -> {
                buttonPlayer1 = cmdRedPlayer1
                buttonPlayer2 = cmdRedPlayer2
                buttonPlayer3 = cmdRedPlayer3
                buttonPlayer4 = cmdRedPlayer4
            }
            MagicColorEnum.WHITE -> {
                buttonPlayer1 = cmdWhitePlayer1
                buttonPlayer2 = cmdWhitePlayer2
                buttonPlayer3 = cmdWhitePlayer3
                buttonPlayer4 = cmdWhitePlayer4
            }
        }
        if (buttonPlayer1 != null && buttonPlayer2 != null) {
            (buttonPlayer1.background as GradientDrawable).setColor(color)
            (buttonPlayer2.background as GradientDrawable).setColor(color)
            if (playerAmount == 4) {
                (buttonPlayer3!!.background as GradientDrawable).setColor(color)
                (buttonPlayer4!!.background as GradientDrawable).setColor(color)
            }
        }
    }

    override fun loadActivity(c: Class<*>) {
        val intent = Intent(applicationContext, c)
        startActivity(intent)
    }

    override fun setLayoutColor(playerIdEnum: PlayerIdEnum, color: Int) {
        if (playerIdEnum == PlayerIdEnum.ONE) {
            layoutPlayer1!!.setBackgroundColor(color)
        } else if (playerIdEnum == PlayerIdEnum.TWO) {
            layoutPlayer2!!.setBackgroundColor(color)
        } else if (playerIdEnum == PlayerIdEnum.THREE) {
            layoutPlayer3!!.setBackgroundColor(color)
        } else if (playerIdEnum == PlayerIdEnum.FOUR) {
            layoutPlayer4!!.setBackgroundColor(color)
        }
    }

    //region Toggle Poison Controls
    override fun enablePoisonControls(rearrangeLifepoints: Boolean) {
        txtPoisonCountPlayer1!!.visibility = View.VISIBLE
        txtPoisonCountPlayer2!!.visibility = View.VISIBLE

        cmdPlusPoisonPlayer1!!.visibility = View.VISIBLE
        cmdPlusPoisonPlayer2!!.visibility = View.VISIBLE

        cmdMinusPoisonPlayer1!!.visibility = View.VISIBLE
        cmdMinusPoisonPlayer2!!.visibility = View.VISIBLE

        if (playerAmount == 4) {
            txtPoisonCountPlayer3!!.visibility = View.VISIBLE
            txtPoisonCountPlayer4!!.visibility = View.VISIBLE

            cmdPlusPoisonPlayer3!!.visibility = View.VISIBLE
            cmdPlusPoisonPlayer4!!.visibility = View.VISIBLE

            cmdMinusPoisonPlayer3!!.visibility = View.VISIBLE
            cmdMinusPoisonPlayer4!!.visibility = View.VISIBLE
        }

        if (rearrangeLifepoints) {
            val paramsLeft = txtLifeCountPlayer1!!.layoutParams as RelativeLayout.LayoutParams
            paramsLeft.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
            paramsLeft.addRule(RelativeLayout.ALIGN_PARENT_END)

            val paramsRight = txtLifeCountPlayer2!!.layoutParams as RelativeLayout.LayoutParams
            paramsRight.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
            paramsRight.addRule(RelativeLayout.ALIGN_PARENT_START)

            txtLifeCountPlayer1!!.layoutParams = paramsLeft
            txtLifeCountPlayer2!!.layoutParams = paramsRight

            if (playerAmount == 4) {
                txtLifeCountPlayer3!!.layoutParams = paramsLeft
                txtLifeCountPlayer4!!.layoutParams = paramsRight
            }
        }
    }

    override fun poisonButtonEnable() {
        cmdTogglePoison!!.background = applicationContext.resources.getDrawable(R.drawable.icon_poison)
    }

    override fun disablePoisonControls(rearrangeLifepoints: Boolean) {
        txtPoisonCountPlayer1!!.visibility = View.INVISIBLE
        txtPoisonCountPlayer2!!.visibility = View.INVISIBLE

        cmdPlusPoisonPlayer1!!.visibility = View.INVISIBLE
        cmdPlusPoisonPlayer2!!.visibility = View.INVISIBLE

        cmdMinusPoisonPlayer1!!.visibility = View.INVISIBLE
        cmdMinusPoisonPlayer2!!.visibility = View.INVISIBLE

        if (playerAmount == 4) {
            txtPoisonCountPlayer3!!.visibility = View.INVISIBLE
            txtPoisonCountPlayer4!!.visibility = View.INVISIBLE

            cmdPlusPoisonPlayer3!!.visibility = View.INVISIBLE
            cmdPlusPoisonPlayer4!!.visibility = View.INVISIBLE

            cmdMinusPoisonPlayer3!!.visibility = View.INVISIBLE
            cmdMinusPoisonPlayer4!!.visibility = View.INVISIBLE
        }

        if (rearrangeLifepoints) {
            val paramsLeft = txtLifeCountPlayer1!!.layoutParams as RelativeLayout.LayoutParams
            paramsLeft.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0)
            paramsLeft.addRule(RelativeLayout.ALIGN_PARENT_END, 0)

            val paramsRight = txtLifeCountPlayer2!!.layoutParams as RelativeLayout.LayoutParams
            paramsRight.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0)
            paramsRight.addRule(RelativeLayout.ALIGN_PARENT_START, 0)

            txtLifeCountPlayer1!!.layoutParams = paramsLeft
            txtLifeCountPlayer2!!.layoutParams = paramsRight

            if (playerAmount == 4) {
                txtLifeCountPlayer3!!.layoutParams = paramsLeft
                txtLifeCountPlayer4!!.layoutParams = paramsRight
            }
        }
    }

    override fun poisonButtonDisable() {
        cmdTogglePoison!!.background = applicationContext.resources.getDrawable(R.drawable.icon_poison_disabled)
    }
    //endregion

    //region Toggle Settings Controls
    override fun enableSettingsControls(hideLifecountControls: Boolean, hidePoisonControls: Boolean) {
        cmdBlackPlayer1!!.visibility = View.VISIBLE
        cmdBlackPlayer2!!.visibility = View.VISIBLE

        cmdBluePlayer1!!.visibility = View.VISIBLE
        cmdBluePlayer2!!.visibility = View.VISIBLE

        cmdGreenPlayer1!!.visibility = View.VISIBLE
        cmdGreenPlayer2!!.visibility = View.VISIBLE

        cmdRedPlayer1!!.visibility = View.VISIBLE
        cmdRedPlayer2!!.visibility = View.VISIBLE

        cmdWhitePlayer1!!.visibility = View.VISIBLE
        cmdWhitePlayer2!!.visibility = View.VISIBLE

        if (playerAmount == 4) {
            cmdBlackPlayer3!!.visibility = View.VISIBLE
            cmdBlackPlayer4!!.visibility = View.VISIBLE

            cmdBluePlayer3!!.visibility = View.VISIBLE
            cmdBluePlayer4!!.visibility = View.VISIBLE

            cmdGreenPlayer3!!.visibility = View.VISIBLE
            cmdGreenPlayer4!!.visibility = View.VISIBLE

            cmdRedPlayer3!!.visibility = View.VISIBLE
            cmdRedPlayer4!!.visibility = View.VISIBLE

            cmdWhitePlayer3!!.visibility = View.VISIBLE
            cmdWhitePlayer4!!.visibility = View.VISIBLE
        }

        if (hideLifecountControls) {
            txtLifeCountPlayer1!!.visibility = View.INVISIBLE
            txtLifeCountPlayer2!!.visibility = View.INVISIBLE

            cmdPlusPlayer1!!.visibility = View.INVISIBLE
            cmdPlusPlayer2!!.visibility = View.INVISIBLE

            cmdMinusPlayer1!!.visibility = View.INVISIBLE
            cmdMinusPlayer2!!.visibility = View.INVISIBLE

            if (playerAmount == 4) {
                txtLifeCountPlayer3!!.visibility = View.INVISIBLE
                txtLifeCountPlayer4!!.visibility = View.INVISIBLE

                cmdPlusPlayer3!!.visibility = View.INVISIBLE
                cmdPlusPlayer4!!.visibility = View.INVISIBLE

                cmdMinusPlayer3!!.visibility = View.INVISIBLE
                cmdMinusPlayer4!!.visibility = View.INVISIBLE
            }
        }

        if (hidePoisonControls) {

            cmdPlusPoisonPlayer1!!.visibility = View.INVISIBLE
            cmdMinusPoisonPlayer1!!.visibility = View.INVISIBLE
            txtPoisonCountPlayer1!!.visibility = View.INVISIBLE

            cmdPlusPoisonPlayer2!!.visibility = View.INVISIBLE
            cmdMinusPoisonPlayer2!!.visibility = View.INVISIBLE
            txtPoisonCountPlayer2!!.visibility = View.INVISIBLE

            if (playerAmount == 4) {
                cmdPlusPoisonPlayer3!!.visibility = View.INVISIBLE
                cmdMinusPoisonPlayer3!!.visibility = View.INVISIBLE
                txtPoisonCountPlayer3!!.visibility = View.INVISIBLE

                cmdPlusPoisonPlayer4!!.visibility = View.INVISIBLE
                cmdMinusPoisonPlayer4!!.visibility = View.INVISIBLE
                txtPoisonCountPlayer4!!.visibility = View.INVISIBLE
            }
        }
    }

    override fun settingsButtonEnable() {
        cmdToggleColorSettings!!.background = applicationContext.resources.getDrawable(R.drawable.icon_settings)
    }

    override fun disableSettingsControls(showLifecountControls: Boolean, showPoisonControls: Boolean) {
        cmdBlackPlayer1!!.visibility = View.INVISIBLE
        cmdBlackPlayer2!!.visibility = View.INVISIBLE

        cmdBluePlayer1!!.visibility = View.INVISIBLE
        cmdBluePlayer2!!.visibility = View.INVISIBLE

        cmdGreenPlayer1!!.visibility = View.INVISIBLE
        cmdGreenPlayer2!!.visibility = View.INVISIBLE

        cmdRedPlayer1!!.visibility = View.INVISIBLE
        cmdRedPlayer2!!.visibility = View.INVISIBLE

        cmdWhitePlayer1!!.visibility = View.INVISIBLE
        cmdWhitePlayer2!!.visibility = View.INVISIBLE

        if (playerAmount == 4) {
            cmdBlackPlayer3!!.visibility = View.INVISIBLE
            cmdBlackPlayer4!!.visibility = View.INVISIBLE

            cmdBluePlayer3!!.visibility = View.INVISIBLE
            cmdBluePlayer4!!.visibility = View.INVISIBLE

            cmdGreenPlayer3!!.visibility = View.INVISIBLE
            cmdGreenPlayer4!!.visibility = View.INVISIBLE

            cmdRedPlayer3!!.visibility = View.INVISIBLE
            cmdRedPlayer4!!.visibility = View.INVISIBLE

            cmdWhitePlayer3!!.visibility = View.INVISIBLE
            cmdWhitePlayer4!!.visibility = View.INVISIBLE
        }

        if (showLifecountControls) {
            txtLifeCountPlayer1!!.visibility = View.VISIBLE
            txtLifeCountPlayer2!!.visibility = View.VISIBLE

            cmdPlusPlayer1!!.visibility = View.VISIBLE
            cmdPlusPlayer2!!.visibility = View.VISIBLE

            cmdMinusPlayer1!!.visibility = View.VISIBLE
            cmdMinusPlayer2!!.visibility = View.VISIBLE

            if (playerAmount == 4) {
                txtLifeCountPlayer3!!.visibility = View.VISIBLE
                txtLifeCountPlayer4!!.visibility = View.VISIBLE

                cmdPlusPlayer3!!.visibility = View.VISIBLE
                cmdPlusPlayer4!!.visibility = View.VISIBLE

                cmdMinusPlayer3!!.visibility = View.VISIBLE
                cmdMinusPlayer4!!.visibility = View.VISIBLE
            }
        }

        if (showPoisonControls) {

            cmdPlusPoisonPlayer1!!.visibility = View.VISIBLE
            cmdMinusPoisonPlayer1!!.visibility = View.VISIBLE
            txtPoisonCountPlayer1!!.visibility = View.VISIBLE

            cmdPlusPoisonPlayer2!!.visibility = View.VISIBLE
            cmdMinusPoisonPlayer2!!.visibility = View.VISIBLE
            txtPoisonCountPlayer2!!.visibility = View.VISIBLE

            if (playerAmount == 4) {
                cmdPlusPoisonPlayer3!!.visibility = View.VISIBLE
                cmdMinusPoisonPlayer3!!.visibility = View.VISIBLE
                txtPoisonCountPlayer3!!.visibility = View.VISIBLE

                cmdPlusPoisonPlayer4!!.visibility = View.VISIBLE
                cmdMinusPoisonPlayer4!!.visibility = View.VISIBLE
                txtPoisonCountPlayer4!!.visibility = View.VISIBLE
            }
        }
    }

    override fun settingsButtonDisable() {
        cmdToggleColorSettings!!.background = applicationContext.resources.getDrawable(R.drawable.icon_settings_disabled)
    }

    //endregion

    //region Toggle Energy Saving Option
    override fun enableEnergySaving(powerSaveColor: Int, powerSaveTextColor: Int) {
        layoutPlayer1!!.setBackgroundColor(powerSaveColor)
        layoutPlayer2!!.setBackgroundColor(powerSaveColor)
        if (playerAmount == 4) {
            layoutPlayer3!!.setBackgroundColor(powerSaveColor)
            layoutPlayer4!!.setBackgroundColor(powerSaveColor)
        }

        txtLifeCountPlayer1!!.setTextColor(powerSaveTextColor)
        txtLifeCountPlayer2!!.setTextColor(powerSaveTextColor)
        if (playerAmount == 4) {
            txtLifeCountPlayer3!!.setTextColor(powerSaveTextColor)
            txtLifeCountPlayer4!!.setTextColor(powerSaveTextColor)
        }

        txtPoisonCountPlayer1!!.setTextColor(powerSaveTextColor)
        txtPoisonCountPlayer2!!.setTextColor(powerSaveTextColor)
        if (playerAmount == 4) {
            txtPoisonCountPlayer3!!.setTextColor(powerSaveTextColor)
            txtPoisonCountPlayer4!!.setTextColor(powerSaveTextColor)
        }
    }

    override fun disableEnergySaving(defaultBlack: Int, regularTextColor: Int) {
        layoutPlayer1!!.setBackgroundColor(defaultBlack)
        layoutPlayer2!!.setBackgroundColor(defaultBlack)
        if (playerAmount == 4) {
            layoutPlayer3!!.setBackgroundColor(defaultBlack)
            layoutPlayer4!!.setBackgroundColor(defaultBlack)
        }

        txtLifeCountPlayer1!!.setTextColor(regularTextColor)
        txtLifeCountPlayer2!!.setTextColor(regularTextColor)
        if (playerAmount == 4) {
            txtLifeCountPlayer3!!.setTextColor(regularTextColor)
            txtLifeCountPlayer4!!.setTextColor(regularTextColor)
        }

        txtPoisonCountPlayer1!!.setTextColor(regularTextColor)
        txtPoisonCountPlayer2!!.setTextColor(regularTextColor)
        if (playerAmount == 4) {
            txtPoisonCountPlayer3!!.setTextColor(regularTextColor)
            txtPoisonCountPlayer4!!.setTextColor(regularTextColor)
        }
    }
    //endregion


    //region Set Life- and Poisonpoints
    override fun setLifepoints(id: PlayerIdEnum, points: String) {
        if (id == PlayerIdEnum.ONE) {
            txtLifeCountPlayer1!!.text = points
        } else if (id == PlayerIdEnum.TWO) {
            txtLifeCountPlayer2!!.text = points
        } else if (id == PlayerIdEnum.THREE) {
            txtLifeCountPlayer3!!.text = points
        } else if (id == PlayerIdEnum.FOUR) {
            txtLifeCountPlayer4!!.text = points
        }
    }

    override fun setPoisonpoints(id: PlayerIdEnum, points: String) {
        if (id == PlayerIdEnum.ONE) {
            txtPoisonCountPlayer1!!.text = points
        } else if (id == PlayerIdEnum.TWO) {
            txtPoisonCountPlayer2!!.text = points
        } else if (id == PlayerIdEnum.THREE) {
            txtPoisonCountPlayer3!!.text = points
        } else if (id == PlayerIdEnum.FOUR) {
            txtPoisonCountPlayer4!!.text = points
        }
    }
    //endregion

    //region Navigation Drawer
    override fun setDrawerTextPowerSaving(shouldBeEnabled: Boolean) {
        var string = resources.getString(R.string.cmdPowerSaveEnable)
        if (!shouldBeEnabled) {
            string = resources.getString(R.string.cmdPowerSaveDisable)
        }

        // Close drawer
        navigationView!!.menu.findItem(R.id.nav_energy_save_mode).title = string
        mainLayout!!.closeDrawer(Gravity.LEFT)
    }

    override fun disableScreenTimeout() {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun enableScreenTimeout() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }


    private fun initControls() {

        mainLayout = findViewById(R.id.mainLayout)

        toolbar = findViewById(R.id.tbMain)
        setSupportActionBar(toolbar)

        if (playerAmount == 4) {
            layoutPlayer1 = findViewById(R.id.rl4Player1)
            layoutPlayer2 = findViewById(R.id.rl4Player2)
            layoutPlayer3 = findViewById(R.id.rl4Player3)
            layoutPlayer4 = findViewById(R.id.rl4Player4)

            txtLifeCountPlayer1 = findViewById(R.id.txtLifeCount4p1)
            txtLifeCountPlayer2 = findViewById(R.id.txtLifeCount4p2)
            txtLifeCountPlayer3 = findViewById(R.id.txtLifeCount4p3)
            txtLifeCountPlayer4 = findViewById(R.id.txtLifeCount4p4)

            cmdPlusPlayer1 = findViewById(R.id.cmdPlus4p1)
            cmdPlusPlayer2 = findViewById(R.id.cmdPlus4p2)
            cmdPlusPlayer3 = findViewById(R.id.cmdPlus4p3)
            cmdPlusPlayer4 = findViewById(R.id.cmdPlus4p4)

            cmdMinusPlayer1 = findViewById(R.id.cmdMinus4p1)
            cmdMinusPlayer2 = findViewById(R.id.cmdMinus4p2)
            cmdMinusPlayer3 = findViewById(R.id.cmdMinus4p3)
            cmdMinusPlayer4 = findViewById(R.id.cmdMinus4p4)

            txtPoisonCountPlayer1 = findViewById(R.id.txtPoisonCount4p1)
            txtPoisonCountPlayer2 = findViewById(R.id.txtPoisonCount4p2)
            txtPoisonCountPlayer3 = findViewById(R.id.txtPoisonCount4p3)
            txtPoisonCountPlayer4 = findViewById(R.id.txtPoisonCount4p4)

            cmdPlusPoisonPlayer1 = findViewById(R.id.cmdPlusPoison4p1)
            cmdPlusPoisonPlayer2 = findViewById(R.id.cmdPlusPoison4p2)
            cmdPlusPoisonPlayer3 = findViewById(R.id.cmdPlusPoison4p3)
            cmdPlusPoisonPlayer4 = findViewById(R.id.cmdPlusPoison4p4)

            cmdMinusPoisonPlayer1 = findViewById(R.id.cmdMinusPoison4p1)
            cmdMinusPoisonPlayer2 = findViewById(R.id.cmdMinusPoison4p2)
            cmdMinusPoisonPlayer3 = findViewById(R.id.cmdMinusPoison4p3)
            cmdMinusPoisonPlayer4 = findViewById(R.id.cmdMinusPoison4p4)

            cmdBlackPlayer1 = findViewById(R.id.cmdBlack4p1)
            cmdBlackPlayer2 = findViewById(R.id.cmdBlack4p2)
            cmdBlackPlayer3 = findViewById(R.id.cmdBlack4p3)
            cmdBlackPlayer4 = findViewById(R.id.cmdBlack4p4)
            cmdBluePlayer1 = findViewById(R.id.cmdBlue4p1)
            cmdBluePlayer2 = findViewById(R.id.cmdBlue4p2)
            cmdBluePlayer3 = findViewById(R.id.cmdBlue4p3)
            cmdBluePlayer4 = findViewById(R.id.cmdBlue4p4)
            cmdGreenPlayer1 = findViewById(R.id.cmdGreen4p1)
            cmdGreenPlayer2 = findViewById(R.id.cmdGreen4p2)
            cmdGreenPlayer3 = findViewById(R.id.cmdGreen4p3)
            cmdGreenPlayer4 = findViewById(R.id.cmdGreen4p4)
            cmdRedPlayer1 = findViewById(R.id.cmdRed4p1)
            cmdRedPlayer2 = findViewById(R.id.cmdRed4p2)
            cmdRedPlayer3 = findViewById(R.id.cmdRed4p3)
            cmdRedPlayer4 = findViewById(R.id.cmdRed4p4)
            cmdWhitePlayer1 = findViewById(R.id.cmdWhite4p1)
            cmdWhitePlayer2 = findViewById(R.id.cmdWhite4p2)
            cmdWhitePlayer3 = findViewById(R.id.cmdWhite4p3)
            cmdWhitePlayer4 = findViewById(R.id.cmdWhite4p4)

            cmdToggleColorSettings = findViewById(R.id.cmdToggleColors4p)
            cmdTogglePoison = findViewById(R.id.cmdTogglePoison4p)
            cmdResetLP = findViewById(R.id.cmdResetLP4p)

            /*
            cmdDrawerTogglePowerSaving = (Button)findViewById(R.id.nav_energy_save_mode);
            cmdSettings = (Button)findViewById(R.id.nav_settings);
            lblVersionInfo = (TextView)findViewById(R.id.lblVersionInfo);
            */
            navigationView = findViewById(R.id.navigationView4players)
        } else {
            layoutPlayer1 = findViewById(R.id.rl2Player1)
            layoutPlayer2 = findViewById(R.id.rl2Player2)

            txtLifeCountPlayer1 = findViewById(R.id.txtLifeCount2p1)
            txtLifeCountPlayer2 = findViewById(R.id.txtLifeCount2p2)

            txtPoisonCountPlayer1 = findViewById(R.id.txtPoisonCount2p1)
            txtPoisonCountPlayer2 = findViewById(R.id.txtPoisonCount2p2)

            cmdBlackPlayer1 = findViewById(R.id.cmdBlack2p1)
            cmdBlackPlayer2 = findViewById(R.id.cmdBlack2p2)
            cmdBluePlayer1 = findViewById(R.id.cmdBlue2p1)
            cmdBluePlayer2 = findViewById(R.id.cmdBlue2p2)
            cmdGreenPlayer1 = findViewById(R.id.cmdGreen2p1)
            cmdGreenPlayer2 = findViewById(R.id.cmdGreen2p2)
            cmdRedPlayer1 = findViewById(R.id.cmdRed2p1)
            cmdRedPlayer2 = findViewById(R.id.cmdRed2p2)
            cmdWhitePlayer1 = findViewById(R.id.cmdWhite2p1)
            cmdWhitePlayer2 = findViewById(R.id.cmdWhite2p2)

            cmdPlusPlayer1 = findViewById(R.id.cmdPlusGuest)
            cmdPlusPlayer2 = findViewById(R.id.cmdPlusHome)
            cmdMinusPlayer1 = findViewById(R.id.cmdMinusGuest)
            cmdMinusPlayer2 = findViewById(R.id.cmdMinusHome)

            cmdPlusPoisonPlayer1 = findViewById(R.id.cmdPlusPoisonGuest)
            cmdPlusPoisonPlayer2 = findViewById(R.id.cmdPlusPoisonHome)
            cmdMinusPoisonPlayer1 = findViewById(R.id.cmdMinusPoisonGuest)
            cmdMinusPoisonPlayer2 = findViewById(R.id.cmdMinusPoisonHome)

            cmdToggleColorSettings = findViewById(R.id.cmdToggleColors)
            cmdTogglePoison = findViewById(R.id.cmdTogglePoison)
            cmdResetLP = findViewById(R.id.cmdResetLP)

            /*
            cmdDrawerTogglePowerSaving = (Button)findViewById(R.id.cmdTogglePowerSave2p);
            cmdSettings = (Button)findViewById(R.id.cmdSettings2p);
            lblVersionInfo = (TextView)findViewById(R.id.lblAppVersion2p);
            */

            navigationView = findViewById(R.id.navigationView2players)
        }

        //region Button Black

        cmdBlackPlayer1!!.setOnClickListener { (_presenter as IGamePresenter).onColorButtonClick(PlayerIdEnum.ONE, MagicColorEnum.BLACK, ClickTypeEnum.SHORT) }
        cmdBlackPlayer1!!.setOnLongClickListener {
            // toggleEnergySaveMode();
            (_presenter as IGamePresenter).onColorButtonClick(PlayerIdEnum.ONE, MagicColorEnum.BLACK, ClickTypeEnum.LONG)
            true
        }

        cmdBlackPlayer2!!.setOnClickListener { (_presenter as IGamePresenter).onColorButtonClick(PlayerIdEnum.TWO, MagicColorEnum.BLACK, ClickTypeEnum.SHORT) }
        cmdBlackPlayer2!!.setOnLongClickListener {
            // toggleEnergySaveMode();
            (_presenter as IGamePresenter).onColorButtonClick(PlayerIdEnum.TWO, MagicColorEnum.BLACK, ClickTypeEnum.LONG)
            true
        }

        if (playerAmount == 4) {
            cmdBlackPlayer3!!.setOnClickListener { (_presenter as IGamePresenter).onColorButtonClick(PlayerIdEnum.THREE, MagicColorEnum.BLACK, ClickTypeEnum.SHORT) }
            cmdBlackPlayer3!!.setOnLongClickListener {
                // toggleEnergySaveMode();
                (_presenter as IGamePresenter).onColorButtonClick(PlayerIdEnum.THREE, MagicColorEnum.BLACK, ClickTypeEnum.LONG)
                true
            }

            cmdBlackPlayer4!!.setOnClickListener { (_presenter as IGamePresenter).onColorButtonClick(PlayerIdEnum.FOUR, MagicColorEnum.BLACK, ClickTypeEnum.SHORT) }
            cmdBlackPlayer4!!.setOnLongClickListener {
                // toggleEnergySaveMode();
                (_presenter as IGamePresenter).onColorButtonClick(PlayerIdEnum.FOUR, MagicColorEnum.BLACK, ClickTypeEnum.LONG)
                true
            }
        }

        //endregion

        //region Button Blue

        cmdBluePlayer1!!.setOnClickListener { (_presenter as IGamePresenter).onColorButtonClick(PlayerIdEnum.ONE, MagicColorEnum.BLUE, ClickTypeEnum.SHORT) }

        cmdBluePlayer2!!.setOnClickListener { (_presenter as IGamePresenter).onColorButtonClick(PlayerIdEnum.TWO, MagicColorEnum.BLUE, ClickTypeEnum.SHORT) }

        if (playerAmount == 4) {
            cmdBluePlayer3!!.setOnClickListener { (_presenter as IGamePresenter).onColorButtonClick(PlayerIdEnum.THREE, MagicColorEnum.BLUE, ClickTypeEnum.SHORT) }

            cmdBluePlayer4!!.setOnClickListener { (_presenter as IGamePresenter).onColorButtonClick(PlayerIdEnum.FOUR, MagicColorEnum.BLUE, ClickTypeEnum.SHORT) }
        }

        //endregion

        //region Button Green
        cmdGreenPlayer1!!.setOnClickListener { (_presenter as IGamePresenter).onColorButtonClick(PlayerIdEnum.ONE, MagicColorEnum.GREEN, ClickTypeEnum.SHORT) }

        cmdGreenPlayer2!!.setOnClickListener { (_presenter as IGamePresenter).onColorButtonClick(PlayerIdEnum.TWO, MagicColorEnum.GREEN, ClickTypeEnum.SHORT) }

        if (playerAmount == 4) {
            cmdGreenPlayer3!!.setOnClickListener { (_presenter as IGamePresenter).onColorButtonClick(PlayerIdEnum.THREE, MagicColorEnum.GREEN, ClickTypeEnum.SHORT) }

            cmdGreenPlayer4!!.setOnClickListener { (_presenter as IGamePresenter).onColorButtonClick(PlayerIdEnum.FOUR, MagicColorEnum.GREEN, ClickTypeEnum.SHORT) }
        }
        //endregion

        //region Button Red
        cmdRedPlayer1!!.setOnClickListener { (_presenter as IGamePresenter).onColorButtonClick(PlayerIdEnum.ONE, MagicColorEnum.RED, ClickTypeEnum.SHORT) }

        cmdRedPlayer2!!.setOnClickListener { (_presenter as IGamePresenter).onColorButtonClick(PlayerIdEnum.TWO, MagicColorEnum.RED, ClickTypeEnum.SHORT) }

        if (playerAmount == 4) {
            cmdRedPlayer3!!.setOnClickListener { (_presenter as IGamePresenter).onColorButtonClick(PlayerIdEnum.THREE, MagicColorEnum.RED, ClickTypeEnum.SHORT) }

            cmdRedPlayer4!!.setOnClickListener { (_presenter as IGamePresenter).onColorButtonClick(PlayerIdEnum.FOUR, MagicColorEnum.RED, ClickTypeEnum.SHORT) }
        }
        //endregion

        //region Button White
        cmdWhitePlayer1!!.setOnClickListener { (_presenter as IGamePresenter).onColorButtonClick(PlayerIdEnum.ONE, MagicColorEnum.WHITE, ClickTypeEnum.SHORT) }

        cmdWhitePlayer2!!.setOnClickListener { (_presenter as IGamePresenter).onColorButtonClick(PlayerIdEnum.TWO, MagicColorEnum.WHITE, ClickTypeEnum.SHORT) }

        if (playerAmount == 4) {
            cmdWhitePlayer3!!.setOnClickListener { (_presenter as IGamePresenter).onColorButtonClick(PlayerIdEnum.THREE, MagicColorEnum.WHITE, ClickTypeEnum.SHORT) }

            cmdWhitePlayer4!!.setOnClickListener { (_presenter as IGamePresenter).onColorButtonClick(PlayerIdEnum.FOUR, MagicColorEnum.WHITE, ClickTypeEnum.SHORT) }
        }
        //endregion


        cmdPlusPlayer1!!.setOnClickListener { (_presenter as IGamePresenter).onLifeUpdate(player1!!.playerIdEnum!!, ClickTypeEnum.SHORT, OperatorEnum.ADD) }
        cmdPlusPlayer1!!.setOnLongClickListener {
            (_presenter as IGamePresenter).onLifeUpdate(player1!!.playerIdEnum!!, ClickTypeEnum.LONG, OperatorEnum.ADD)
            true
        }

        cmdMinusPlayer1!!.setOnClickListener { (_presenter as IGamePresenter).onLifeUpdate(player1!!.playerIdEnum!!, ClickTypeEnum.SHORT, OperatorEnum.SUBSTRACT) }
        cmdMinusPlayer1!!.setOnLongClickListener {
            (_presenter as IGamePresenter).onLifeUpdate(player1!!.playerIdEnum!!, ClickTypeEnum.LONG, OperatorEnum.SUBSTRACT)
            true
        }

        //endregion

        //region Lifepoints Guest

        cmdPlusPlayer2!!.setOnClickListener { (_presenter as IGamePresenter).onLifeUpdate(player2!!.playerIdEnum!!, ClickTypeEnum.SHORT, OperatorEnum.ADD) }
        cmdPlusPlayer2!!.setOnLongClickListener {
            (_presenter as IGamePresenter).onLifeUpdate(player2!!.playerIdEnum!!, ClickTypeEnum.LONG, OperatorEnum.ADD)
            true
        }

        cmdMinusPlayer2!!.setOnClickListener { (_presenter as IGamePresenter).onLifeUpdate(player2!!.playerIdEnum!!, ClickTypeEnum.SHORT, OperatorEnum.SUBSTRACT) }
        cmdMinusPlayer2!!.setOnLongClickListener {
            (_presenter as IGamePresenter).onLifeUpdate(player2!!.playerIdEnum!!, ClickTypeEnum.LONG, OperatorEnum.SUBSTRACT)
            true
        }

        if (playerAmount == 4) {
            cmdPlusPlayer3!!.setOnClickListener { (_presenter as IGamePresenter).onLifeUpdate(player3!!.playerIdEnum!!, ClickTypeEnum.SHORT, OperatorEnum.ADD) }
            cmdPlusPlayer3!!.setOnLongClickListener {
                (_presenter as IGamePresenter).onLifeUpdate(player3!!.playerIdEnum!!, ClickTypeEnum.LONG, OperatorEnum.ADD)
                true
            }

            cmdMinusPlayer3!!.setOnClickListener { (_presenter as IGamePresenter).onLifeUpdate(player3!!.playerIdEnum!!, ClickTypeEnum.SHORT, OperatorEnum.SUBSTRACT) }
            cmdMinusPlayer3!!.setOnLongClickListener {
                (_presenter as IGamePresenter).onLifeUpdate(player3!!.playerIdEnum!!, ClickTypeEnum.LONG, OperatorEnum.SUBSTRACT)
                true
            }

            cmdPlusPlayer4!!.setOnClickListener { (_presenter as IGamePresenter).onLifeUpdate(player4!!.playerIdEnum!!, ClickTypeEnum.SHORT, OperatorEnum.ADD) }
            cmdPlusPlayer4!!.setOnLongClickListener {
                (_presenter as IGamePresenter).onLifeUpdate(player4!!.playerIdEnum!!, ClickTypeEnum.LONG, OperatorEnum.ADD)
                true
            }

            cmdMinusPlayer4!!.setOnClickListener { (_presenter as IGamePresenter).onLifeUpdate(player4!!.playerIdEnum!!, ClickTypeEnum.SHORT, OperatorEnum.SUBSTRACT) }
            cmdMinusPlayer4!!.setOnLongClickListener {
                (_presenter as IGamePresenter).onLifeUpdate(player4!!.playerIdEnum!!, ClickTypeEnum.LONG, OperatorEnum.SUBSTRACT)
                true
            }
        }

        //endregion

        navigationView!!.setNavigationItemSelectedListener { item ->
            val id = item.itemId
            when (id) {
                R.id.nav_settings ->
                    //presenter.onSettingsButtonClick(ClickTypeEnum.LONG);
                    _presenter!!.onMenuEntrySettingsTap()
                R.id.nav_about -> _presenter!!.onMenuEntryAboutTap()
                R.id.nav_energy_save_mode -> _presenter!!.onMenuEntryEnergySaveTap()
                R.id.nav_game_2players -> _presenter!!.onMenuEntryTogglePlayerTap()
                R.id.nav_game_4players -> _presenter!!.onMenuEntryTogglePlayerTap()
                R.id.nav_dicing -> _presenter!!.onMenuEntryDicingTap()
                R.id.nav_countermanager -> _presenter!!.onMenuEntryCounterManagerTap()
                else -> {
                }
            }
            true
        }


        //region Reset Button

        // Reset Button
        cmdResetLP!!.setOnClickListener {
            if (!showResetConfirmation) {
                (_presenter as IGamePresenter).onResetButtonClick()
            } else {

                val confirmed = false

                val alert = AlertDialog.Builder(this@GameActivity)
                alert.setTitle(resources.getString(R.string.dialog_game_reset_confirm_title))
                alert.setMessage(resources.getString(R.string.dialog_game_reset_confirm_message))
                alert.setPositiveButton("Yes") { dialog, which ->
                    dialog.dismiss()
                    (_presenter as IGamePresenter).onResetButtonClick()
                }

                alert.setNegativeButton("No") { dialog, which -> dialog.dismiss() }

                alert.show()
            }
        }

        // endregion

        //region Button Toggle Poison

        cmdTogglePoison!!.setOnClickListener { (_presenter as IGamePresenter).onPoisonButtonClick() }

        //endregion

        //region Poison Buttons Home

        // Poison Home Plus
        cmdPlusPoisonPlayer1!!.setOnClickListener { (_presenter as IGamePresenter).onPoisonUpdate(player1!!.playerIdEnum!!, ClickTypeEnum.SHORT, OperatorEnum.ADD) }
        cmdPlusPoisonPlayer1!!.setOnLongClickListener {
            (_presenter as IGamePresenter).onPoisonUpdate(player1!!.playerIdEnum!!, ClickTypeEnum.LONG, OperatorEnum.ADD)
            true
        }

        // Poison Home Minus
        cmdMinusPoisonPlayer1!!.setOnClickListener { (_presenter as IGamePresenter).onPoisonUpdate(player1!!.playerIdEnum!!, ClickTypeEnum.SHORT, OperatorEnum.SUBSTRACT) }
        cmdMinusPoisonPlayer1!!.setOnLongClickListener {
            (_presenter as IGamePresenter).onPoisonUpdate(player1!!.playerIdEnum!!, ClickTypeEnum.LONG, OperatorEnum.SUBSTRACT)
            true
        }

        //endregion

        //region Poison Buttons Guest

        // Poison Guest Plus
        cmdPlusPoisonPlayer2!!.setOnClickListener { (_presenter as IGamePresenter).onPoisonUpdate(player2!!.playerIdEnum!!, ClickTypeEnum.SHORT, OperatorEnum.ADD) }
        cmdPlusPoisonPlayer2!!.setOnLongClickListener {
            (_presenter as IGamePresenter).onPoisonUpdate(player2!!.playerIdEnum!!, ClickTypeEnum.LONG, OperatorEnum.ADD)
            true
        }

        // Poison Guest Minus
        cmdMinusPoisonPlayer2!!.setOnClickListener { (_presenter as IGamePresenter).onPoisonUpdate(player2!!.playerIdEnum!!, ClickTypeEnum.SHORT, OperatorEnum.SUBSTRACT) }
        cmdMinusPoisonPlayer2!!.setOnLongClickListener {
            (_presenter as IGamePresenter).onPoisonUpdate(player2!!.playerIdEnum!!, ClickTypeEnum.LONG, OperatorEnum.SUBSTRACT)
            true
        }

        //endregion

        if (playerAmount == 4) {
            cmdPlusPoisonPlayer3!!.setOnClickListener { (_presenter as IGamePresenter).onPoisonUpdate(player3!!.playerIdEnum!!, ClickTypeEnum.SHORT, OperatorEnum.ADD) }
            cmdPlusPoisonPlayer3!!.setOnLongClickListener {
                (_presenter as IGamePresenter).onPoisonUpdate(player3!!.playerIdEnum!!, ClickTypeEnum.LONG, OperatorEnum.ADD)
                true
            }

            cmdMinusPoisonPlayer3!!.setOnClickListener { (_presenter as IGamePresenter).onPoisonUpdate(player3!!.playerIdEnum!!, ClickTypeEnum.SHORT, OperatorEnum.SUBSTRACT) }
            cmdMinusPoisonPlayer3!!.setOnLongClickListener {
                (_presenter as IGamePresenter).onPoisonUpdate(player3!!.playerIdEnum!!, ClickTypeEnum.LONG, OperatorEnum.SUBSTRACT)
                true
            }


            cmdPlusPoisonPlayer4!!.setOnClickListener { (_presenter as IGamePresenter).onPoisonUpdate(player4!!.playerIdEnum!!, ClickTypeEnum.SHORT, OperatorEnum.ADD) }
            cmdPlusPoisonPlayer4!!.setOnLongClickListener {
                (_presenter as IGamePresenter).onPoisonUpdate(player4!!.playerIdEnum!!, ClickTypeEnum.LONG, OperatorEnum.ADD)
                true
            }

            cmdMinusPoisonPlayer4!!.setOnClickListener { (_presenter as IGamePresenter).onPoisonUpdate(player4!!.playerIdEnum!!, ClickTypeEnum.SHORT, OperatorEnum.SUBSTRACT) }
            cmdMinusPoisonPlayer4!!.setOnLongClickListener {
                (_presenter as IGamePresenter).onPoisonUpdate(player4!!.playerIdEnum!!, ClickTypeEnum.LONG, OperatorEnum.SUBSTRACT)
                true
            }
        }

        //region Settings Button
        cmdToggleColorSettings!!.setOnClickListener { (_presenter as IGamePresenter).onSettingsButtonClick(ClickTypeEnum.SHORT) }
        cmdToggleColorSettings!!.setOnLongClickListener {
            (_presenter as IGamePresenter).onSettingsButtonClick(ClickTypeEnum.LONG)
            true
        }
    }
}