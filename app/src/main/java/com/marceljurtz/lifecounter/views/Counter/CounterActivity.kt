package com.marceljurtz.lifecounter.views.Counter

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Spinner
import android.widget.TextView

import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.marceljurtz.lifecounter.enums.CounterType
import com.marceljurtz.lifecounter.models.Color
import com.marceljurtz.lifecounter.models.Counter
import com.marceljurtz.lifecounter.models.Player
import com.marceljurtz.lifecounter.enums.PlayerIdEnum
import com.marceljurtz.lifecounter.models.PreferenceManager
import com.marceljurtz.lifecounter.models.ViewHelper
import com.marceljurtz.lifecounter.R

import java.io.InvalidObjectException
import java.util.ArrayList

class CounterActivity : com.marceljurtz.lifecounter.views.Base.View(), ICounterView {

    internal val player1: Player = Player(PlayerIdEnum.ONE)
    internal val player2: Player = Player(PlayerIdEnum.TWO)
    internal val player3: Player = Player(PlayerIdEnum.THREE)
    internal val player4: Player = Player(PlayerIdEnum.FOUR)

    internal val players: ArrayList<Player> = arrayListOf(player1, player2, player3, player4)

    internal val rlCounter: RelativeLayout = findViewById<View>(R.id.rlCounter) as RelativeLayout
    internal val player1Layout: LinearLayout = findViewById<View>(R.id.llCounterPlayer1) as LinearLayout
    internal val player2Layout: LinearLayout = findViewById<View>(R.id.llCounterPlayer2) as LinearLayout
    internal val player3Layout: LinearLayout = findViewById<View>(R.id.llCounterPlayer3) as LinearLayout
    internal val player4Layout: LinearLayout = findViewById<View>(R.id.llCounterPlayer4) as LinearLayout

    internal val lblCounterHeaderPlayer1: TextView = findViewById<View>(R.id.lblCountersPlayer1) as TextView
    internal val lblCounterHeaderPlayer2: TextView = findViewById<View>(R.id.lblCountersPlayer2) as TextView
    internal val lblCounterHeaderPlayer3: TextView = findViewById<View>(R.id.lblCountersPlayer3) as TextView
    internal val lblCounterHeaderPlayer4: TextView = findViewById<View>(R.id.lblCountersPlayer4) as TextView

    internal val navigationView: NavigationView = findViewById<View>(R.id.navigationViewCountermanager) as NavigationView

    var counterToAdd: Counter? = null
    var counterToEdit: Counter? = null

    internal val adapter: ArrayAdapter<Player> = ArrayAdapter(applicationContext, R.layout.spinner_item)

    private val preferences: SharedPreferences = applicationContext.getSharedPreferences(PreferenceManager.PREFS, Activity.MODE_PRIVATE)

    private var modalMode = CounterType.Counter

    // FAB Add item
    private val fabMain: FloatingActionButton = findViewById(R.id.fab)
    private val fabCt: FloatingActionButton = findViewById(R.id.fabCounter)
    private val fabPw: FloatingActionButton = findViewById(R.id.fabPlaneswalker)

