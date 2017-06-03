package com.leodeleon.popmovies.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.leodeleon.popmovies.R;

/**
 * Created by leodeleon on 09/02/2017.
 */

public abstract class LoaderAdapter<ContentViewHolder extends RecyclerView.ViewHolder, FooterViewHolder extends RecyclerView.ViewHolder>
    extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  public static final int VIEW_TYPE_FOOTER = 3;
  public static final int VIEW_TYPE_ITEM = 2;
  private static final int ID_FOOTER = -3;
  private static final int FOOTER_COUNT = 1;
  private boolean isLoading;

  @Override public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (viewType == VIEW_TYPE_FOOTER) {
      return onCreateFooterItemViewHolder(parent);
    } else if (viewType == VIEW_TYPE_ITEM) {
      return onCreateContentItemViewHolder(parent, viewType);
    } else {
      throw new IllegalStateException();
    }
  }

  @Override public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
    if (isAtContentPosition(position)) {
      onBindContentItemViewHolder(viewHolder, position);
    } else {
      onBindFooterItemViewHolder((FooterViewHolder) viewHolder);
    }
  }

  @Override public final int getItemCount() {
    return getContentItemCount() + getFooterItemCount();
  }

  @Override public final int getItemViewType(int position) {
    if (isAtContentPosition(position)) {
      return VIEW_TYPE_ITEM;
    } else {
      return VIEW_TYPE_FOOTER;
    }
  }

  @Override public long getItemId(int position) {
    if (isAtContentPosition(position)) {
      return getContentItemId(position);
    } else {
      return ID_FOOTER;
    }
  }

  private boolean isAtContentPosition(int position) {
    return getContentItemCount() > 0 && position < getContentItemCount();
  }

  public int getFooterItemCount(){
    return FOOTER_COUNT;
  }

  private RecyclerView.ViewHolder onCreateFooterItemViewHolder(ViewGroup parent){
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loader_view, parent, false);
    return new LoaderViewHolder(view);
  }


  private void onBindFooterItemViewHolder(FooterViewHolder footerViewHolder){
    footerViewHolder.itemView.setVisibility(isLoading ? View.VISIBLE : View.GONE);
  }

  public void startLoading() {
    isLoading = true;
  }

  public void stopLoading() {
    isLoading = false;
  }

  public boolean isLoading() {
    return isLoading;
  }

  private class LoaderViewHolder extends RecyclerView.ViewHolder {
    public LoaderViewHolder(View itemView) {
      super(itemView);
    }
  }

  protected abstract ContentViewHolder onCreateContentItemViewHolder(ViewGroup parent, int contentViewType);

  protected abstract void onBindContentItemViewHolder(RecyclerView.ViewHolder contentViewHolder, int position);

  protected abstract int getContentItemId(int position);

  protected abstract int getContentItemCount();

}