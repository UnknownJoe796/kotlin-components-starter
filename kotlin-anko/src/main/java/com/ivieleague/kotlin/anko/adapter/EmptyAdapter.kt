package com.ivieleague.kotlin.anko.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

/**
 * Created by jivie on 2/29/16.
 */
class EmptyAdapter(val viewMaker: () -> View) : BaseAdapter() {
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View? = viewMaker()

    override fun getItem(p0: Int): Any? = null

    override fun getItemId(p0: Int): Long = 0

    override fun getCount(): Int = 1
}