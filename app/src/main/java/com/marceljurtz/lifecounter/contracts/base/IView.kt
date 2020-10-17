package com.marceljurtz.lifecounter.contracts.base

interface IView {
    fun loadActivity(c: Class<*>)
    fun startBrowserIntent(url: String)
}
