package com.ivieleague.kotlin.anko

import android.content.Context
import android.database.DataSetObserver
import android.view.ViewManager
import android.widget.LinearLayout
import android.widget.ListAdapter
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.onClick
import org.jetbrains.anko.onLongClick
import org.jetbrains.anko.wrapContent

/**
 * A linear layout that can use an adapter.
 * Created by jivie on 8/12/15.
 */
open class AdapterLinearLayout(context: Context, val stretchMode: Boolean) : LinearLayout(context) {

    init {
        orientation = LinearLayout.VERTICAL
    }

    private var _adapter: ListAdapter? = null
    var adapter: ListAdapter?
        get() = _adapter
        set(value) {
            _adapter = value
            _adapter?.registerDataSetObserver(observer)
            notifyDataSetChanged()
        }

    fun notifyDataSetChanged() {
        removeAllViews()
        val _adapter = _adapter ?: return
        for (i in 0.._adapter.count - 1) {
            val view = _adapter.getView(i, null, this)
            view.onClick {
                onItemClick.invoke(this, view, i, _adapter.getItemId(i))
            }
            view.onLongClick {
                onItemLongClick.invoke(this, view, i, _adapter.getItemId(i))
            }
            addView(view, getParams())
        }
    }

    private fun getParams(): LinearLayout.LayoutParams {
        if (orientation == LinearLayout.VERTICAL) {
            if (stretchMode) {
                return LinearLayout.LayoutParams(matchParent, 0, 1f)
            } else {
                return LinearLayout.LayoutParams(matchParent, wrapContent)
            }
        } else {
            if (stretchMode) {
                return LinearLayout.LayoutParams(0, matchParent, 1f)
            } else {
                return LinearLayout.LayoutParams(wrapContent, matchParent)
            }
        }
    }

    var observer: DataSetObserver = object : DataSetObserver() {
        override fun onInvalidated() {
            notifyDataSetChanged()
        }

        override fun onChanged() {
            notifyDataSetChanged()
        }
    }

    var onItemClick: (parent: AdapterLinearLayout, view: android.view.View?, position: Int, id: Long) -> Unit = { a, b, c, d -> }
    var onItemLongClick: (parent: AdapterLinearLayout, view: android.view.View?, position: Int, id: Long) -> Boolean = { a, b, c, d -> true }
    fun onItemClick(func: (parent: AdapterLinearLayout, view: android.view.View?, position: Int, id: Long) -> Unit) {
        onItemClick = func
    }

    fun onItemLongClick(func: (AdapterLinearLayout, view: android.view.View?, position: Int, id: Long) -> Boolean) {
        onItemLongClick = func
    }

    @Suppress("UNCHECKED_CAST") fun <T> onItemClick(func: (item: T) -> Unit) {
        onItemClick { parent, view, position, id ->
            val item = adapter?.getItem(position) as T
            if (item != null) func(item)
        }
    }

    @Suppress("UNCHECKED_CAST") fun <T> onItemLongClick(func: (item: T) -> Boolean) {
        onItemLongClick { parent, view, position, id ->
            val item = adapter?.getItem(position) as T
            if (item != null) func(item) else false
        }
    }
}

@Suppress("NOTHING_TO_INLINE") inline fun ViewManager.adapterLinearLayout() = adapterLinearLayout {}
inline fun ViewManager.adapterLinearLayout(stretchMode: Boolean = false, init: AdapterLinearLayout.() -> Unit): AdapterLinearLayout {
    return ankoView({ AdapterLinearLayout(it, stretchMode) }, init)
}