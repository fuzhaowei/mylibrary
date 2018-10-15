package com.yuandi.lbrary.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.yuandi.lbrary.holder.ViewHolder

/**
 * Created by EdgeDi
 * 2018/3/19 15:00
 */
abstract class BaseLGAdapter<T>(val context: Context, val layout: Int, var list: List<T>) : BaseAdapter() {

    private val inflater by lazy { LayoutInflater.from(context) }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): T {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val viewHolder: ViewHolder
        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false)
            viewHolder = ViewHolder(convertView)
            convertView!!.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
        }
        initialise(viewHolder, getItem(position), position)
        return viewHolder.itemView
    }

    protected abstract fun initialise(holder: ViewHolder, item: T, position: Int)
}