    private val fabOpen: Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_open)
    private val fabClose: Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_close)
    private val fabCw: Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_rotate_cw)
    private val fabCcw: Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_rotate_ccw)

    internal val txtCt: TextView = findViewById<View>(R.id.txtCounter) as TextView
    internal val txtPw: TextView = findViewById<View>(R.id.txtPlaneswalker) as TextView

    internal var isOpen: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counter)

        _presenter = CounterPresenter(this, preferences, players)

        lblCounterHeaderPlayer1.setOnClickListener { (_presenter as ICounterPresenter).onPlayerIdentificationTap(player1.playerIdEnum!!) }
        lblCounterHeaderPlayer1.setOnLongClickListener {
            (_presenter as ICounterPresenter).onPlayerIdentificationLongTap(player1.playerIdEnum!!)
            true
        }

        lblCounterHeaderPlayer2.setOnClickListener { (_presenter as ICounterPresenter).onPlayerIdentificationTap(player2.playerIdEnum!!) }
        lblCounterHeaderPlayer2.setOnLongClickListener {
            (_presenter as ICounterPresenter).onPlayerIdentificationLongTap(player2.playerIdEnum!!)
            true
        }

        lblCounterHeaderPlayer3.setOnClickListener { (_presenter as ICounterPresenter).onPlayerIdentificationTap(player3.playerIdEnum!!) }
        lblCounterHeaderPlayer3.setOnLongClickListener {
            (_presenter as ICounterPresenter).onPlayerIdentificationLongTap(player3.playerIdEnum!!)
            true
        }

        lblCounterHeaderPlayer4.setOnClickListener { (_presenter as ICounterPresenter).onPlayerIdentificationTap(player4.playerIdEnum!!) }
        lblCounterHeaderPlayer4.setOnLongClickListener {
            (_presenter as ICounterPresenter).onPlayerIdentificationLongTap(player4.playerIdEnum!!)
            true
        }

        adapter.setDropDownViewResource(R.layout.spinner_item)

        _presenter!!.onCreate()

        fabMain!!.setOnClickListener {
            if (isOpen) {
                txtPw.visibility = View.INVISIBLE
                txtCt.visibility = View.INVISIBLE
                fabPw!!.startAnimation(fabClose)
                fabCt!!.startAnimation(fabClose)
                fabMain!!.startAnimation(fabCcw)
            } else {
                txtPw.visibility = View.VISIBLE
                txtCt.visibility = View.VISIBLE
                fabPw!!.startAnimation(fabOpen)
                fabCt!!.startAnimation(fabOpen)
                fabMain!!.startAnimation(fabCw)
            }

            fabPw!!.isClickable = !isOpen
            fabCt!!.isClickable = !isOpen

            isOpen = !isOpen
        }

        fabCt!!.setOnClickListener {
            (_presenter as ICounterPresenter).onFabCtTap()
            fabMain!!.callOnClick()
        }

        fabPw!!.setOnClickListener {
            (_presenter as ICounterPresenter).onFabPwTap()
            fabMain!!.callOnClick()
        }

        disableMenuItem(navigationView, R.id.nav_energy_save_mode)
        disableMenuItem(navigationView, R.id.nav_countermanager)

        navigationView.setNavigationItemSelectedListener { item ->
            val id = item.itemId
            when (id) {
                R.id.nav_game_2players -> _presenter!!.onMenuEntryTwoPlayerTap()
                R.id.nav_game_4players -> _presenter!!.onMenuEntryFourPlayerTap()
                R.id.nav_countermanager -> _presenter!!.onMenuEntryCounterManagerTap()
                R.id.nav_settings -> _presenter!!.onMenuEntrySettingsTap()
                R.id.nav_dicing -> _presenter!!.onMenuEntryDicingTap()
                R.id.nav_about -> _presenter!!.onMenuEntryAboutTap()
                else -> {
                }
            }
            true
        }
    }

    override fun loadCounterAddDialog(players: ArrayList<Player>, counterType: CounterType) {

        adapter.clear()
        adapter.addAll(players)

        val dialog = Dialog(this@CounterActivity)
        dialog.setContentView(R.layout.dialog_countermanager_new)

        val txtCardDescription = dialog.findViewById<View>(R.id.txtCardDescription) as EditText
        val txtATK = dialog.findViewById<View>(R.id.txtCardAtk) as EditText
        val txtDEF = dialog.findViewById<View>(R.id.txtCardDef) as EditText
        val lblDivider = dialog.findViewById<View>(R.id.lblDivider) as TextView
        val spPlayers = dialog.findViewById<View>(R.id.spUserSelection) as Spinner
        val cmdIncrease = dialog.findViewById<View>(R.id.cmdIncreaseCounter) as ImageButton
        val cmdDecrease = dialog.findViewById<View>(R.id.cmdDecreaseCounter) as ImageButton

        if (counterType == CounterType.Planeswalker) {
            txtATK.visibility = View.GONE
            lblDivider.visibility = View.GONE
        }

        modalMode = counterType

        spPlayers.adapter = adapter

        cmdIncrease.setOnClickListener {
            try {
                txtATK.setText(Integer.toString(Integer.parseInt(txtATK.text.toString()) + 1))
            } catch (e: NumberFormatException) {
                txtATK.setText("1")
            }

            try {
                txtDEF.setText(Integer.toString(Integer.parseInt(txtDEF.text.toString()) + 1))
            } catch (e: NumberFormatException) {
                txtDEF.setText("1")
            }
        }

        cmdDecrease.setOnClickListener {
            try {
                txtATK.setText(Integer.toString(Integer.parseInt(txtATK.text.toString()) - 1))
            } catch (e: NumberFormatException) {
                txtATK.setText("0")
            }

            try {
                txtDEF.setText(Integer.toString(Integer.parseInt(txtDEF.text.toString()) - 1))
            } catch (e: NumberFormatException) {
                txtDEF.setText("0")
            }
        }

        val cmdDialogOk = dialog.findViewById<View>(R.id.dialogButtonOK) as Button
        cmdDialogOk.setOnClickListener {

            try {
                var counter = Counter(txtCardDescription.text.toString(),
                        Integer.parseInt(txtATK.text.toString()),
                        Integer.parseInt(txtDEF.text.toString()),
                        counterType)

                (_presenter as ICounterPresenter).addCounter((spPlayers.selectedItem as Player).playerIdEnum!!, counter)
            } catch (ex: NullPointerException) {
                displayErrorMessage(getString(R.string.dialog_countermanager_error_invalid_entry))
            } catch (ex: NumberFormatException) {
                displayErrorMessage(getString(R.string.dialog_countermanager_error_invalid_entry))
            }

            dialog.dismiss()
        }

        // Delete button only for editing entries
        val cmdDialogDelete = dialog.findViewById<View>(R.id.dialogButtonDelete) as Button
        cmdDialogDelete.visibility = View.INVISIBLE

        dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

        dialog.show()
    }

    override fun addCounter(playerIdEnum: PlayerIdEnum, counter: Counter) {
        val paddingLeft = 50
        val paddingRight = 50
        val fontSize = 24

        val llParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        val llParamsSpacer = LinearLayout.LayoutParams(0, 0, 1f)

        val wrapper = LinearLayout(applicationContext)
        wrapper.tag = counter.identifier
        wrapper.orientation = LinearLayout.HORIZONTAL
        wrapper.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        wrapper.setPadding(paddingLeft, 0, paddingRight, 0)

        val lblDescription = TextView(applicationContext)
        lblDescription.text = counter.description
        lblDescription.layoutParams = llParams
        lblDescription.textSize = fontSize.toFloat()
        lblDescription.setTextColor(resources.getColor(R.color.textColor))
        lblDescription.tag = ViewHelper.lblDescriptionTag
        wrapper.addView(lblDescription)

        val spacer = View(applicationContext)
        spacer.layoutParams = llParamsSpacer
        wrapper.addView(spacer)

        if (counter.counterType == CounterType.Counter) {
            val lblATK = TextView(applicationContext)
            lblATK.setText(counter.atk)
            lblATK.layoutParams = llParams
            lblATK.textSize = fontSize.toFloat()
            lblATK.setTextColor(resources.getColor(R.color.textColor))
            lblATK.tag = ViewHelper.lblAtkTag
            wrapper.addView(lblATK)

            val lblDivider = TextView(applicationContext)
            lblDivider.text = " / "
            lblDivider.layoutParams = llParams
            lblDivider.textSize = fontSize.toFloat()
            lblDivider.setTextColor(resources.getColor(R.color.textColor))
            wrapper.addView(lblDivider)
        }

        val lblDEF = TextView(applicationContext)
        lblDEF.setText(counter.def)
        lblDEF.layoutParams = llParams
        lblDEF.textSize = fontSize.toFloat()
        lblDEF.setTextColor(resources.getColor(R.color.textColor))
        lblDEF.tag = ViewHelper.lblDefTag
        wrapper.addView(lblDEF)

        wrapper.setOnClickListener { (_presenter as ICounterPresenter).onCounterTap(wrapper) }

        wrapper.setOnLongClickListener {
            (_presenter as ICounterPresenter).onCounterLongTap(wrapper)
            true
        }

        when (playerIdEnum) {
            PlayerIdEnum.ONE -> {
                player1Layout.addView(wrapper)
                player1Layout.visibility = View.VISIBLE
            }
            PlayerIdEnum.TWO -> {
                player2Layout.addView(wrapper)
                player2Layout.visibility = View.VISIBLE
            }
            PlayerIdEnum.THREE -> {
                player3Layout.addView(wrapper)
                player3Layout.visibility = View.VISIBLE
            }
            PlayerIdEnum.FOUR -> {
                player4Layout.addView(wrapper)
                player4Layout.visibility = View.VISIBLE
            }
            else -> {
            }
        }
    }

    override fun loadPlayerIdentificationDialog(playerIdEnum: PlayerIdEnum, playername: String) {
        val dialog = Dialog(this@CounterActivity)
        dialog.setContentView(R.layout.dialog_countermanager_playerdescription)

        val txtPlayerDescription = dialog.findViewById<View>(R.id.txtCounterPlayerDescription) as EditText
        txtPlayerDescription.setText(playername)

        val dialogButton = dialog.findViewById<View>(R.id.cmdCounterPlayerDescrptionDialogOK) as Button

        dialogButton.setOnClickListener {
            counterToAdd = null
            val newDescription = txtPlayerDescription.text.toString()
            dialog.dismiss()

            if (newDescription != null && newDescription.length > 0) {
                (_presenter as ICounterPresenter).onPlayerIdentificationChangeConfirmed(playerIdEnum, newDescription)
            }
        }

        dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

        dialog.show()
    }

    override fun setPlayerIdentificationText(playerIdEnum: PlayerIdEnum, headerText: String) {
        when (playerIdEnum) {
            PlayerIdEnum.ONE -> lblCounterHeaderPlayer1.text = headerText
            PlayerIdEnum.TWO -> lblCounterHeaderPlayer2.text = headerText
            PlayerIdEnum.THREE -> lblCounterHeaderPlayer3.text = headerText
            PlayerIdEnum.FOUR -> lblCounterHeaderPlayer4.text = headerText
            else -> {
            }
        }
    }

    override fun loadCounterDeletionDialog(wrapperLayout: LinearLayout) {
        AlertDialog.Builder(this@CounterActivity)
                .setTitle(resources.getString(R.string.dialog_countermanager_delete_title))
                .setMessage(resources.getString(R.string.dialog_countermanager_delete_message))
                .setPositiveButton(resources.getString(R.string.yes)) { dialog, whichButton -> (_presenter as ICounterPresenter).onCounterDeletionConfirmed(wrapperLayout) }
                .setNegativeButton(resources.getString(R.string.no), null).show()
    }

    override fun loadPlayerDeletionDialog(playerIdEnum: PlayerIdEnum) {
        AlertDialog.Builder(this@CounterActivity)
                .setTitle(resources.getString(R.string.dialog_countermanager_delete_title))
                .setMessage(resources.getString(R.string.dialog_countermanager_delete_multiple_message))
                .setPositiveButton(resources.getString(R.string.yes)) { dialog, whichButton -> (_presenter as ICounterPresenter).onPlayerDeletionConfirmed(playerIdEnum) }
                .setNegativeButton(resources.getString(R.string.no), null).show()
    }

    override fun deleteAllCounters() {
        deleteAllCountersForPlayer(player1.playerIdEnum!!)
        deleteAllCountersForPlayer(player2.playerIdEnum!!)
        deleteAllCountersForPlayer(player3.playerIdEnum!!)
        deleteAllCountersForPlayer(player4.playerIdEnum!!)
    }

    override fun deleteAllCountersForPlayer(playerIdEnum: PlayerIdEnum) {
        when (playerIdEnum) {
            PlayerIdEnum.ONE -> removeAllCounterViewsAndHideLayout(player1Layout, R.id.lblCountersPlayer1)
            PlayerIdEnum.TWO -> removeAllCounterViewsAndHideLayout(player2Layout, R.id.lblCountersPlayer2)
            PlayerIdEnum.THREE -> removeAllCounterViewsAndHideLayout(player3Layout, R.id.lblCountersPlayer3)
            PlayerIdEnum.FOUR -> removeAllCounterViewsAndHideLayout(player4Layout, R.id.lblCountersPlayer4)
            else -> {
            }
        }
    }

    private fun removeAllCounterViewsAndHideLayout(layout: ViewGroup, layoutIdentifier: Int) {
        val viewsToRemove = ArrayList<View>()

        for (i in 0 until layout.childCount) {
            val v = layout.getChildAt(i)
            if (v.id != layoutIdentifier) {
                viewsToRemove.add(v)
            }
        }

        for (v in viewsToRemove) {
            layout.removeView(v)
        }

        viewsToRemove.clear()
        layout.visibility = View.GONE
    }

    override fun deleteCounter(counterLayout: LinearLayout, deleteParent: Boolean) {
        if (deleteParent) {
            (counterLayout.parent as ViewGroup).visibility = View.GONE
        }
        (counterLayout.parent as ViewGroup).removeView(counterLayout)
    }

    override fun loadCounterEditDialog(counterLayout: LinearLayout, player: Player, counter: Counter) {
        adapter.clear()
        //adapter.addAll(players);
        adapter.add(player)

        val identifier = counter.identifier

        val dialog = Dialog(this@CounterActivity)
        dialog.setContentView(R.layout.dialog_countermanager_new)

        val txtCardDescription = dialog.findViewById<View>(R.id.txtCardDescription) as EditText
        txtCardDescription.setText(counter.description)

        val txtATK = dialog.findViewById<View>(R.id.txtCardAtk) as EditText
        txtATK.setText(Integer.toString(counter.atk))

        val txtDEF = dialog.findViewById<View>(R.id.txtCardDef) as EditText
        txtDEF.setText(Integer.toString(counter.def))

        val spPlayers = dialog.findViewById<View>(R.id.spUserSelection) as Spinner
        spPlayers.adapter = adapter
        spPlayers.isEnabled = false

        val cmdIncrease = dialog.findViewById<View>(R.id.cmdIncreaseCounter) as ImageButton
        val cmdDecrease = dialog.findViewById<View>(R.id.cmdDecreaseCounter) as ImageButton

        cmdIncrease.setOnClickListener {
            try {
                txtATK.setText(Integer.toString(Integer.parseInt(txtATK.text.toString()) + 1))
            } catch (e: NumberFormatException) {
                txtATK.setText("1")
            }

            try {
                txtDEF.setText(Integer.toString(Integer.parseInt(txtDEF.text.toString()) + 1))
            } catch (e: NumberFormatException) {
                txtDEF.setText("1")
            }
        }

        cmdDecrease.setOnClickListener {
            try {
                txtATK.setText(Integer.toString(Integer.parseInt(txtATK.text.toString()) - 1))
            } catch (e: NumberFormatException) {
                txtATK.setText("0")
            }

            try {
                txtDEF.setText(Integer.toString(Integer.parseInt(txtDEF.text.toString()) - 1))
            } catch (e: NumberFormatException) {
                txtDEF.setText("0")
            }
        }

        val dialogButton = dialog.findViewById<View>(R.id.dialogButtonOK) as Button
        dialogButton.setOnClickListener {
            try {
                var counter = Counter(txtCardDescription.text.toString(),
                        Integer.parseInt(txtATK.text.toString()),
                        Integer.parseInt(txtDEF.text.toString()),
                        modalMode)

                (_presenter as ICounterPresenter).onCounterEditCompleted((spPlayers.selectedItem as Player).playerIdEnum!!, counter.identifier!!, counter)

            } catch (ex: NullPointerException) {
                Snackbar.make(findViewById(android.R.id.content), R.string.dialog_countermanager_error_invalid_entry, Snackbar.LENGTH_LONG).show()
            } catch (ex: NumberFormatException) {
                Snackbar.make(findViewById(android.R.id.content), R.string.dialog_countermanager_error_invalid_entry, Snackbar.LENGTH_LONG).show()
            }

            dialog.dismiss()
        }

        val cmdDialogDelete = dialog.findViewById<View>(R.id.dialogButtonDelete) as Button
        cmdDialogDelete.setOnClickListener {
            dialog.dismiss()
            (_presenter as ICounterPresenter).onCounterLongTap(counterLayout)
        }

        dialog.show()
    }

    override fun updateCounterView(player: Player, counter: Counter) {
        val currentPlayerLayout: LinearLayout

        try {
            when (player.playerIdEnum) {
                PlayerIdEnum.ONE -> currentPlayerLayout = player1Layout
                PlayerIdEnum.TWO -> currentPlayerLayout = player2Layout
                PlayerIdEnum.THREE -> currentPlayerLayout = player3Layout
                PlayerIdEnum.FOUR -> currentPlayerLayout = player4Layout
                else -> throw InvalidObjectException(getString(R.string.exc_invalid_object_player))
            }

            val counterView = ViewHelper.findFirstViewByTag(currentPlayerLayout, counter.identifier!!) as LinearLayout?
                    ?: throw InvalidObjectException(getString(R.string.exc_invalid_object_counter))

            // Update Description
            (ViewHelper.findFirstViewByTag(counterView, ViewHelper.lblDescriptionTag) as TextView).text = counter.description

            // Update ATK
            if (counter.counterType == CounterType.Counter) {
                (ViewHelper.findFirstViewByTag(counterView, ViewHelper.lblAtkTag) as TextView).text = Integer.toString(counter.atk)
            }

            // Update DEF
            (ViewHelper.findFirstViewByTag(counterView, ViewHelper.lblDefTag) as TextView).text = Integer.toString(counter.def)

        } catch (ex: Exception) {
            displayErrorMessage(getString(R.string.dialog_countermanager_edit_counter_error))
        }

    }

    private fun displayErrorMessage(errormessage: String) {
        Snackbar.make(findViewById(android.R.id.content), errormessage, Snackbar.LENGTH_LONG).show()
    }

    override fun setBackgroundColor(color: Color) {
        rlCounter.setBackgroundColor(color.intValue)
    }
}
