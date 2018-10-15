package com.yuandi.lbrary.util

import android.app.Activity
import android.view.View
import android.view.ViewGroup

/**
 * Created by EdgeDi
 * 2018/3/15 10:24
 */
object ExpandUtil {

    fun createStatusBarView(activity: Activity, color: Int): View {
        val view = View(activity)
        val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity))
        view.layoutParams = params
        view.setBackgroundColor(color)
        return view
    }

    private fun getStatusBarHeight(activity: Activity) =
            activity.resources.getDimensionPixelOffset(activity.resources.getIdentifier("status_bar_height", "dimen", "android"))

    fun setRootView(activity: Activity) {
        val rootView = activity.window.decorView as ViewGroup
        rootView.fitsSystemWindows = true
    }
}