package com.yuandi.lbrary.util

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.res.Configuration

/**
 * Created by EdgeDi
 * 2018/8/3 11:21
 */
class DisplayUtil {

    private var Density = 0f
    private var ScaledDensity = 0f

    fun ChangeDensity(activity: Activity, application: Application, width: Int = 360, height: Int = 160) {
        val appMetrics = application.resources.displayMetrics
        if (Density == 0f) {
            Density = appMetrics.density
            ScaledDensity = appMetrics.scaledDensity
            application.registerComponentCallbacks(object : ComponentCallbacks {
                override fun onLowMemory() {
                }

                override fun onConfigurationChanged(newConfig: Configuration?) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        ScaledDensity = application.resources.displayMetrics.scaledDensity
                    }
                }

            })
        }
        val targetDensity = appMetrics.widthPixels / width
        val scaledDensity = targetDensity * (ScaledDensity / Density)
        val DensityDpi = (Density * height).toInt()
        with(appMetrics) {
            density = targetDensity.toFloat()
            this.scaledDensity = scaledDensity
            densityDpi = DensityDpi
        }
        val activityMetrics = activity.resources.displayMetrics
        with(activityMetrics) {
            density = targetDensity.toFloat()
            this.scaledDensity = scaledDensity
            densityDpi = DensityDpi
        }
    }

}
