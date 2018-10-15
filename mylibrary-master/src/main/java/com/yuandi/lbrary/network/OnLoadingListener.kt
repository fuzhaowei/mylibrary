package com.yuandi.lbrary.network

import java.io.File

/**
 * Created by EdgeDi
 * 2018/8/3 14:50
 * 下载回调
 */
interface OnLoadingListener {

    fun onSuccess(file: File)

    fun onError(e: String)

    fun onProgress(progress: Float)

}
