package com.leodeleon.popmovies.listeners;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by leodeleon on 09/02/2017.
 */

public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {

  GridLayoutManager layoutManager;
  private int previousTotal = 0;
  private int visibleThreshold = 2;
  private boolean loading = true;

  public PaginationScrollListener(RecyclerView recyclerView) {
    this.layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
  }

  @Override
  public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
    super.onScrolled(recyclerView, dx, dy);

    int visibleItemCount = layoutManager.getChildCount();
    int totalItemCount = layoutManager.getItemCount();
    int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

    if (loading) {
      if (totalItemCount > previousTotal) {
        loading = false;
        previousTotal = totalItemCount;
      }
    }

    if (!loading &&
      !isLastPage() &&
      (totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold)
      ) {
      loadMoreItems();
      loading = true;
    }

  }

  protected abstract void loadMoreItems();
  public abstract boolean isLastPage();


}