package com.marceljurtz.lifecounter.models

import android.view.View
import android.view.ViewGroup

object ViewHelper {

    var lblDescriptionTag = "description"
    var lblAtkTag = "atk"
    var lblDefTag = "def"

    fun findFirstViewByTag(root: ViewGroup, tag: String): View? {

        val childCount = root.childCount
        for (i in 0 until childCount) {
            val childView = root.getChildAt(i)

            if (childView.tag != null && childView.tag.toString() == tag) {
                return childView
            }
        }

        return null
    }
}
