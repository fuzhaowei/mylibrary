package com.yuandi.lbrary.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.util.regex.Pattern

/**
 * Created by EdgeDi
 * 2018/7/19 10:59
 */
class TrendsUrlInterceptor : Interceptor {

    private val pattern = Pattern.compile("[/]")

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url().toString()
        val matcher = pattern.matcher(url)
        var end = 0
        while (matcher.find()) {
            end = matcher.end()
        }
        val change = url.substring(0, end)
        val value = url.substring(end, url.length)
        if (request.header("ed_change") == "url" && TrendsUrl.getUrl() != change) {
            Log.d("old change", change)
            Log.d("new change", TrendsUrl.getUrl())
            request = request.newBuilder()
                    .url(TrendsUrl.getUrl() + value)
                    .build()
            Log.d("end change", TrendsUrl.getUrl() + value)
        }
        return chain.proceed(request)
    }

}
