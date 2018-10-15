package com.yuandi.lbrary.network

/**
 * Created by EdgeDi
 * 2018/8/3 14:49
 * 请求数据回调
 */
interface OnDataListener<in T> {

    fun Error(e: String?)

    fun Success(content: T)

}
