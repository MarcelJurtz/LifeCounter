package com.marceljurtz.lifecounter.contracts.base

import com.marceljurtz.lifecounter.contracts.INavDrawerInteraction

interface IPresenter : INavDrawerInteraction {
    fun onCreate()
    fun onPause()
    fun onResume()
    fun onDestroy()
}
