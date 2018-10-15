package com.yuandi.lbrary.base

import android.graphics.Color
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import com.yuandi.lbrary.util.setBarColor
import com.yuandi.lbrary.util.setImageColor

/**
 * Created by EdgeDi
 * 2018/3/15 10:25
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        if (getStatus()) {
//            setBarColor(getColor())
//        } else {
//            setImageColor()
//        }
        setContentView(layout())
    }

    override fun onContentChanged() {
        super.onContentChanged()
        initUI()
        setListener()
    }

    @LayoutRes abstract fun layout(): Int

    abstract fun initUI()

    abstract fun setListener()

    protected open fun getColor() = Color.parseColor("#080808")

    /**
     * true为颜色
     * false为图片
     */
    protected open fun getStatus() = true
}