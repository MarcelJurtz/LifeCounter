package com.marceljurtz.lifecounter.views.Base

import android.content.Intent
import android.net.Uri
import android.view.Menu
import android.view.MenuItem

import com.google.android.material.navigation.NavigationView
import com.marceljurtz.lifecounter.R
import com.marceljurtz.lifecounter.contracts.base.IPresenter
import com.marceljurtz.lifecounter.contracts.base.IView
import com.marceljurtz.lifecounter.models.AppDetails

import androidx.appcompat.app.AppCompatActivity

abstract class View : AppCompatActivity(), IView {

    protected var _presenter: IPresenter? = null

    private var _navigationView: NavigationView? = null
    private var _navMenu: Menu? = null

    override fun loadActivity(c: Class<*>) {
        val intent = Intent(applicationContext, c)
        finish()
        startActivity(intent)
    }

    override fun startBrowserIntent(url: String) {
        val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(url))
        startActivity(browserIntent)
    }

    override fun onDestroy() {
        if (_presenter != null)
            _presenter!!.onDestroy()

        super.onDestroy()
    }

    override fun onPause() {
        if (_presenter != null)
            _presenter!!.onPause()

        super.onPause()
    }

    override fun onResume() {
        super.onResume()

        if (_presenter != null)
            _presenter!!.onResume()
    }

    fun disableMenuItem(view: NavigationView, menuItemId: Int) {
        if (_navigationView == null) {
            _navigationView = view
            _navMenu = _navigationView!!.menu
        }

        _navMenu!!.findItem(menuItemId).isVisible = false
    }
}
