@file:Suppress("NOTHING_TO_INLINE")

package com.yuandi.lbrary.util

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v4.app.Fragment
import android.view.ViewGroup
import android.view.WindowManager
import org.jetbrains.anko.internals.AnkoInternals
import org.jetbrains.anko.support.v4.act

/**
 * Created by EdgeDi
 * 2018/3/15 10:10
 */
inline fun Fragment.Skip(clazz: Class<*>) = startActivity(Intent(context, clazz))

inline fun Activity.Skip(clazz: Class<*>) = startActivity(Intent(this, clazz))

inline fun Activity.setBarColor(color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = color
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        val view_group = window.decorView as ViewGroup
        view_group.addView(ExpandUtil.createStatusBarView(this, color))
        ExpandUtil.setRootView(this)
    }
}

inline fun Activity.setImageColor() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = Color.TRANSPARENT
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }
}

inline fun <reified T : Activity> Fragment.startActivity(vararg params: Pair<String, Any?>) =
        AnkoInternals.internalStartActivity(act, T::class.java, params)

inline fun <reified T : Activity> Fragment.startActivityForResult(requestCode: Int, vararg params: Pair<String, Any?>) =
        startActivityForResult(AnkoInternals.createIntent(act, T::class.java, params), requestCode)