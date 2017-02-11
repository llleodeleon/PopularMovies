package com.leodeleon.popmovies.listeners;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by leodeleon on 09/02/2017.
 */

public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {

  private int previousTotal = 0;
  private int visibleThreshold = 2;
  private boolean loading = true;
  private int firstVisibleItem, totalItemCount;

  @Override
  public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
    super.onScrolled(recyclerView, dx, dy);

    RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();

    int visibleItemCount = recyclerView.getChildCount();
    if (manager instanceof GridLayoutManager) {
      GridLayoutManager gridLayoutManager = (GridLayoutManager)manager;
      firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition();
      totalItemCount = gridLayoutManager.getItemCount();
    } else if (manager instanceof LinearLayoutManager) {
      LinearLayoutManager linearLayoutManager = (LinearLayoutManager)manager;
      firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
      totalItemCount = linearLayoutManager.getItemCount();
    }

    if (loading) {
      if (totalItemCount > previousTotal) {
        loading = false;
        previousTotal = totalItemCount;
      }
    }

    if (!loading &&
      (totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold)
      ) {
      loadMoreItems();
      loading = true;
    }

  }

  protected abstract void loadMoreItems();

}