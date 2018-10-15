package com.yuandi.lbrary.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created by EdgeDi
 * 2018/3/15 9:58
 */
abstract class BaseFragment : Fragment() {

    val view_layout by lazy { LayoutInflater.from(context).inflate(layout(), null) }
    private var create_status = false
    private var create = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = view_layout

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        create_status = true
        if (userVisibleHint && create_status && !create) {
            init()
            initUI()
            setListener()
            create = true
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && create_status && !create) {
            init()
            initUI()
            setListener()
            create = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        create = false
    }

    abstract fun layout(): Int

    abstract fun init()

    abstract fun initUI()

    abstract fun setListener()

}