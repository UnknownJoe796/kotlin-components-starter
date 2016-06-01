package com.ivieleague.kotlin.anko

import android.widget.AbsListView
import android.widget.ListView

/**
 * When attached as a ListView scroll listener, it can be used to obtain information about where
 * the list view is scrolled to and what items are visible.
 * Created by jivie on 10/23/15.
 */
class ScrollStatus() : AbsListView.OnScrollListener {

    private var _firstVisibleItem: Int = 0
    private var _visibleItemCount: Int = 0
    private var _totalItemCount: Int = 0

    val firstVisibleItem: Int get() = _firstVisibleItem
    val visibleItemCount: Int get() = _visibleItemCount
    val totalItemCount: Int get() = _totalItemCount
    val lastVisibleItem: Int get() = _firstVisibleItem + _visibleItemCount
    val isAtBottom: Boolean get() {
        return _totalItemCount <= _firstVisibleItem + _visibleItemCount
    }
    val isAtTop: Boolean get() = _firstVisibleItem == 0

    override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
        _firstVisibleItem = firstVisibleItem
        _visibleItemCount = visibleItemCount
        _totalItemCount = totalItemCount
    }

    override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
    }

}

fun ListView.scrollStatus(): ScrollStatus {
    val listener = ScrollStatus()
    setOnScrollListener(listener)
    return listener
}