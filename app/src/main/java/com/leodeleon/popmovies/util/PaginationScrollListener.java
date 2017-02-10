package com.leodeleon.popmovies.util;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by leodeleon on 09/02/2017.
 */

public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {

  GridLayoutManager layoutManager;

  public PaginationScrollListener(GridLayoutManager layoutManager) {
    this.layoutManager = layoutManager;
  }

  @Override
  public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
    super.onScrolled(recyclerView, dx, dy);

    int visibleItemCount = layoutManager.getChildCount();
    int totalItemCount = layoutManager.getItemCount();
    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

    if (!isLoading() && !isLastPage()) {
      if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
        && firstVisibleItemPosition >= 0
        && totalItemCount >= getTotalPageCount()) {
        loadMoreItems();
      }
    }

  }

  protected abstract void loadMoreItems();

  public abstract int getTotalPageCount();

  public abstract boolean isLastPage();

  public abstract boolean isLoading();

}