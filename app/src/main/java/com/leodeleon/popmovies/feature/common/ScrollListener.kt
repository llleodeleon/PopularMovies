package com.leodeleon.popmovies.feature.common

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.LayoutManager

class ScrollListener(val layoutManager: LayoutManager, val f: () -> Unit ) {

  private var previousTotal = 0
  private var loading = true
  private var visibleThreshold = 2
  private var firstVisibleItem = 0
  private var visibleItemCount = 0
  private var totalItemCount = 0

  fun loadMore() {
    visibleItemCount = layoutManager.childCount
    totalItemCount = layoutManager.itemCount
    if (layoutManager is GridLayoutManager) {
      firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
    } else if (layoutManager is LinearLayoutManager) {
      firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
    }

    if (loading) {
      if (totalItemCount > previousTotal) {
        loading = false
        previousTotal = totalItemCount
      }
    }

    if (!loading &&
        (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
      f()
      loading = true
    }
  }
}